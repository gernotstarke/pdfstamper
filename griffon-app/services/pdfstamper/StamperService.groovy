/************************************************************************
 * This is free software - without ANY guarantee!
 *
 *
 * Copyright 2013, Dr. Gernot Starke, arc42.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ************************************************************************/

package pdfstamper

import com.itextpdf.awt.geom.Point
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper

import org.arc42.util.FileUtil



class StamperService {
    private PdfstamperModel model


    // this method is called after the model is instantiated
    void serviceInit() {

    }

    /**
     * stamp all pdf files in sourceDir, write resulting files to targetDir
     * @param myModel : the fully configured model, containing all configuration items
     */
    public void stampPdfFilesInDirectory(PdfstamperModel myModel) {
        String currentSourceFileName, currentTargetFileName

        int nrOfPagesInCurrentFile

        this.model = myModel

        // assert we're working on real directories
        assert new File(model.sourceDir).isDirectory()
        assert new File(model.targetDir).isDirectory()

        // collect all pdf files
        def filesToProcess = FileUtil.findPdfFilesInDirectory(model.sourceDir).sort()

        model.currentFileNumber = 0

        // loop over all files found
        // **************************
        for (currentFile in filesToProcess) {
            model.currentFileNumber += 1

            // file to read from in sourceDir
            currentSourceFileName = FileUtil.fullPathFileName( model.sourceDir, currentFile )

            // file to write to
            currentTargetFileName = FileUtil.fullPathFileName( model.targetDir, currentFile )

            // process the current file
            nrOfPagesInCurrentFile = stampPagesInFile(currentSourceFileName, currentTargetFileName)


            // evenify: ensure, that every file has an EVEN nr of pages!!
            // if the number of pages is ODD (that is, nr % 2 == 1), increment nrOfPagesInCurrentFile by 1
            if (model.evenify) {
                if (nrOfPagesInCurrentFile % 2 == 1) {
                    model.totalNrOfPagesSoFar += 1
                    // todo: if configured, add a blank pdf page here

                }
            }

        }

    }

    /**
     * Adds pagenumbers to pdf file by taking sourceFile and duplicating it to targetFile
     *
     * Pagenumbers might begin with a number greater than one, as this file might follow
     * another one... with consecutive numbering.
     *
      * Adding text (pagenumbers), aka "stamping" is performed upon a canvas.
     *
     * @param sourceFile is the original pdf, which is not modified.
     * @param targetFile is the file that will be written to.
     *
     * @author Gernot Starke
     *
     * */
    private int stampPagesInFile(String sourceFile, String targetFile) {
        // PDF related stuff
        PdfReader reader;
        PdfStamper stamper;


        String footerToStampOnPage


        // make sure source file exists, abort otherwise
        assert new File(sourceFile).exists()

        // initialize the pdf reader and stamper
        reader = initPdfReader(sourceFile)
        stamper = initPdfStamper(targetFile, reader)
        int nrOfPagesInThisFile = reader.getNumberOfPages()



        // loop over all pages in this file
        for (int currentPageInFile = 1; currentPageInFile <= nrOfPagesInThisFile; currentPageInFile += 1) {

            // increment total page count
            model.totalNrOfPagesSoFar += 1

            // we have to extract the canvas, so we can actually perform the stamping
            PdfContentByte canvas = stamper.getOverContent(currentPageInFile);


            // we have to calculate footer positions for every page,
            // as page layout and dimensions might change within a file (although unlikely)
            Point footerPosition = calculateFooterPosition( currentPageInFile, reader )
            Point headerPosition = calculateHeaderPosition( currentPageInFile, reader )

            // create the footer we want to stamp, including pagenumber
            //footerToStampOnPage = createFooter(currentPageInFile, pageNumberOffset, pageNumberPrefix)
            footerToStampOnPage = model.createFooter()

            // stamp the footer onto the page
            stampSinglePage(canvas, footerPosition, Element.ALIGN_CENTER, footerToStampOnPage);

            stampSinglePage( canvas, headerPosition, Element.ALIGN_CENTER, model.header )
        }

        // cleanup
        //reader.close()
        stamper.close()

        return  nrOfPagesInThisFile

    }

    /**
     * void stampSinglePage: adds text at specified position on a page, represented by canvas
     *
     * @param canvas : the canvas to stamp on
     * @param textToStamp : the string we have to print. Might contain a prefix.
     *        Examples: "A-1", "42", "chapter 4, page 19"
     * @param postion : the position as iText-Point where we have to stamp
     * @param alignment : left-aligned, right-aligned or centered:
     *
     * @author Gernot Starke
     * */
    private void stampSinglePage(PdfContentByte canvas, Point position, int alignment, String textToStamp) {

        /*
        * from the iText documentation:
        * ColumnText.showTextAligned(
        *   PdfContentByte canvas, int alignment, Phrase phrase,
        *   float x, float y, float rotation)
         */
        ColumnText.showTextAligned(canvas, alignment,
                new Phrase(textToStamp),
                (float) position.getX(), (float) position.getY(), 0);

    }

    /**
         * calculateHeaderPosition
         * Determines the Point where the header shall be stamped.
         * Headers are always stamped centered at the top.
         *
         * @param currentPageNumberInFile : what page is currently being processed?
         * @param reader : the PdfReader which reads our file
         * @return Point , the location where the header shall be stamped
         */
        private Point calculateHeaderPosition(int currentPageNumberInFile, PdfReader reader) {
            Rectangle rectangle = reader.getPageSize(currentPageNumberInFile);
            return new Point((int) (rectangle.getRight() / 2), 800);
        }


    /**
     * calculateFooterPosition
     * Determines the Point where the pagenumber shall be stamped.
     * PageNumbers are always stamped centered at the bottom.
     *
     * @param currentPageNumberInFile : what page is currently being processed?
     * @param reader : the PdfReader which reads our file
     * @return Point , the location where the header shall be stamped
     */
    private Point calculateFooterPosition(int currentPageNumberInFile, PdfReader reader) {
        Rectangle rectangle = reader.getPageSize(currentPageNumberInFile);
        return new Point((int) (rectangle.getRight() / 2), 15);
    }
//
//    /**
//     *
//     * @param currentPageNumber what's the current page number in current file
//     * @param offset
//     * @param prefix
//     * @return the text to stamp on the page, in the form "Chapter 1 - page 12" or similar
//     */
//    private String createFooter(int currentPageNrInFile, int pageNumberOffset, String prefix) {
//        // ensure prefix is not null
//        prefix = prefix != null ? prefix : ""
//
//
//        return prefix + String.valueOf(pageNumberOffset + currentPageNrInFile)
//
//    }



    /**
     * initializes the Pdf reader, so we can read one page after the other...
     * @param sourceFile : name of the file we want to read.
     * @return a reader instance
     */
    private initPdfReader(String sourceFile) {
        def PdfReader localReader

        try {
            localReader = new PdfReader(sourceFile);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localReader
    }

    /**
     * Initializes the stamper
     *
     * @param targetFile what is the file where we output our resulting pdf
     * @param reader the pdf reader where we get the original content from
     * @return a valid stamper instance
     */
    private PdfStamper initPdfStamper(String targetFile, PdfReader reader) {
        def PdfStamper localStamper

        assert reader != null

        try {
            localStamper = new PdfStamper(reader, new FileOutputStream(targetFile))

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localStamper
    }


}

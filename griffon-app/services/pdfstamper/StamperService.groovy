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

import java.util.regex.Pattern


class StamperService {
    private PdfstamperModel model

    private int nrOfPagesInCurrentFile = 0

    // this method is called after the model is instantiated
    void serviceInit() {

    }

    /**
     * stamp all pdf files in sourceDir, write resulting files to targetDir
     * @param myModel : the fully configured model, containing all configuration items
     */
    public void stampPdfFilesInDirectory(PdfstamperModel myModel) {
        String currentSourceFileName, currentTargetFileName

        this.model = myModel

        myModel.disallowStamping()  // disable start button

        // assert we're working on real directories
        assert new File(model.sourceDir).isDirectory()
        assert new File(model.targetDir).isDirectory()

        // collect all pdf files
        def filesToProcess = findPdfFilesInDirectory(model.sourceDir).sort()
        println "found files: " + filesToProcess

        model.currentFileNumber = 0

        // loop over all files fount
        for (currentFile in filesToProcess) {
            model.currentFileNumber += 1
            currentSourceFileName = model.getSourceDir() + "/" + currentFile
            currentTargetFileName = model.getTargetDir() + "/" + currentFile

            println "currently processing input file " + currentSourceFileName

            // process the current file
            stampPagesInFile(currentSourceFileName, currentTargetFileName)

            model.totalNrOfPagesSoFar += nrOfPagesInCurrentFile
        }

        model.allowStamping() // enables stamping again
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
     * @param prefix is the (optional) prefix string, like "chapter-2" or such...
     *
     * @author Gernot Starke
     *
     * */
    private void stampPagesInFile(String sourceFile, String targetFile) {
        // PDF related stuff
        PdfReader reader;
        PdfStamper stamper;


        String footerToStampOnPage

        // page number prefix, i.e. "page", yielding footers like "page 23"
        String pageNumberPrefix = model.pagePrefix

        // make sure the page number prefix is either a valid String or empty
        pageNumberPrefix = pageNumberPrefix != null ? pageNumberPrefix + " " : ""

        assert pageNumberPrefix != null


        // TODO handle file prefix


        // make sure source file exists, abort otherwise
        assert new File(sourceFile).exists()

        // initialize the pdf reader and stamper
        reader = initPdfReader(sourceFile)
        stamper = initPdfStamper(targetFile, reader)

        int nrOfPagesInThisFile = reader.getNumberOfPages()

        int pageNumberOffset = model.totalNrOfPagesSoFar

        // loop over all pages in this file
        for (int currentPageInFile = 1; currentPageInFile <= nrOfPagesInThisFile; currentPageInFile += 1) {

            // we have to calculate page number positions over and over again,
            // as page layout and dimensions might change within a file (although unlikely)
            Point position = calculatePageNrPosition(currentPageInFile, reader);

            // we have to extract the canvas, so we can actually perform the stamping
            PdfContentByte canvas = stamper.getOverContent(currentPageInFile);

            // increment total page count
            model.totalNrOfPagesSoFar += 1

            // create the footer we want to stamp, including pagenumber
            //footerToStampOnPage = createFooter(currentPageInFile, pageNumberOffset, pageNumberPrefix)
            footerToStampOnPage = model.createFooter()

            // do it, stamp the text onto the page
            stampSinglePage(canvas, position, Element.ALIGN_CENTER, footerToStampOnPage);

        }

        // cleanup
        //reader.close()
        stamper.close()

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
     * calculatePageNrPosition
     * Determines the Point where the pagenumber shall be stamped.
     * PageNumbers are always stamped centered at the bottom.
     *
     * @param currentPageNumberInFile : what page is currently being processed?
     * @param reader : the PdfReader which reads our file
     * @return Point , the location where the header shall be stamped
     */
    private Point calculatePageNrPosition(int currentPageNumberInFile, PdfReader reader) {
        Rectangle rectangle = reader.getPageSize(currentPageNumberInFile);
        return new Point((int) (rectangle.getRight() / 2), 15);
    }

    /**
     *
     * @param currentPageNumber what's the current page number in current file
     * @param offset
     * @param prefix
     * @return the text to stamp on the page, in the form "Chapter 1 - page 12" or similar
     */
    private String createFooter(int currentPageNrInFile, int pageNumberOffset, String prefix) {
        // ensure prefix is not null
        prefix = prefix != null ? prefix : ""


        return prefix + String.valueOf(pageNumberOffset + currentPageNrInFile)

    }


    /**
     * returns number of Pdf files found in given directory
     * @param directory
     * @return
     */
    public int nrOfPdfFilesInDirectory(String directory) {
        return findPdfFilesInDirectory(directory).length

    }

    /**
     *
     * @param directory where to search for files
     * @return a String[] of pdf filenames in the directory
     */
    private String[] findPdfFilesInDirectory(String directory) {
        return findFilesToProcess(directory, ~/.*\.pdf$/)

    }

    /**
     *
     * @param pattern @param inpuPathname
     * @return String[] of pdf files within inputputPathname
     */
    private String[] findFilesToProcess(String inputPathname, final Pattern pattern) {
        // myLogger.debug "finding files in " + inputPathname

        File inputPath = new File(inputPathname)

        def pdfFilesToProcess = []

        // if inputPathname is a directory, get all contained pdf files
        if (inputPath.directory) {
            inputPath.eachFileMatch(pattern) {
                pdfFilesToProcess << it.name
            }
        } else {
            pdfFilesToProcess << inputPathname
        }

        return pdfFilesToProcess
    }

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
            // TODO: make sure the output file does not exist!
            localStamper = new PdfStamper(reader, new FileOutputStream(targetFile))

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localStamper
    }


}

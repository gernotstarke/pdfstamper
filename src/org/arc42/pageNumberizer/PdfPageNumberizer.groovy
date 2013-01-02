package org.arc42.pageNumberizer

import com.itextpdf.awt.geom.Point
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.Phrase
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper

/**
 * Created with IntelliJ IDEA.
 * User: gstarke
 * Date: 16.12.12
 * Time: 17:05
 * 
 */

class PdfPageNumberizer {

    // location of pdf files to be stamped
    private String sourceDir

    // where to put finished pdf files
    private String targetDir


    // PDF related stuff
    private PdfReader reader;
    private PdfStamper stamper;




    PdfPageNumberizer( ) {
        sourceDir = "./sourceDir/"
        targetDir = "./targetDir/"
    }

    /** convenience mathod, in case no prefix is given.
     * (workaround, as Spockframework does currently not support optional method parameters)
     *
     * @param sourceFile
     * @param targetFile
     * @param pageNumberOffset
     * @param prefix
     * @return
     */
    def int numberizePagesInFile(String sourceFile, String targetFile, int pageNumberOffset) {
        return numberizePagesInFile(sourceFile, targetFile, pageNumberOffset, "")
    }


        /**
     * Adds pagenumbers to pdf file by taking sourceFile and duplicating it to targetFile
     *
     * Pagenumbers might begin with a number greater than one, as this file might follow
     * another one... with consecutive numbering.
     *
     * TODO: Add number prefix, so that "Chapter-1, page 15" becomes possible
     *
     * Adding page numbers (aka "stamping") is performed upon a canvas.
     *
     * @param sourceFile is the original pdf, which is not modified.
     * @param prefix is the (optional) prefix string, like "chapter-2" or such...
     *
     * @return number of pages found in this pdf
     * @author Gernot Starke
     *
     **/
    def int numberizePagesInFile(String sourceFile, String targetFile, int pageNumberOffset, String prefix) {
        String textToStampOnPage

        // make sure the optional parameter prefix is either a valid String or empty
        prefix = prefix != null ? prefix : ""

        assert prefix != null

        // make sure source file exists, abort otherwise
        assert new File(sourceFile).exists()

        reader = initPdfReader( sourceFile )
        stamper = initPdfStamper( targetFile, reader )

        final int nrOfPagesInThisFile = reader.getNumberOfPages()

        //logger.debug("starting page processing for file " + config.getInputFile());


        // loop over all pages in this file
        for (int currentPageInFile = 1; currentPageInFile <= nrOfPagesInThisFile; currentPageInFile += 1) {

            // we have to calculate page number positions over and over again,
            // as page layout and dimensions might change within a file (although unlikely)
            Point postion = calculatePageNrPosition( currentPageInFile );

            // we have to extract the canvas, so we can actually perform the stamping
            PdfContentByte canvas =  stamper.getOverContent(currentPageInFile);

            // create the text we want to print as page number
            // TODO: replace by method call textToStampOnPage = String.valueOf( pageNumberOffset + currentPageInFile )
            textToStampOnPage = createStringToStampOnPage( currentPageInFile, pageNumberOffset, prefix)

            // do it, stamp the text onto the page
            stampSinglePage( canvas, postion, Element.ALIGN_CENTER, textToStampOnPage );
        }


        // cleanup
        reader.close()
        stamper.close()

        // return number of pages
        return nrOfPagesInThisFile
    }


    protected initPdfReader(String sourceFile) {
        def PdfReader reader

        try {
            reader = new PdfReader(sourceFile);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  reader
    }

    /**
     * initPdfStamper
     * Initializes the stamper attribute
     *
     * @param targetFile what is the file where we output our resulting pdf
     * @param reader the pdf reader where we get the original content from
     * @return a valid stamper instance
     */
    private PdfStamper initPdfStamper(String targetFile, PdfReader reader) {
        def PdfStamper localStamper

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

    /**
     *
     * @param currentPageNumber what's the current page number in current file
     * @param offset
     * @param prefix
     * @return the text to stamp on the page, in the form "Chapter 1 - page 12" or similar
     */
    public String createStringToStampOnPage( int currentPageNrInFile, int pageNumberOffset, String prefix) {
        // ensure prefix is not null
        prefix = prefix != null ? prefix : ""

        return prefix + String.valueOf( pageNumberOffset + currentPageNrInFile )

    }


    /**
     * void stampSinglePage: adds text at specified position on a page, represented by canvas
     *
     * @param canvas: the canvas to stamp on
     * @param textToStamp: the string we have to print. Might contain a prefix.
     *        Examples: "A-1", "42", "chapter 4, page 19"
     * @param postion: the position as iText-Point where we have to stamp
     * @param alignment: left-aligned, right-aligned or centered:
     *
     * @author Gernot Starke
     **/
    private void stampSinglePage( PdfContentByte canvas, Point position, int alignment, String textToStamp  ) {

        /*
        * from the iText documentation:
        * ColumnText.showTextAligned(
        *   PdfContentByte canvas, int alignment, Phrase phrase,
        *   float x, float y, float rotation)
         */
        ColumnText.showTextAligned(canvas, alignment,
                new Phrase( textToStamp ),
                (float) position.getX(), (float) position.getY(), 0);

    }


    /**
     * calculatePageNrPosition
     * Determines the Point where the pagenumber shall be stamped.
     * PageNumbers are always stamped centered at the bottom.
     *
     * @param currentPageNumberInFile: what page is currently being processed?
     * @return Point, the location where the header shall be stamped
     */
    private Point calculatePageNrPosition( int currentPageNumberInFile ) {
        Rectangle rectangle = reader.getPageSize(currentPageNumberInFile);
        return new Point((int) (rectangle.getRight()/2), 15 );
    }



}

package pdfstamper

import griffon.test.*

class StamperServiceTests extends GriffonUnitTestCase {

    PdfstamperModel myModel

    protected void setUp() {
        super.setUp()
        myModel = new PdfstamperModel()

        myModel.pagePrefix = "Page"
        myModel.totalNrOfPagesSoFar = 42
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testFooterWithoutFilePrefix() {
        myModel.filePrefix = ""
        myModel.filePageSeparator = ""

        assert myModel.createFooter() == "Page 42"
    }

    void testFooterWithFilePrefix() {
        myModel.filePrefix = "Chapter"
        myModel.filePageSeparator = " / "

        myModel.currentFileNumber = 3

        assert myModel.createFooter() == "Chapter 3 / Page 42"

    }
}

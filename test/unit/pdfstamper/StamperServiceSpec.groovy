package pdfstamper

import spock.lang.*

class StamperServiceSpec extends Specification {

    PdfstamperModel myModel
    StamperService myService



    def setup() {
        //GriffonUnitTestCase.setUp()
        myModel = new PdfstamperModel()

        myModel.pagePrefix = "Page"
        myModel.filePageSeparator = ""
        myModel.filePrefix = ""

        myModel.targetDir = System.getenv("TMPDIR")

        myService = new StamperService()

    }



    def "Can stamp one-page Pdf file without evenify"() {
        given: "A One-page pdf file and PdfStamper model with minimal settings"
            myModel.evenify = false
            myModel.sourceDir = TestResources.DIR_WITH_ONE_PDF

        when: "we stamp the one-page file"
            myService.prepareAndProcessAllFiles( myModel )


        then: "the pagecount shall be one"
        myModel.totalNrOfPagesSoFar == 1

    }

    @Unroll("Stamping files in '#directory' with evenify='#evenify' yields '#nrOfPages' stamped pages")
    def "can stamp Pdf files with various settings"() {
        given: "directory with pdf files and evenify settings"
            myModel.sourceDir = directory
            myModel.evenify   = evenify

        when: "we stamp the files in directory"
            myService.prepareAndProcessAllFiles( myModel )

        then:
            myModel.totalNrOfPagesSoFar == nrOfPages

        where:
        directory                         | evenify  |  nrOfPages
         TestResources.DIR_WITH_ONE_PDF   | false    |   1
         TestResources.DIR_WITH_ONE_PDF   | true     |   2
         TestResources.DIR_WITH_TWO_PDFs  | false    |   4
         TestResources.DIR_WITH_TWO_PDFs  | true     |   6
         TestResources.DIR_WITH_MANY_PDFs | false    | 123
         TestResources.DIR_WITH_MANY_PDFs | true     | 128

    }


}

package org.arc42.pageNumberizer.test

import org.arc42.pageNumberizer.PdfPageNumberizer
import org.arc42.util.FileUtil
import spock.lang.Specification

/**
 * Specifies how a single file has to be stamped with pagenumbers
 * Created with IntelliJ IDEA.
 * User: gstarke
 * Date: 25.12.12
 * Time: 19:11
 *
 */
class StampSingleFileSpec extends Specification {
    def PdfPageNumberizer pdfPageNumberizer
    def String[] filesToProcess

    def setup() {
        pdfPageNumberizer = new PdfPageNumberizer()
    }

    def "Stamp a single file with only pagenumbers"() {
        given: "directory containing a single pdf with three pages"
            filesToProcess = FileUtil.findPdfFilesInDirectory( TestResources.DIR_WITH_ONE_PDF )

        when: "we numberize pages in this file starting with page number 1"
            int pageNumberOffset = 0
            String sourceFile = TestResources.DIR_WITH_ONE_PDF + filesToProcess[0]
            String targetFile = TestResources.DIR_WITH_ONE_PDF + "stamped_" + filesToProcess[0]

            int totalNumberOfPagesStamped = pdfPageNumberizer.numberizePagesInFile( sourceFile, targetFile, pageNumberOffset )


        then: "at the end, the page number counter will be 3"
            println "total number of pages stamped in " + sourceFile
            println "==> " + totalNumberOfPagesStamped

            3 == totalNumberOfPagesStamped
    }
}

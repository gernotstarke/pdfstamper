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
class StampFilesSpec extends Specification {
    def PdfPageNumberizer pdfPageNumberizer
    def String[] filesToProcess

    private int pageNumberOffset
    private int totalNumberOfPagesStamped

    private String sourceFile
    private String targetFile

    private String sourceDir
    private String targetDir


    /** setup is run before each test method
    *
     */
    def setup() {
        pdfPageNumberizer = new PdfPageNumberizer()

        pageNumberOffset  = 0

        totalNumberOfPagesStamped = 0

        // TODO: clearTargetDir()
    }


    /*
     * tries to numberize single files: one simple 1-pager, one >6MB large colored pdf
     */
    def "Stamp a single file "(String p_sourceDir, String p_targetDir, int p_expectedNrOfPages) {
        given: "directory containing a single pdf and a target filename"
            filesToProcess = FileUtil.findPdfFilesInDirectory( p_sourceDir )
            sourceFile = p_sourceDir + filesToProcess[0]
            targetFile = p_targetDir + "stamped_" + filesToProcess[0]

        when: "we numberize pages in this file and write the output to a target directory"
            totalNumberOfPagesStamped =
                pdfPageNumberizer.numberizePagesInFile(sourceFile, targetFile, pageNumberOffset)
                println totalNumberOfPagesStamped + " page(s) stamped in " + sourceFile


        then: "the page number counter must match that of the original file"
            p_expectedNrOfPages == totalNumberOfPagesStamped

        where:
            p_sourceDir                       | p_targetDir             || p_expectedNrOfPages
            TestResources.DIR_WITH_ONE_PDF  | TestResources.TARGET_DIR  || 1
            TestResources.DIR_WITH_HUGE_PDF | TestResources.TARGET_DIR  || 3

    }



    def "Stamp two files with pagenumbers and consecutive numbering"() {
        given: "directory containing two pdf files with one and three pages"
            sourceDir = TestResources.DIR_WITH_TWO_PDFs
            filesToProcess = FileUtil.findPdfFilesInDirectory( sourceDir )
            targetDir = TestResources.TARGET_DIR

        when: "we numberize pages in both files consecutively"
            for (int i=0; i<2; i++) {
                sourceFile = sourceDir + filesToProcess[i]
                targetFile = targetDir + "stamped_" + filesToProcess[i]

                totalNumberOfPagesStamped = totalNumberOfPagesStamped +
                            pdfPageNumberizer.numberizePagesInFile( sourceFile, targetFile, totalNumberOfPagesStamped)

            }

        then: "at the end, the total number of pages stamped will be four"
            4 == totalNumberOfPagesStamped
    }



    def "Stamp several files with pagenumbers and consecutive numbering"() {
        given: "directory containing many pdf files between one and 101 pages"
            sourceDir = TestResources.DIR_WITH_MANY_PDFs
            filesToProcess = FileUtil.findPdfFilesInDirectory( sourceDir )
            targetDir = TestResources.TARGET_DIR

        when: "we numberize pages in all files consecutively"
            String currentFileName
            for (it in filesToProcess ) {
                currentFileName = it
                sourceFile = sourceDir + currentFileName
                targetFile = targetDir + "stamped_many_" + currentFileName

                totalNumberOfPagesStamped = totalNumberOfPagesStamped +
                            pdfPageNumberizer.numberizePagesInFile( sourceFile, targetFile, totalNumberOfPagesStamped)

            }

        then: "at the end, the total number of pages stamped will be 119"
        //
            119 == totalNumberOfPagesStamped
    }
}

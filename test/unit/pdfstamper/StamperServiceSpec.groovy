package pdfstamper

import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfReader
import com.lowagie.text.Rectangle
import spock.lang.*

import org.arc42.util.FileUtil

//import java.awt.Rectangle

class StamperServiceSpec extends Specification {
    String inputPath

    // setup is run before every feature method
    def setup() {

    }

    def "Check directories with test Pdfs"() {
        when: "We check some given paths"

        def testDir = new File(TestResources.TEST_RESOURCES_DIR)
        def emptyDir = new File(TestResources.EMPTY_DIR)
        def oneDir = new File(TestResources.DIR_WITH_ONE_PDF)
        def twoDir = new File(TestResources.DIR_WITH_TWO_PDFs)
        def manyDir = new File(TestResources.DIR_WITH_MANY_PDFs)

        then: "All of them are directories"
        // root of test directory structure

            testDir.isDirectory()
            //emptyDir.isDirectory()
            oneDir.isDirectory()
            twoDir.isDirectory()
            manyDir.isDirectory()
    }


    def "Can locate source directory"() {
        given: "A path to a directory"
        inputPath = TestResources.TEST_RESOURCES_DIR

        when: "A new file object is created at that path"
        def sourceDir = new File(inputPath)

        //println sourceDir.absolutePath
        //println sourceDir.canonicalPath

        then: "This is a directory"
        sourceDir.directory == true

    }

    def "Locate test source directory with ONE Pdf file"() {
        when: "Create a file object at path with one pdf"
        def filesToProcess = FileUtil.findPdfFilesInDirectory(TestResources.DIR_WITH_ONE_PDF)

        then: "This directory contains ONE pdf file"
        filesToProcess.length == 1
        println "one file: " + filesToProcess
    }



    def "Test directory exists with TWO pdf files"() {
        given: "A path to a directory containing exactly TWO pdf files"

        when: "we open the directory"
        def filesToProcess = FileUtil.findPdfFilesInDirectory(TestResources.DIR_WITH_TWO_PDFs)

        then: "we find two pdf files"
        filesToProcess.length == 2
        println "two files: " + filesToProcess
    }


    def "Test directory with several pdf files"() {
        given: "Path to directory with several pdf files"

        when: "we search for all pdf files in this directory"
        def filesToProcess = FileUtil.findPdfFilesInDirectory(TestResources.DIR_WITH_MANY_PDFs)

        then: "we find more than 3 files"
        filesToProcess.length > 3
        println "several files: " + filesToProcess
    }



    def "One Pdf file has correct name and dimensions"() {
        when: "we open the one-pdf-sample"
        def filesToProcess = FileUtil.findPdfFilesInDirectory(TestResources.DIR_WITH_ONE_PDF)
        def sourceFile = TestResources.DIR_WITH_ONE_PDF + filesToProcess[0]

        // println filesToProcess[0]

        def pdfreader = new PdfReader(sourceFile)

        Rectangle mediabox = pdfreader.getPageSize(1)
        int width = mediabox.getWidth().intValue()
        int heigth = mediabox.getHeight().intValue()

        then: "it has the correct name and A4 portrait dimensions"
        filesToProcess.length == 1

        filesToProcess[0] == "sample-A4-portrait-1pg.pdf"

        width == PageSize.A4.width
        heigth == PageSize.A4.height

    }

}

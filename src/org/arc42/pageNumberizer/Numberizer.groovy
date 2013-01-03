package org.arc42.pageNumberizer

import org.arc42.util.FileUtil



/**
 * Numberize a set of pdf files (aka "handout") with pagenumbers and optional information.
 *
 * Acts as controller for the StamperModel
 * @author Gernot Starke
 *
 * Date: 25.12.12
 *
 */

class Numberizer {

    private PdfPageNumberizer fileWorker
    private NumberizerUI gui
    private StamperModel model


    protected String messages


    public Numberizer() {
        messages = ""
    }

    /**
     * numberize all pdf files in sourceDir, write resulting files to targetDir
     */
    public void numberizePdfFilesInDirectory( StamperModel model) {
        int totalNumberOfPagesSoFar = 0
        int totalNumberOfFiles = 0

        String currentSourceFileName
        String currentTargetFileName

        // assert we're working on real directories
        assert new File (model.getSourceDir()).isDirectory()
        assert new File (model.getTargetDir()).isDirectory()

        // collect all pdf files
        def filesToProcess = FileUtil.findPdfFilesInDirectory( model.getSourceDir() ).sort()

        println "found files: " + filesToProcess

        // loop over all files fount
        for (it in filesToProcess ) {
            currentSourceFileName = model.getSourceDir() + "/" + it
            currentTargetFileName = model.getTargetDir() + "/" + it

            println "currently processing input file " + currentSourceFileName

            def int nrOfPagesInCurrentFile =
                fileWorker.numberizePagesInFile(
                        currentSourceFileName,
                        currentTargetFileName,
                        totalNumberOfPagesSoFar // offset
                )

            totalNumberOfPagesSoFar += nrOfPagesInCurrentFile
        }
    }

    // this is our main app
    static main(args) {
        Numberizer worker = new Numberizer()

        worker.fileWorker = new PdfPageNumberizer()
        worker.model = new StamperModel( "./", "./")
        worker.gui = new NumberizerUI( worker, worker.model )

        worker.gui.createUI()


    }
}

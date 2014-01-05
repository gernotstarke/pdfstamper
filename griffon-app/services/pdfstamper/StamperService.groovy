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

import org.arc42.util.FileUtil
import pdfstamper.HorizontalPositions as HPOS


class StamperService {

    // processing state (== status)
    ProcessingState processingState

    // our configuration information is kept in the model
    private StamperConfiguration config

    // pdf processing delegated to helper
    private SingleFileStamper fileStamper




    // this method is called after the model is instantiated
    void serviceInit() {
        processingState = new ProcessingState()
    }

    /**
     * stamp all pdf files in sourceDir, write resulting files to targetDir
     * @param pdfStamperModel : the fully configured model, containing all configuration items
     *
     */
    public ProcessingState prepareAndProcessAllFiles(StamperConfiguration configuration) {

        this.config = configuration

        // make sure that target directory exists
        // this "should" have been done by the fileChooser... but one never knows
        assert new File(config.targetDir).isDirectory()

        // depending on the footer config, different PageStampers might be needed
        configureFileStamper()

        // collect and sort all pdf files
        def pdfFilesToProcess = prepareAndCollectPdfSourceFiles( configuration.sourceDir )

        // start stamping at zero pages
        processingState.resetProcessingState()

        // iterate over all pdf files
        processAllPdfFiles( pdfFilesToProcess )

        return processingState
    }




    /*
     * chose the implementation of @see SingleFileStamper for footer processing.
     */
    private void configureFileStamper() {
        if (config.pageNumberHorizontalPosition == HPOS.positions[HPOS.CENTER] ) {
            fileStamper = new JoinedFooterStamper( config )

        }
        else fileStamper = new PartitionedFooterStamper( config )
    }


    /*
     * prepare processing: check directories, determine appropriate PageStamper-class,
     * collect filenames
     */
    private String[] prepareAndCollectPdfSourceFiles( String sourceDirectory ) {

        // assert we're working on real directories
        assert new File(config.sourceDir).isDirectory()

        // get only the files with "pdf" extension
        return FileUtil.findPdfFilesInDirectory( sourceDirectory ).sort()
    }


    /*
     * iterate over all @param pdfFilesToProcess, prepare filePath for them
     * and process (stamp) them one after the other.
     */
    private processAllPdfFiles(String[] pdfFilesToProcess) {

        String currentSourceFileName
        String currentTargetFileName

        // loop over all files found
        // **************************
        for (currentFile in pdfFilesToProcess) {
            processingState.incrementCurrentFileNumber()

            // file to read from in sourceDir
            currentSourceFileName = FileUtil.fullPathFileName(config.sourceDir, currentFile)

            // file to write to
            currentTargetFileName = FileUtil.fullPathFileName(config.targetDir, currentFile)


            // now we have fully qualified filenames for source and target
            fileStamper.stampSingleFile( currentSourceFileName,
                                         currentTargetFileName,
                                         processingState)


        }
    }

}

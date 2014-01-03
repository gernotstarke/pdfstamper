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
 *********************************************************************** */

/**
 * @author Gernot Starke
 *
 */

package pdfstamper

import griffon.transform.PropertyListener

import java.awt.*

import static griffon.util.GriffonNameUtils.isBlank

import pdfstamper.HorizontalPositions as HPOS

@Bindable
@PropertyListener(startButtonEnabler)
@PropertyListener(pageAndFileSeparatorEnabler)

public class PdfstamperModel {

    // configuration items - will be passed to stamping-service
    // after user has choosen required options
    StamperConfiguration config


    String sourceDir = ""
    String targetDir = ""

    // header configuration
    // ************************************************
    String header = ""

    // footer configuration settings
    // ************************************************

    String pagePrefix = "Seite"
    String filePageSeparator = ", "
    String filePrefix = "Kapitel"


    // other configuration options
    //*************************************************
    Boolean evenify = true    // ensure that every file has EVEN number of pages

    // either CENTER, OUTSIDE or INSIDE
    String pageNumberHorizontalPosition = HPOS.positions[HPOS.OUTSIDE]


    Boolean footerIsCentered = false



    // status and statusBar
    // ***************************************************

    String status = "Welcome to PdfStamper - built on Groovy and Griffon by arc42 "
    Color statusColor = PdfStamperUIConstants.TITLECOLOR

    int nrOfFilesToStamp

    boolean startButtonEnabled = false

    boolean currentlyIdle = true


    /**
     * getStamperConfiguration
     */
    public StamperConfiguration getStamperConfiguration() {
        return new StamperConfiguration( sourceDir: sourceDir,
                targetDir: targetDir,
                header: header,
                filePrefix: filePrefix,
                pagePrefix: pagePrefix,
                filePageSeparator: filePageSeparator,
                pageNumberHorizontalPosition: pageNumberHorizontalPosition,
                evenify: evenify)

    }


    public void disallowStamping() {
        currentlyIdle = false
    }

    public void allowStamping() {
        currentlyIdle = true
    }

    // check if we need a page/file separator
    private pageAndFileSeparatorEnabler = {
        e -> footerIsCentered = (pageNumberHorizontalPosition == HPOS.positions[HPOS.CENTER])
    }

    // check if stamping is currently allowed
    // (use method to en/disable start button)
    private startButtonEnabler = { e ->
        startButtonEnabled =
                //  start of stamping is only allowed, if:
                // 1.) source and target directories have been selected
                directoryHasBeenSelected( sourceDir) &&
                directoryHasBeenSelected( targetDir) &&

                // 2.) stamperService is current not running
                //     -> disableStamping gets called upon entry of service
                currentlyIdle &&

                // 3.) source and target directories are different
                targetDir != sourceDir &&

                // 4.) there's at least ONE Pdf file contained in source directory
                nrOfFilesToStamp > 0
    }

    private boolean directoryHasBeenSelected( String directory) {
        return !isBlank( directory )
    }


    public String getFilePrefix() {
        return (filePrefix == null) ? "" : filePrefix
    }


    public String getPagePrefix() {
        return (pagePrefix == null) ? "" : pagePrefix
    }


    public String getFilePageSeparator() {
        return (filePageSeparator == null) ? "" : filePageSeparator
    }

}



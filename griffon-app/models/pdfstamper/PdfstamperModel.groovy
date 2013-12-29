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

import java.awt.Color

import static griffon.util.GriffonNameUtils.isBlank
import griffon.transform.PropertyListener


@Bindable
@PropertyListener(startButtonEnabler)
@PropertyListener(pageAndFileSeparatorEnabler)

class PdfstamperModel {


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


    // sample footer - to display in View
    //*************************************************
    String sampleFooter = ""

    // gets called upon PropertyChange
    // TODO: remove, better put example in toolTip
    def calcSampleFooter = {
        //println 'called calcFooter with ' + filePrefix + ' ' + pagePrefix
        sampleFooter =  filePrefix + ' 2' + filePageSeparator + pagePrefix + ' 42'
    }



    // other configuration options
    //*************************************************
    Boolean evenify = true    // ensure that every file has EVEN number of pages


    String pageNumberHorizontalPosition

    Boolean footerIsCentered = false


    // processing status
    // ***************************************************
    int totalNrOfPagesSoFar = 0
    int nrOfFilesToStamp = 0

    int currentFileNumber = 0


    // statusBar
    // ***************************************************

    String status = "Welcome to PdfStamper - built on Groovy and Griffon by arc42 "
    Color statusColor = PdfStamperUIConstants.TITLECOLOR

    boolean startButtonEnabled = false

    boolean currentlyIdle = true

    public void disallowStamping() {
        currentlyIdle = false
    }

    public void allowStamping() {
        currentlyIdle = true
    }

    // check if we need a page/file separator
    private pageAndFileSeparatorEnabler = {
        e -> footerIsCentered = (pageNumberHorizontalPosition == HorizontalPositions.positions[HorizontalPositions.CENTER])
    }



    //* check if stamping is currently allowed (use method to en/disable start button)
     private startButtonEnabler = { e ->  startButtonEnabled =
        //  start of stamping is only allowed, if:
        // 1.) source and target directories have been selected
         !isBlank(sourceDir) &&
         !isBlank(targetDir) &&

        // 2.) stamperService is current not running
        //     -> disableStamping gets called upon entry of service

         currentlyIdle &&

        // 3.) source and target directories are different
         targetDir != sourceDir  &&

        // 4.) there's at least ONE Pdf file contained in source directory
         nrOfFilesToStamp > 0
    }





    /**
     * create the footer text
     * examples:
     *   file prefix = "Chapter", page prefix = "Page", filePageSeparator = " / ", footer = Chapter 2 / Page 42
     * @return the footer to be stamped on the current page
    **/
    public String createFooter() {
        // avoid NPE, might be redundant checking
        filePrefix = (filePrefix == null) ? "" : filePrefix
        pagePrefix = (pagePrefix == null) ? "" : pagePrefix
        filePageSeparator = (filePageSeparator == null) ? "" : filePageSeparator

        String myFooter = ""

        if (filePrefix != "") {
            myFooter = filePrefix + " " + currentFileNumber.toString() + filePageSeparator
        }

        return myFooter + pagePrefix + " " + totalNrOfPagesSoFar

    }
}

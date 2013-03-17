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
import groovy.beans.Bindable

import static griffon.util.GriffonNameUtils.isBlank
import griffon.transform.PropertyListener


@Bindable
@PropertyListener(enabler)


class PdfstamperModel {


    String sourceDir = ""
    String targetDir = ""

    // header configuration
    // ************************************************
    String header = ""

    // footer configuration settings
    // ************************************************

    String pagePrefix
    String filePageSeparator
    String filePrefix

    // sample footer - to display in View
    //*************************************************
    String sampleFooter = ""

    // gets called upon PropertyChange
    def calcSampleFooter = {
        //println 'called calcFooter with ' + filePrefix + ' ' + pagePrefix
        sampleFooter =  filePrefix + ' 2' + filePageSeparator + pagePrefix + ' 42'
    }



    // processing status
    // ***************************************************
    int totalNrOfPagesSoFar = 0
    int nrOfFilesToStamp = 0

    int currentFileNumber = 0

    boolean startButtonEnabled = false

    boolean stampingCurrentlyPossible = false

    private enabler = { e ->
        startButtonEnabled =
            !isBlank(sourceDir) &&
            !isBlank(targetDir) &&
            stampingCurrentlyPossible
    }

    /**
     * check wether stamping is currently allowed (use method to en/disable start button)
     *
     * start of stamping is only allowed, if:
     *   1.) source and target directories have been selected
     *   2.) stamperService is current not running
     *   3.) source and target directories are different
     *   4.) there's at least ONE Pdf file contained in source directory
     * @return true iff stamping is currently allowed
     */




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
        String prefix = ""

        if (filePrefix != "") {
            myFooter = filePrefix + " " + currentFileNumber.toString() + filePageSeparator
        }

        return myFooter + pagePrefix + " " + totalNrOfPagesSoFar

    }
}

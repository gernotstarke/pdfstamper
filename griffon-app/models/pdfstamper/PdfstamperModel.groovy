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

import griffon.transform.PropertyListener
import groovy.beans.Bindable

import static griffon.util.GriffonNameUtils.isBlank
import griffon.transform.PropertyListener


@Bindable
@PropertyListener(enabler)


class PdfstamperModel {


    String sourceDir = ""
    String targetDir = ""

    // configuration settings

    String pagePrefix = "Seite "
    String filePrefix = "Kapitel "


    Boolean addHeader = false
    String header = ""


    // processing status
    int totalNrOfPagesSoFar = 0
    int nrOfFilesToStamp = 0

    String footerSample = ""

    // start of stamping is only allowed, if:
    //   1.) source and target directories have been selected
    //   2.) stamping has not already been performed
    //   3.) source and target directories are different
    //   4.) there's at least ONE Pdf file contained in source directory
    boolean startButtonEnabled = false

    private enabler = { e ->
        startButtonEnabled = !isBlank(sourceDir) &&
                    !isBlank(targetDir)
    }
}

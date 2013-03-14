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

/*
 * @author Gernot Starke
 *
 */
package pdfstamper

import javax.swing.JFileChooser

class PdfstamperController {
    def model
    def view
    def stamperService


    void mvcGroupInit(Map<String, Object> args) {
        println "this is mvcGroupInit"

    }


    def startStamping = { evt = null ->
        println "starting to stamp in controller"
        // delegate to service
        // TODO: ensure stampins is performed OUTSIDE of swing event loop!

        execOutsideUI{
            stamperService.stampPdfFilesInDirectory( model )
        }

    }

    def selectSrcDir = { evt = null ->
        def openResult = view.srcDirChooserWindow.showOpenDialog(view.mainFrame)
        if (JFileChooser.APPROVE_OPTION == openResult) {
            // update source directory textfield
            model.sourceDir = view.srcDirChooserWindow.selectedFile
            view.sourceDir.reverseUpdate()

            // update number of Pdf files found in source dir
            model.nrOfFilesToStamp = stamperService.nrOfPdfFilesInDirectory( model.sourceDir )
            //view.nrOfFilesToStamp.reverseUpdate()
        }
    }


    def selectTargetDir = { evt = null ->
        def openResult = view.targetDirChooserWindow.showOpenDialog(view.mainFrame)
        if (JFileChooser.APPROVE_OPTION == openResult) {
            model.targetDir = view.targetDirChooserWindow.selectedFile
            view.targetDir.reverseUpdate()
        }
    }

    def aboutAction = {
        withMVCGroup('about') { m, v, c ->
            c.show()
        }
    }
    def onOSXAbout = { app ->
        withMVCGroup('about') { m, v, c ->
            c.show()
        }
    }


//    def onOSXPrefs = { app ->
//        withMVCGroup('preferences') { m, v, c ->
//            c.show()
//        }
//    }


    def quit = {
        app.shutdown()
    }
}

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

import javax.swing.BoxLayout
import javax.swing.JFileChooser
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension

final Dimension textFieldDimension = new Dimension(500, 30)


srcDirChooserWindow = fileChooser(
        fileSelectionMode: JFileChooser.DIRECTORIES_ONLY,
        dialogTitle: "please choose source directory, containing files to be stamped")

targetDirChooserWindow = fileChooser(
        fileSelectionMode: JFileChooser.DIRECTORIES_ONLY,
        dialogTitle: "please choose target directory")


mainFrame = application(title: 'griffonPdfStamper',
        //preferredSize: new Dimension(720, 240),
        pack: true,
        //location: [50,50],
        locationByPlatform: true,
        iconImage: imageIcon('/griffon-icon-48x48.png').image,
        iconImages: [imageIcon('/griffon-icon-48x48.png').image,
                imageIcon('/griffon-icon-32x32.png').image,
                imageIcon('/griffon-icon-16x16.png').image]) {

        borderLayout()

        label("", constraints: BorderLayout.PAGE_START)

        panel(constraints: BorderLayout.LINE_START) {
            gridLayout(columns: 1, rows: 4, hgap: 6, vgap: 6)


            button( "Source directory...",
                    actionPerformed:controller.selectSrcDir)

            button( "Target directory...",
                    actionPerformed:controller.selectTargetDir)
            glue()
            button( "Start!", foreground: Color.BLUE,
                    actionPerformed:controller.startStamping,
                    enabled: bind { model.startButtonEnabled }
            )
        }

        panel(constraints: BorderLayout.CENTER) {
            gridLayout(columns: 1, rows: 4, hgap: 6, vgap: 6)

            textField(id: "sourceDir",
                    columns: 60,
                    text: bind('sourceDir', target:model,
                            id:'sourceDir')
            )

            textField( id: "targetDir",
                    columns: 60,
                    text: bind( 'targetDir', target: model,
                            id:'targetDir')
            )

            glue()

            panel(id: "progressAndCancel") {
                migLayout()

                label( "files found: ")
                label( text: bind {model.nrOfFilesToStamp }, id:'nrOfFilesToStamp', constraints: "wrap")

                label( "pages stamped: ")
                label( text: bind {model.totalNrOfPagesSoFar } )
            }


        }

        panel(constraints: BorderLayout.LINE_END) {
            boxLayout(axis: BoxLayout.PAGE_AXIS)

            label(icon: imageIcon("/pdfstamper-logo.png"))

            glue()
            // label("arc42.org   ", font: ["Helvetica", Font.PLAIN, 9],  foreground: Color.BLUE)
            glue()
            button( text: "Cancel",
                    actionPerformed:controller.quit)

        }

    }

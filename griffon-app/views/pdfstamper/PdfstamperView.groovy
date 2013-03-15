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

package pdfstamper

import javax.swing.BoxLayout
import javax.swing.JFileChooser
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Desktop
import java.awt.Dimension


srcDirChooserWindow = fileChooser(
        fileSelectionMode: JFileChooser.DIRECTORIES_ONLY,
        dialogTitle: "please choose source directory, containing files to be stamped")

targetDirChooserWindow = fileChooser(
        fileSelectionMode: JFileChooser.DIRECTORIES_ONLY,
        dialogTitle: "please choose target directory")


mainFrame = application(title: 'Pdf Stamper - arc42.org',
        pack: true,
        resizable: false,
        locationByPlatform: true,
        iconImage: imageIcon('/pdfstamper-logo.png').image,
        iconImages: [imageIcon('/pdfstamper-logo-90px.png').image]) {

    panel(border: emptyBorder(6)) {
        migLayout(layoutConstraints: 'fill')

        bannerPanel(constraints: 'span 2, grow, wrap',
                title: 'Pdf Stamper',
                subtitle: 'add pagenumbers and headers to your pdf files...',
                titleIcon: imageIcon('/pdfstamper-logo-90px.png'),
                subTitleColor: Color.WHITE,
                background: new Color(0, 0, 0, 1),
                startColor: Color.LIGHT_GRAY,
                endColor: Color.BLUE,
                vertical: true
        )

        panel(border: lineBorder(color: Color.BLUE, thickness: 1), constraints: 'grow, left, wrap, span 2') {
            migLayout()

            label('Directories', constraints: "wrap", foreground: Color.BLUE)

            button('Source Directory', constraints: 'skip',
                    actionPerformed: controller.selectSrcDir)
            textField(columns: 40, id: 'sourceDir',
                    text: bind('sourceDir',
                            target: model,
                            id: 'sourceDir')
            )

            label(id: 'nrOfPdfFilesFound',
                    foreground: Color.LIGHT_GRAY,
                    text: bind { model.nrOfFilesToStamp }
            )
            label(' Pdf files', foreground: Color.LIGHT_GRAY, constraints: 'wrap')


            button('Target Directory', constraints: 'skip',
                    actionPerformed: controller.selectTargetDir)
            textField(columns: 40, constraints: 'wrap',
                    id: "targetDir",
                    text: bind('targetDir',
                            target: model,
                            id: 'targetDir')
            )
        }


        panel(border: lineBorder(color: Color.BLUE, thickness: 1), constraints: 'grow, span 2,wrap') {
            migLayout()

            label 'Configuration:', constraints: "wrap", foreground: Color.BLUE

            label 'Header: ', constraints: 'left, skip'
            textField(id: 'header', columns: 40,
                    constraints: 'span, growx',
                    text: bind { model.header })

            label 'File prefix: ', constraints: 'skip'
            textField(columns: 20, id: 'filePrefix', constraints: 'wrap',

                    text: bind( target:model, 'filePrefix', value: 'Kapitel')
            )

            label 'Page number prefix: ', constraints: 'skip'
            textField(columns: 20, id: 'pagePrefix', constraints: '',
                    text: bind( target:model, 'pagePrefix', value: 'Seite')
            )

            label 'e.g.', foreground: Color.LIGHT_GRAY, constraints: ''
            label( text: bind( source: model,
                            sourceProperty: 'sampleFooter',
                            sourceEvent: 'propertyChange',
                            converter: model.calcFooter),
                    foreground: Color.LIGHT_GRAY,
                    constraints: 'wrap')


        }


        panel(border: lineBorder(color: Color.BLUE, thickness: 1), constraints: 'grow, wrap') {
            migLayout(constraints: '')
            label 'Stamping:', constraints: "left, wrap", foreground: Color.BLUE

            button('Start!', constraints: 'skip, spanx 2',
                    actionPerformed: controller.startStamping,
                    enabled: bind { model.startButtonEnabled }
            )
            label(text: bind { model.totalNrOfPagesSoFar }, id: 'NrOfPagesProcessed',
                    foreground: Color.LIGHT_GRAY, constraints: '')
            label(' pages already processed', foreground: Color.LIGHT_GRAY, constraints: '')

        }

        button('Cancel', constraints: 'right, wrap',
                actionPerformed: controller.quit
        )

        jideButton(label: "free and open source: arc42.org",
                foreground: Color.BLUE, constraints: 'right',
                actionPerformed: {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI('http://www.arc42.org'))
                        }
                        catch (Exception ex) {
                        }
                    }

                }
        )
    }

}





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

import javax.swing.*

srcDirChooserWindow = fileChooser(
        fileSelectionMode: JFileChooser.DIRECTORIES_ONLY,
        dialogTitle: "please choose source directory, containing files to be stamped")

targetDirChooserWindow = fileChooser(
        fileSelectionMode: JFileChooser.DIRECTORIES_ONLY,
        dialogTitle: "please choose target directory")


mainFrame = application( //
        title: 'Pdf Stamper - arc42.org',
        pack: true,
        resizable: false,
        locationByPlatform: true,
        iconImage: imageIcon('/pdfstamper-logo.png').image,
        iconImages: [imageIcon('/pdfstamper-logo-90px.png').image]) {


    panel(border: emptyBorder(4),
            background: PdfStamperUIConstants.BACKGROUND) {
        migLayout(layoutConstraints: 'fill')

        // header panel
        widget(build(MainViewHeaderPanel), constraints: 'span 2, grow, wrap')

        // directory selection panel
        widget(build(MainViewDirectorySelectionPanel), constraints: 'grow, wrap, span 2')

        // configuration panel
        widget(build(MainViewConfigurationPanel), constraints: 'grow, span 2,wrap')

        // stamping (= processing) panel
        widget(build(MainViewStampingPanel), constraints: 'grow, span 4, wrap')


        // status line
        // TODO: status bar too big!
        widget(build(MainViewStatusBar), constraints: 'south, grow')

    }

}





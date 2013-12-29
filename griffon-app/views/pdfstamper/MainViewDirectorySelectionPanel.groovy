package pdfstamper

import java.awt.*

final String SRC_DIR_TOOLTIP = """<html>
Select a source directory,<br>
containing the pdf files to be stamped.<br>
The original files will left untouched and <strong>never be modified.</strong>
</html>"""

final String TARGET_DIR_TOOLTIP = """<html>
        Select a target directory, where PdfStamper will place the stamped files. <br>
        Resulting files will be copied (not moved) from source directory.
        </html>"""


directoryPanel = panel(border: lineBorder(color: PdfStamperUIConstants.NICE_BLUE, thickness: 1)) {
    migLayout()

    label('Directories:',
            constraints: "wrap",
            foreground: PdfStamperUIConstants.NICE_BLUE)

    button('Source Directory',
            id: 'srcDirButton',
            constraints: 'skip',
            actionPerformed: controller.selectSrcDir,
            mouseEntered: { srcDirButtonBaloon.visible = true },
            mouseExited: { srcDirButtonBaloon.visible = false })

    textField(columns: 30,
            id: 'sourceDirField',
            text: bind('sourceDir',
                    target: model,
                    id: 'sourceDir'),
            mouseEntered: { srcDirButtonBaloon.visible = true },
            mouseExited: { srcDirButtonBaloon.visible = false }
    )

    balloonTip(srcDirButton,
            id: "srcDirButtonBaloon",
            text: SRC_DIR_TOOLTIP,
            hideAfter: 5000,
            useCloseButton: false)


    label(id: 'nrOfPdfFilesFound',
            foreground: Color.LIGHT_GRAY,
            text: bind { model.nrOfFilesToStamp }
    )

    label(' Pdf files', foreground: Color.LIGHT_GRAY, constraints: 'wrap')


    button('Target Directory',
            id: 'targetDirButton',
            constraints: 'skip',
            actionPerformed: controller.selectTargetDir,
            mouseEntered: { targetDirButtonBaloon.visible = true },
            mouseExited: { targetDirButtonBaloon.visible = false })

    textField(columns: 40,
            constraints: 'wrap',
            id: "targetDir",
            text: bind('targetDir',
                    target: model,
                    id: 'targetDir'),
            mouseEntered: { targetDirButtonBaloon.visible = true },
            mouseExited: { targetDirButtonBaloon.visible = false }
    )


    balloonTip(targetDirButton,
            id: "targetDirButtonBaloon",
            text: TARGET_DIR_TOOLTIP,
            hideAfter: 5000,
            useCloseButton: false)


}


return directoryPanel

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
 *
 *********************************************************************** */


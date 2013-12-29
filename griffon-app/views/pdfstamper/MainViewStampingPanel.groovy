package pdfstamper

import java.awt.*

stampingPanel = panel(border: lineBorder(color: PdfStamperUIConstants.NICE_BLUE, thickness: 1),
        constraints: 'grow, span 4, wrap')
        {
            migLayout()
            label 'Stamping:',
                    constraints: "left, wrap",
                    foreground: PdfStamperUIConstants.NICE_BLUE

            button('Start!',
                    constraints: 'skip, grow, spanx 2',
                    actionPerformed: controller.startStamping,
                    enabled: bind { model.startButtonEnabled }
            )
            label(text: bind { model.totalNrOfPagesSoFar },
                    id: 'NrOfPagesProcessed',
                    foreground: Color.LIGHT_GRAY,
                    constraints: '')
            label(' pages already processed',
                    foreground: Color.LIGHT_GRAY,
                    constraints: 'skip')

            button('Cancel',
                    constraints: 'push, al right',
                    actionPerformed: controller.quit
            )
        }

return stampingPanel

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


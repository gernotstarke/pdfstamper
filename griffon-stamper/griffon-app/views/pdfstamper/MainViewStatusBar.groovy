package pdfstamper

import javax.swing.SwingConstants
import java.awt.*

statusBar = vbox {
    separator()
    panel {
        gridBagLayout()
        label(id: 'status', text: bind { model.status }, foreground: bind { model.statusColor },
                constraints: gbc(weightx: 1.0,
                        anchor: GridBagConstraints.WEST,
                        fill: GridBagConstraints.HORIZONTAL,
                        insets: [1, 3, 1, 3])
        )
        separator(orientation: SwingConstants.VERTICAL, constraints: gbc(fill: GridBagConstraints.VERTICAL))

        jideButton(label: "arc42.org",
                id: 'arc42Button',
                foreground: PdfStamperUIConstants.NICE_BLUE,

                actionPerformed: {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            model.status = "launching browser to display arc42.org website..."
                            model.statusColor = PdfStamperUIConstants.NICE_GREEN
                            desktop.browse(new URI('http://www.arc42.org'))
                        }
                        catch (Exception ex) {
                            log.info("no browser found to display arc42.org website", ex)
                        }
                    }

                })

    }
}


return statusBar
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


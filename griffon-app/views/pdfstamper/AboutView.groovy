/**
 * Copyright 2007-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 */

/**
 * Original @author Andres Almiray
 * slightly adapted by @author Gernot Starke
 */
package pdfstamper

import java.awt.Color
import java.awt.Desktop
import java.awt.Font


spanCount = 4

rowConstraints = "center, span $spanCount, wrap".toString()

panel(id: 'content') {
    migLayout layoutConstraints: 'fill'

    label(icon: imageIcon('/pdfstamper-logo.png'),
            constraints: rowConstraints)

    label(GriffonNameUtils.capitalize(
            app.getMessage('application.title', app.config.application.title)) +
            ' ' + Metadata.current.getApplicationVersion(),
            font: current.font.deriveFont(Font.BOLD),
            constraints: rowConstraints)

    label(text: bind { model.description }, constraints: rowConstraints)


    button(creditsAction, constraints: 'left')

    button(label: "arc42.org",
            foreground: Color.BLUE, constraints: 'center',
            actionPerformed: {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI('http://www.arc42.org'))
                    }
                    catch (Exception ex) {
                    }
                }

            })
    button(licenseAction, constraints: 'right')

    keyStrokeAction(component: current,
            keyStroke: 'ESCAPE',
            condition: 'in focused window',
            action: hideAction)
}

/*
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
 * modified by @author Gernot Starke
 */

package pdfstamper

import java.awt.Color
import java.awt.Desktop

panel(id: 'content') {
    migLayout layoutConstraints: 'fill'


    button(label: "arc42.org",
            foreground: Color.BLUE, constraints: "center, grow, wrap",
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

    scrollPane(constraints: 'grow, wrap') {
        textArea(editable: false, text: bind { model.license },
                caretPosition: bind('license', source: model, converter: {0i}))
    }


    button(hideAction, constraints: 'right')

    keyStrokeAction(component: current,
            keyStroke: 'ESCAPE',
            condition: 'in focused window',
            action: hideAction)
}
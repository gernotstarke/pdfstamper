package pdfstamper

import java.awt.*

final String FILE_PREFIX_TOOLTIP = "The string to be stamped immediately before the pagenumber."

final String PAGE_PREFIX_TOOLTIP =
        """<html>
        Text to be stamped as prefix of the pagenumber,<br>
        e.g. "page 42"
        </html>"""

final String PAGE_NR_POSITION_TOOLTIP =
        """<html>
        Determines the horizontal position of the pagenumber<br>
        with its prefix:<br><ul>
             <li>inside: odd pages: left, even pages: right</li>
             <li>center: file and page will be concatenated and horizontally centered..</li>
             <li>outside (<strong>standard for books</strong>):<br> odd pages: right, even pages: left</li>
        </ul>
        </html>"""

final String FILE_PAGE_SEPARATOR_TOOLTIP =
        """<html>
        Character(s) to be stamped between "page prefix" and "page number",<br>
        e.g. "<strong>,</strong> "
        </html>"""

final String EVENIFY_TOOLTIP =
        """<html>
        Add a blank page, if neccessary,<br>
        so pagecount of file becomes even.<br>
        <br>
        Important if you concatenate the resulting files.

        </html>"""

final String HEADER_TOOLTIP =
        """<html>
        Text to be stamped on the header (centered)<br>
        of every page in every selected file.
        </html>"""


configurationPanel =
        panel(border: lineBorder(color: PdfStamperUIConstants.NICE_BLUE, thickness: 1),
                constraints: '')
                {
                    migLayout()

                    label 'Configuration:',
                            constraints: "wrap",
                            foreground: PdfStamperUIConstants.NICE_BLUE


                    // Header text
                    // ===================================================
                    label 'Header text: ',
                            constraints: 'left, skip',
                            mouseEntered: { headerBaloon.visible = true },
                            mouseExited: { headerBaloon.visible = false }

                    textField(id: 'headerText',
                            columns: 20,
                            constraints: 'span, growx',
                            text: bind(target: model, 'header', value: ""),
                            mouseEntered: { headerBaloon.visible = true },
                            mouseExited: { headerBaloon.visible = false }
                    )
                    balloonTip(headerText,
                            id: "headerBaloon",
                            text: HEADER_TOOLTIP,
                            hideAfter: 5000,
                            useCloseButton: false,
                            style: PdfStamperUIConstants.GREEN_TIP)



                    // File prefix  (e.g. "Chapter")
                    // ===================================================
                    label 'File prefix: ',
                            constraints: 'skip',
                            mouseEntered: { filePrefixBaloon.visible = true },
                            mouseExited: { filePrefixBaloon.visible = false }

                    textField(columns: 15,
                            id: 'filePrefix',
                            constraints: 'wrap',
                            text: bind(target: model, 'filePrefix', value: 'Kapitel'),
                            mouseEntered: { filePrefixBaloon.visible = true },
                            mouseExited: { filePrefixBaloon.visible = false })

                    balloonTip(filePrefix,
                            id: "filePrefixBaloon",
                            text: FILE_PREFIX_TOOLTIP,
                            hideAfter: 5000,
                            useCloseButton: false,
                            style: PdfStamperUIConstants.GREEN_TIP)


                    // Page number prefix (e.g. "Seite"
                    // ===================================================

                    label 'Page number prefix: ',
                            constraints: 'skip',
                            mouseEntered: { pagePrefixBaloon.visible = true },
                            mouseExited: { pagePrefixBaloon.visible = false }

                    textField(columns: 15,
                            id: 'pagePrefix',
                            constraints: 'wrap',
                            text: bind(target: model, 'pagePrefix', value: 'Seite'),
                            mouseEntered: { pagePrefixBaloon.visible = true },
                            mouseExited: { pagePrefixBaloon.visible = false }
                    )

                    balloonTip(pagePrefix,
                            id: "pagePrefixBaloon",
                            text: PAGE_PREFIX_TOOLTIP,
                            hideAfter: 5000,
                            useCloseButton: false,
                            style: PdfStamperUIConstants.GREEN_TIP)


                    // Page number position
                    // ===================================================

                    label 'Page number position:',
                            constraints: 'skip',
                            mouseEntered: {pageNrPositionBaloon.visible = true},
                            mouseExited: {pageNrPositionBaloon.visible = false}


                    comboBox(items: HorizontalPositions.positions,
                            id:'pageNrPosition',
                            selectedIndex: HorizontalPositions.OUTSIDE,
                            selectedItem: bind(
                                            target: model,
                                            targetProperty: 'pageNumberHorizontalPosition'),
                            constraints: 'wrap',
                            mouseEntered: {pageNrPositionBaloon.visible = true},
                            mouseExited: {pageNrPositionBaloon.visible = false})

                    balloonTip( pageNrPosition,
                            id: "pageNrPositionBaloon",
                            text: PAGE_NR_POSITION_TOOLTIP,
                            useCloseButton: false,
                            style: PdfStamperUIConstants.GREEN_TIP)


                    // File / Page separator
                    // ===================================================

                    label('File / Page Separator: ',
                            constraints: 'skip',
                            visible:  bind { model.footerIsCentered },
                            mouseEntered: { filePageSeparatorBaloon.visible = true },
                            mouseExited: { filePageSeparatorBaloon.visible = false }
                    )

                    textField(id: 'filePageSeparator',
                            columns: 4,
                            constraints: '',
                            text: bind(target: model, 'filePageSeparator', value: ', '),
                            visible: bind { model.footerIsCentered},
                            mouseEntered: { filePageSeparatorBaloon.visible = true },
                            mouseExited: { filePageSeparatorBaloon.visible = false }
                    )


                    /*label('e.g.',
                            foreground: Color.LIGHT_GRAY,
                            constraints: 'skip',
                            visible: bind { model.footerIsCentered })

                    label(text: bind(source: model,
                                    sourceProperty: 'sampleFooter',
                                    sourceEvent: 'propertyChange',
                                    converter: model.calcSampleFooter),
                            foreground: Color.LIGHT_GRAY,
                            constraints: 'wrap',
                            visible: bind { model.footerIsCentered }
                    )*/

                    balloonTip(filePageSeparator,
                            id: "filePageSeparatorBaloon",
                            text: FILE_PAGE_SEPARATOR_TOOLTIP,
                            hideAfter: 5000,
                            useCloseButton: false )





                    // evenify flag
                    // ===================================================

                    label 'Evenify:', constraints: ''
                    checkBox(id: 'evenifyCheck',
                            selected: bind(target: model, 'evenify', value: true),
                            constraints: '',
                            mouseEntered: { evenifyBaloon.visible = true },
                            mouseExited: { evenifyBaloon.visible = false })

                    textField(id: 'blankPageText',
                            columns: 25,
                            constraints: 'wrap',
                            text: bind(target: model, 'blankPageText', value: 'Diese Seite bleibt absichtlich frei '),
                            visible: bind { model.evenify},
                            mouseEntered: { filePageSeparatorBaloon.visible = true },
                            mouseExited: { filePageSeparatorBaloon.visible = false }
                    )


                    balloonTip(evenifyCheck,
                            id: "evenifyBaloon",
                            text: EVENIFY_TOOLTIP,
                            hideAfter: 10000,
                            useCloseButton: false,
                            style: PdfStamperUIConstants.RED_TIP)





                    // prepare for additional security options
                    // checkBox('disable copy from Pdf', constraints: 'skip, wrap'  )
                }

// return the panel, so that the complete view can render it...
return configurationPanel

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


package pdfstamper

import com.itextpdf.text.Element
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.awt.geom.Point

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

class JoinedFooterStamper extends SingleFileStamper {

    /**
     * create an instance to separately stamp file-info and page-info
     * @param configuration
     */
    public JoinedFooterStamper( StamperConfiguration configuration) {
        super( configuration )
        this.processingState.resetProcessingState()
    }


    @Override
    /**
     * this instance is created if pagenumber shall be stamped centered,
     * therefore page- and file-information will simply be joined.
     */
    void stampFooter( PdfContentByte canvas, Rectangle pageSize) {
        println "stamping a joined and centered footer...: " + createJoinedFooter()

        // create the footer text
        String footerText = createJoinedFooter()

        stampCenteredFooter( canvas, pageSize, footerText )

    }

    /*
     * The joined footer consists of file-prefix, file-number, file-page-separator + page-prefix + current page number
     */
    private String createJoinedFooter() {
        return joinFilePrefixAndNumber() +
               config.getFilePageSeparator() +
               joinPagePrefixAndNumber()
    }


}

package pdfstamper

import com.itextpdf.awt.geom.Point
import com.itextpdf.text.Element
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfContentByte

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

class PartitionedFooterStamper extends SingleFileStamper {

    /**
     * instances need a StamperConfiguration, otherwise they don't know what to stamp,
     * and in what orientation.
     *
     * Instance is created only ONCE per stamping-job - therefore remains identical for several files.
     **/
    protected PartitionedFooterStamper(StamperConfiguration configuration) {
        super(configuration)
    }


    @Override
    void stampFooter(PdfContentByte canvas, Rectangle pageSize) {

        // the easy part:
        stampFileInfo(canvas, pageSize)

        // the harder part: page-prefix and page-number
        // are stamped left- and right in alternation
        stampPageInfo(canvas, pageSize)
    }


    /*
     * Only this method knows about inside, outside, left or right on a page.
     * Page info is always stamped on the bottom of a page.
     * Left- or right orientation depends on odd/even page, as follows:
     * * even page, inside-orientation: page-nr stamped on right
     * * odd page, inside-orientation:  page-nr stamped on left
     * * even page, outside-orientation:  page-nr stamped on left
     * * odd page, outside-orientation: page-nr stamped on right
     */

    protected void stampPageInfo(PdfContentByte canvas, Rectangle pageSize) {
      // TODO this code is ugly - refactor to something more readable

        if (isEven(processingState.totalNumberOfPagesSoFar)) {

            if (config.pageNumberHorizontalPosition == HorizontalPositions.positions[HorizontalPositions.INSIDE])
                stampFooterOnRight(canvas, pageSize)
            else if (config.pageNumberHorizontalPosition == HorizontalPositions.positions[HorizontalPositions.OUTSIDE])
                stampFooterOnLeft(canvas )
        } else { // page number is odd
            if (config.pageNumberHorizontalPosition == HorizontalPositions.positions[HorizontalPositions.INSIDE])
                stampFooterOnLeft(canvas )
            else if (config.pageNumberHorizontalPosition == HorizontalPositions.positions[HorizontalPositions.OUTSIDE])
                stampFooterOnRight(canvas, pageSize)
            else println "no pageinfo stamped, something went wrong"
        }
    }

    protected void stampFooterOnRight(PdfContentByte canvas, Rectangle pageSize) {

        Point footerPosition = PositionsOnPage.calculateOutsideFooterPosition( pageSize )
        stampTextOnPage( canvas, footerPosition, Element.ALIGN_RIGHT, joinPagePrefixAndNumber())
    }

    protected void stampFooterOnLeft( PdfContentByte canvas ) {
        Point footerPosition = PositionsOnPage.calculateInsideFooterPosition()
        stampTextOnPage( canvas, footerPosition, Element.ALIGN_LEFT, joinPagePrefixAndNumber())

    }

    private boolean isEven( int nr ) {
      return (nr % 2) == 0
    }


    /*
    * FileInfo consists of the file-prefix plus the number of the current file.
    * Both values are contained in the PdfStamperModel.
    */

    private void stampFileInfo(PdfContentByte canvas, Rectangle pageSize) {
        // centered footer text consists of file-prefix and file-number
        String centeredFooterText = joinFilePrefixAndNumber()

        // stamp file and page info
        stampCenteredFooter(canvas, pageSize, centeredFooterText)

    }

}


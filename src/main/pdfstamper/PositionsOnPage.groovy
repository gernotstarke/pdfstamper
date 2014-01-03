package pdfstamper

import com.itextpdf.awt.geom.Point
import com.itextpdf.text.Rectangle

/**
 * Constants to help calculate positions to stamp
 */
class PositionsOnPage {

    public static final int OFFSET_FROM_TOP_OF_PAGE = 30
    public static final int OFFSET_FROM_BOTTOM_OF_PAGE = 15

    public static final int OFFSET_FROM_INSIDE = 50 // stamp with LEFT-alignment
    public static final int OFFSET_FROM_OUTSIDE = 20 // stamp with RIGHT-alignment

    /**
     * @param float pageWidth
     *
     * @return Point (bottom-centered with y-offset)
     */
    public static Point calculateCenteredFooterPosition( float pageWidth) {
        return new Point( x: (int) pageWidth / 2,
                          y: OFFSET_FROM_BOTTOM_OF_PAGE)
    }

    /**
     * Centered-header has some offset from top_of_page.
     * @param pageSize
     * @return
     */
    public static Point calculateCenteredHeaderPosition( Rectangle pageSize) {
        float pageWidth = pageSize.getWidth()
        float pageHeight = pageSize.getHeight()

        return new Point( x: (int) pageWidth / 2,
                          y: (int) (pageHeight- OFFSET_FROM_TOP_OF_PAGE))
    }

    /**
     * Inside-footer has some offset from the bottom and the left
     */
    public static Point calculateInsideFooterPosition(  ) {
        return new Point( x: (int) OFFSET_FROM_INSIDE,
                          y: (int) OFFSET_FROM_BOTTOM_OF_PAGE)
    }

    /**
     * Outside-footer has offset from bottom and right
     */
    public static Point calculateOutsideFooterPosition( Rectangle pageSize) {
        return new Point( x: (int) (pageSize.getWidth() - OFFSET_FROM_OUTSIDE),
                          y: (int) OFFSET_FROM_BOTTOM_OF_PAGE )
    }
}


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

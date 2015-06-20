package org.arc42.util

import com.itextpdf.awt.geom.Point

/**
 * Defines some standard positions for text on pages, mainly for A4 format.
 * User: gstarke
 *
 */
class WellKnownPositions {

    final static private double A4_PORTRAIT_CENTER = 595/2

    // the lowest y-position on the page where we stamp text
    final static private double BOTTOM = 15.0

    // the highest y-position
    final static private double A4_PORTRAIT_TOP = 825.0

    // left position
    final static private double A4_PORTRAIT_LEFT = 15

    // right position
    final static private double A4_PORTRAIT_RIGHT = 550

    /*************************************************************/


    /**
     * Point denoting the center of the bottom line on an A4 portrait page
     **/
    final static public Point A4_PORTRAIT_BOTTOM_CENTER =
        new Point(  A4_PORTRAIT_CENTER, BOTTOM )


    /**
     * Point denoting the center of the top line on an A4 portrait page
     **/
    final static public Point A4_PORTRAIT_TOP_CENTER =
        new Point( A4_PORTRAIT_CENTER, A4_PORTRAIT_TOP )

    /**
     * Point denoting the left part of the bottom line on an A4 portrait page.
     * Should be used with RIGHT alignment.
     * @see A4_PORTRAIT_BOTTOM_RIGHT
    **/
    final static public Point A4_PORTRAIT_BOTTOM_LEFT =
        new Point( A4_PORTRAIT_LEFT, BOTTOM)


    /**
     * Point denoting the right boundary of the bottom line on an A4 portrait page
     * Should be used with RIGHT alignment
     **/
     final static public Point A4_PORTRAIT_BOTTOM_RIGHT =
         new Point( A4_PORTRAIT_RIGHT, BOTTOM)

}

package pdfstamper

import net.java.balloontip.styles.BalloonTipStyle
import net.java.balloontip.styles.ModernBalloonStyle

import java.awt.*

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

class PdfStamperUIConstants {
    public static Color NICE_BLUE = new Color(15, 120, 180)
    public static Color NICE_GREEN = new Color(20, 150, 30)

    public static Color BACKGROUND = new Color(238, 238, 238)

    public static Color HEADER_SHADE_START = new Color(244, 244, 244)

    public static Color TITLECOLOR = new Color(19, 56, 160)
    public static Color SUBTITLE = NICE_BLUE

    public static Color WARNING = new Color(155, 20, 20)


    /*  ModernBaloonTipStyle constructor:
     *
     * @param arcWidth
     * @param arcHeight
     * @param topFillColor
     * @param bottomFillColor
     * @param borderColor
     */
    public static BalloonTipStyle GREEN_TIP =
            new ModernBalloonStyle(8, 8, HEADER_SHADE_START, NICE_GREEN, BACKGROUND)

    public static BalloonTipStyle RED_TIP =
            new ModernBalloonStyle(8, 8, HEADER_SHADE_START, Color.RED, BACKGROUND)

    public static BalloonTipStyle BLUE_TIP =
            new ModernBalloonStyle(8, 8, Color.WHITE, NICE_BLUE, BACKGROUND)

}

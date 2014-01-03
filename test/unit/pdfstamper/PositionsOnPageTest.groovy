package pdfstamper

import com.itextpdf.awt.geom.Point
import com.itextpdf.text.PageSize
import com.itextpdf.text.Rectangle
import org.junit.Before

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

class PositionsOnPageTest extends GroovyTestCase {

    Rectangle a4Size

    @Before
    void setup() {
        }

    void testCalculateCenteredFooterPosition() {

    }

    void testCalculateCenteredHeaderPosition() {

    }

    void testCalculateInsideFooterPosition() {
        a4Size = new Rectangle( PageSize.A4 )

        Point footerPosition = PositionsOnPage.calculateInsideFooterPosition()

        Point expectedInsideFooterPosition = new Point( 50, 15)

        assertEquals( "inside footer position does not match", expectedInsideFooterPosition, footerPosition)

    }

    void testCalculateOutsideFooterPosition() {
        a4Size = new Rectangle( PageSize.A4 )

        assertEquals( "A4 width should equal approx 595", 595, a4Size.getWidth(), 1.0)
        assertEquals( "A4 height should equal approx 842", 842, a4Size.getHeight(), 0.5)


        Point footerPosition = PositionsOnPage.calculateOutsideFooterPosition( a4Size )

        Point expectedOutsideFooterPosition = new Point( 575, 15)

        assertEquals( "outside footer position does not match", expectedOutsideFooterPosition, footerPosition)


    }
}

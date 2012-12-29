package org.arc42.pageNumberizer.test

import org.arc42.pageNumberizer.WellKnownPositions
import spock.lang.Specification

/**
 * Kind of funny acceptance test. We want to make sure our constant definitions work as expected
 * User: gstarke
 * Date: 27.12.12
 * Time: 21:29
 *
 */
class WellKnownPositionSpec extends Specification {

    def "Well known Positions should be proper Points"() {
        expect: "centered text at the bottom of A4 portrait pages should be located properly"

            15.0 == WellKnownPositions.A4_PORTRAIT_BOTTOM_CENTER.getY()
            (595/2)-1 <  WellKnownPositions.A4_PORTRAIT_BOTTOM_CENTER.getX()
            (595/2)+1 >  WellKnownPositions.A4_PORTRAIT_BOTTOM_CENTER.getX()


    }
}

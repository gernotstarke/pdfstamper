package org.arc42.pageNumberizer.test

import org.arc42.pageNumberizer.PdfPageNumberizer
import spock.lang.Specification

/**
 * trivial specification: We want optional prefix strings, offsets and pagenumbers
 * to be handled properly
 * Date: 29.12.12
 * Time: 08:25
 *
 */
class TextToStampSpec extends Specification {


    def "properly combine prefix and pagenumbers "(int currentPage, int offset, String prefix, String result) {

        given: "a pageNumberizer instance"
            def PdfPageNumberizer pdfPageNumberizer = new PdfPageNumberizer()

        expect:
            println( currentPage + "-" + offset + prefix )
            result == pdfPageNumberizer.createStringToStampOnPage(currentPage, offset, prefix)

        where:
            currentPage | offset | prefix || result
                      1 |      0 |     "" ||    "1"
                      1 |      1 |     "" ||    "2"
                      2 |      1 |     "" ||    "3"
                     11 |      0 |     "" ||   "11"
                     11 |      1 |     "" ||   "12"
                     11 |     11 |     "" ||   "22"
                     11 |     11 |     "" ||   "22"
                     11 |     11 |   "C-" || "C-22"
                     11 |     11 |   null ||   "22"


    }
}

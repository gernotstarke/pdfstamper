package org.arc42.pageNumberizer

import javax.swing.JFileChooser


/**
 * Numberize a set of pdf files (aka "handout") with pagenumbers and optional
 * additional information.
 * @author Gernot Starke
 *
 * Date: 25.12.12
 * Time: 22:51
 *
 */

class NumberizeHandout {

    // prefer to have a main method
    static main(args) {
        PdfPageNumberizer pdfPageNumberizer1 = new PdfPageNumberizer()


        def openDirectoryDialog = new JFileChooser(
                dialogTitle: "Choose a source directory file",

                fileSelectionMode: JFileChooser.DIRECTORIES_ONLY )

        openDirectoryDialog.showOpenDialog()

    }
}

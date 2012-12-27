package org.arc42.util

import java.util.regex.Pattern

/**
 * Helper class for file handling
 * User: gstarke
 * Date: 26.12.12
 * Time: 09:26
 *
 */
class FileUtil {


    /**
     *
     * @param pattern @param inpuPathname
     * @return String[] of pdf files within inputputPathname
     */
    static String[] findFilesToProcess(String inputPathname, final Pattern pattern) {
        // myLogger.debug "finding files in " + inputPathname

        File inputPath = new File(inputPathname)

        def pdfFilesToProcess = []

        // if inputPathname is a directory, get all contained pdf files
        if (inputPath.directory) {
            inputPath.eachFileMatch(pattern) {
                pdfFilesToProcess << it.name
            }
        }
        else {
            pdfFilesToProcess << inputPathname
        }
        //myLogger.info "found these pdf files: " + pdfFilesToProcess

        return pdfFilesToProcess
    }

    /**
     *
     * @param directory where to search for files
     * @return a String[] of pdf filenames in the directory
     */
    static String[] findPdfFilesInDirectory( String directory ) {
        return findFilesToProcess(directory, ~/.*\.pdf$/)

    }

}

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
 *********************************************************************** */

/*
 * @author Gernot Starke
 *
 */


package org.arc42.util

import java.util.regex.Pattern

/**
 * very simple helper class for file handling
 *
 */

class FileUtil {


    /**
        * returns number of Pdf files found in given directory
        * @param directory
        * @return
        */
       public static int nrOfPdfFilesInDirectory(String directory) {
           return findPdfFilesInDirectory(directory).length

       }

     /**
      *
      * @param directory where to search for files
      * @return a String[] of pdf filenames in the directory
      */
     public static String[] findPdfFilesInDirectory( String directory ) {
         return findFilesToProcess(directory, ~/.*\.pdf$/)

     }



    /**
     *
     * @param pattern @param inpuPathname
     * @return String[] of pdf files within inputputPathname
     */
    public static String[] findFilesToProcess(String inputPathname, final Pattern pattern) {
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
     *  @return the full path name by appending the fileName after "/" after the path.
     */
    public static String fullPathFileName( String path, String fileName ) {
        return path + "/" + fileName
    }

}

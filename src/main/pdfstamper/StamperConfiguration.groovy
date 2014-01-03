package pdfstamper

/**
 *  configuration items for stamping pdf files:
 *
 */
class StamperConfiguration {


    String sourceDir
    String targetDir

    // header configuration
    // ************************************************
    String header

    // footer configuration settings
    // ************************************************

    String pagePrefix = "Seite"
    String filePageSeparator = ", "
    String filePrefix = "Kapitel"


    // other configuration options
    //*************************************************
    Boolean evenify    // ensure that every file has EVEN number of pages

    // either CENTER, OUTSIDE or INSIDE
    String pageNumberHorizontalPosition



    /**
     * default constructor
     **/
    public StamperConfiguration() {
        super()
    }

    /**
     * full constructor
     *
     * @return
     */
    public StamperConfiguration( String sourceDir,
                                 String targetDir,
                                 String header,
                                 String pagePrefix,
                                 String filePrefix,
                                 String filePageSeparator,
                                 String pageNumberHorizontalPosition,
                                 boolean evenify
                                 )   {
        this.sourceDir = sourceDir
        this.targetDir = targetDir
        this.header = header
        this.pagePrefix = pagePrefix
        this.pageNumberHorizontalPosition = pageNumberHorizontalPosition
        this.filePrefix = filePrefix
        this.filePageSeparator = filePageSeparator
        this.evenify = evenify
    }

    public resetToDefaultConfiguration() {

        sourceDir = ""
        targetDir = ""
        header = ""

        pagePrefix = "Seite"
        filePageSeparator = ", "
        filePrefix = "Kapitel"

        pageNumberHorizontalPosition = HorizontalPositions.positions[HorizontalPositions.OUTSIDE]

        evenify = true    // ensure that every file has EVEN number of pages

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



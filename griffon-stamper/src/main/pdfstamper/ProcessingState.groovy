package pdfstamper

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

class ProcessingState {

    int currentFileNumber

    int totalNumberOfPagesSoFar

    long startTime

    long processingTimeMillis = 0


    /**
     * initialize instance
     */
    public ProcessingState() {
        super()
        this.resetProcessingState()
    }

    /**
     * reset the processing state
     */
    public void resetProcessingState() {
        currentFileNumber = 0
        totalNumberOfPagesSoFar = 0

        startTime = System.currentTimeMillis()
        processingTimeMillis = 0
    }

    /**
     * calculate the milliseconds between start and stop...
     */
    public void stopTimer() {
        processingTimeMillis = System.currentTimeMillis() - startTime
    }

    /**
     * increment the pagecount
     */
    public void incrementTotalPageCount() {
        totalNumberOfPagesSoFar += 1
    }

    public void incrementCurrentFileNumber() {
        currentFileNumber += 1
    }

    public String getElapsedTime() {
        return processingTimeMillis.toString()
    }


    /**
     * generate some statistics from processingState
     */
    public String summarizeProcessingResults() {
        String processedPages = totalNumberOfPagesSoFar + " pages "
        String processedFiles = "in " + currentFileNumber + " files processed in "
        String milliseconds =  getElapsedTime() + "msec"

        return processedPages + processedFiles + milliseconds
    }

}

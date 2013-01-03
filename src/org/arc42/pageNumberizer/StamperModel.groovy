package org.arc42.pageNumberizer

/**
 * Domain model of the stamper/numberizer app.
 *
 * Our domain holds a few configuration items for the rest of the app.
 *
 * @author Gernot Starke
 *
 * Date: 03.01.13
 * Time: 09:31
 *
 */
class StamperModel {

    /**
     * where to read the source pdf files.
     * All pdf's found here will be processes (stamped, numberized),
     * but NONE of the original files will be modified.
     */
    private String sourceDir

    /**
     * where to write the stamped/numberized pdf files
     * Must be a valid directory in local file system
     */
    private String targetDir



    /**
     *
     * @param p_source
     * @param p_target
     */

    public StamperModel( String p_source, String p_target) {

        sourceDir = p_source
        targetDir = p_target


    }

    public StamperModel() {
        sourceDir = "."
        targetDir = "."
    }


    public String getSourceDir() {
        return sourceDir ?: "./"
    }


    public String getTargetDir() {
        return targetDir ?: "./"
    }

    public String setSourceDir( String p_Dir ) {
        sourceDir = p_Dir
    }

    public String setTargetDir( String p_Dir ) {
        targetDir = p_Dir
    }
}

/*
 * Copyright 2007-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 */

/**
 * Original @author Andres Almiray
 * Modified by @author Gernot Starke
 */

package pdfstamper

import groovy.beans.Bindable

class LicenseModel extends AbstractDialogModel {
    @Bindable String license

    private static String LICENSE = null

    void mvcGroupInit(Map<String, Object> args) {
        super.mvcGroupInit(args)
        width = 600
        height = 320
        resizable = false
        license = fetchLicenseText()
    }

    protected String getDialogKey() { 'License' }

    protected String getDialogTitle() { 'License' }

    @groovy.transform.Synchronized
    static fetchLicenseText() {
        if (LICENSE == null) {
            try {
                LICENSE = getClass().getResource('/license.txt').text
            } catch (x) {
                LICENSE = 'Apache 2.0 License - free for use, but not guarantee'
            }
        }
        LICENSE
    }
}
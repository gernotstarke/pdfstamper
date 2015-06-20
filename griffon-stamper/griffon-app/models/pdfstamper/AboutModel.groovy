/**
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
 * slightly adapted by @author Gernot Starke
 */


package pdfstamper

import groovy.beans.Bindable


class AboutModel extends AbstractDialogModel {
    @Bindable String description

    protected String getDialogKey() { 'About' }

    protected String getDialogTitle() { 'About PdfStamper' }

    void mvcGroupInit(Map<String, Object> args) {
        super.mvcGroupInit(args)
        resizable = false
        description = '''
            <html><br/><p>
            PdfStamper adds page- and chapter numbers<br/>
            to multiple pdf files, e.g. for course handouts.<br/>
            <p><br/>
            It's based on ideas from Andres Almiray<br/>
            Griffon, iTextPdf, MigLayout, Jide-Builder and<br/>
            Groovy. <p><br/>
            Developed with pleasure on IntelliJ!<br/>
            ...by Gernot Starke and arc42.org <br/>
            <br/></p></html>
        '''.stripIndent(12).trim()


    }

}
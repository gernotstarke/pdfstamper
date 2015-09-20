//
//  PdfStamperModel.swift
//  pdfstamper
//
//  Created by Dr. Gernot Starke on 20.09.15.
//  Copyright © 2015 Dr. Gernot Starke. All rights reserved.
//

import Foundation

/***********************************************************

Model (state and data) for PdfStamper.

Contains required state and data for stamping several pdf files.

## state info:
* source directory valid
* target directory valid
* configuration valid

## data:
* source directory URL / string
* target directory URL / string

* sourceFiles (each of those has a corresponding target file)

* total pagecount
* current pageNr

************************************************************/

class PdfStamperModel {

    /// source directory stuff
    var sourceDirValid: Bool
    var sourceDirURL: NSURL
    var sourceDirName: String

    
    /// target directory stuff
    var targetDirValid: Bool
    var targetDirURL: NSURL
    var sourceDirName: String
    
    
    /// constructor
    init() {
        sourceDirValid = false
        targetDirValid = false
        
        print("PdfStamperModel created")
    }
}

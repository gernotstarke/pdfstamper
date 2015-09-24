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
    
    var sourceDirValid: Bool {
        return FileUtil.isDirectory( sourceDirURL.path! )
    }
    
    var targetDirValid: Bool {
        return FileUtil.isDirectory( targetDirURL.path! )
    }
    
    
    var sourcePdfFileSet: [NSURL] {
        get {
            return FileUtil.collectPdfFiles( sourceDirURL )
        }
    }
    
//    var targetPdfFileSet: [NSURL] {
//        ge
//    }
    
    // can we safely create the files in the target directory?
    // conditions:
    // 1.) target directory is empty OR
    // 2.) overwrite is allowed
    var targetClean: Bool {
        get {
            return false
        }
    }
    
    var sourceDirURL: NSURL {
        didSet {
            
        }
    }
    var targetDirURL: NSURL {
        didSet {
            
        }
    }
    
    
    var sourceDirStatusText: String
    var targetDirStatusText: String
    
    
    /// constructor
    init() {
        sourceDirURL = NSURL(fileURLWithPath: "")
        targetDirURL = NSURL(fileURLWithPath: "")
        
        sourceDirStatusText = ""
        targetDirStatusText = ""
        
        print("PdfStamperModel created")
    }
    
    /// are all conditions fulfilled, so we can actually stamp?
    /// func is called when directories are changed to en/disable start button
    /// -------------------------------------------------------
    func isStampingAllowed() -> Bool {
        
        let conditions = [
            sourceDirValid,
            targetDirValid]
        
        return conditions.reduce( true ) {
            $0 && $1
        }
        
    }
    
    /// status text for directory-text fields
    /// "no source directory selected"
    
    
}

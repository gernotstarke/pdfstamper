//
//  FileUtil.swift
//  pdfstamper
//
//  Created by Dr. Gernot Starke on 20.09.15.
//  Copyright © 2015 Dr. Gernot Starke. All rights reserved.
//

import Foundation

class FileUtil {
    
    /// check if directory exists
    static func isDirectory(path: String) -> Bool {
        let fm = NSFileManager.defaultManager()
        
        do {
            let _ = try fm.contentsOfDirectoryAtPath(path)
            return true
        }
            catch {
                // error reading directory
                return false
        }
    }
    
    
    // count nr of pdf files
    // =====================
    static func nrOfPdfFilesInFolder( folderPath: NSURL ) -> Int {
        var count = 0
        
        let fileManager = NSFileManager.defaultManager()
        let keys = [NSURLIsDirectoryKey]
        
        let enumerator:NSDirectoryEnumerator? = fileManager.enumeratorAtURL(
            folderPath,
            includingPropertiesForKeys: keys,
            options: NSDirectoryEnumerationOptions(),
            errorHandler: nil)
      
        while let url = enumerator!.nextObject() as! NSURL! {
          if isPdfFile(url.path! ) {
            count += 1
          }
        }
        
        return count
    }
  
  
  // collect pdf files from directory
  static func collectPdfFiles() -> [NSURL] {
    return []
  }
 
  
  // check if filename has pdf extension
  // ===================================
  static func isPdfFile( filePath: String) -> Bool {
    let upperCaseFileName = filePath.uppercaseString
    return (upperCaseFileName.hasSuffix("PDF"))
  }
  
}

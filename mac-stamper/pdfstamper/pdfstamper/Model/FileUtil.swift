//
//  FileUtil.swift
//  pdfstamper
//
//  Created by Dr. Gernot Starke on 20.09.15.
//  Copyright © 2015 Dr. Gernot Starke. All rights reserved.
//

import Foundation

class FileUtil {
    
    // 
    static let pdfExtensions = ["pdf", "PDF"]
    
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
    
    // TODO: restrict to pdf files!!
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
            count += 1
        }
        
        return count
    }
 
    
}

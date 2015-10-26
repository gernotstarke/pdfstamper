//
//  pdfstamperTests.swift
//  pdfstamperTests
//
//  Created by Dr. Gernot Starke on 20.06.15.
//  Copyright (c) 2015 Dr. Gernot Starke. All rights reserved.
//

import Cocoa
import XCTest

@testable import pdfstamper

class pdfstamperTests: XCTestCase {
    
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testExample() {
        // This is an example of a functional test case.
        XCTAssert(true, "Pass")
    }
    
    // if no directory has been selected,
    // it should always be invalid!
    func emptyDirIsNotValid() {
        var myModel = PdfStamperModel()
        myModel.sourceDirURL = NSURL( fileURLWithPath: "")
        
        XCTAssertEqual(myModel.sourceDirValid, false, "empty dir name should be invalid")
        
    }
    
}

//
//  RootViewController.swift
//  pdfstamper
//
//  Created by Dr. Gernot Starke on 21.06.15.
//  Copyright (c) 2015 Dr. Gernot Starke. All rights reserved.
//

import Cocoa

class RootViewController: NSViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do view setup here.
    }
    
    override func awakeFromNib() {
        println("(arc42) View controller instance with view: \(self.view)")
    }
    
}

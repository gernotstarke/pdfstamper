//
//  AppDelegate.swift
//  pdfstamper
//
//  Created by Dr. Gernot Starke on 20.06.15.
//  Copyright (c) 2015 Dr. Gernot Starke. All rights reserved.
//

import Cocoa

@NSApplicationMain
class AppDelegate: NSObject, NSApplicationDelegate {

    @IBOutlet weak var window: NSWindow!

  
    func applicationDidFinishLaunching(aNotification: NSNotification) {
        // Insert code here to initialize your application
        
        print("AppDelegate: DidFinishLaunching")

    }

    func applicationWillTerminate(aNotification: NSNotification) {
        // Insert code here to tear down your application
        print("AppDelegate: applicationWillTerminate")
    }


}


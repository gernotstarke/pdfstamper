//
//  RootViewController.swift
//  pdfstamper
//
//  Created by Dr. Gernot Starke, June-Sept 2015.
//  Copyright (c) 2015 Dr. Gernot Starke. All rights reserved.
//

import Cocoa

class RootViewController: NSViewController {

    // application name and version, to be shown in footer of view
    // (in versionLabel)
    let appName: String = "org.arc42.PdfStamper"
    var appVersionInfo: String = "-.-.-"
    
    
    // MARK labels and textfields
    
    @IBOutlet weak var sourceDirTextField: NSTextField!
    @IBOutlet weak var sourceDirStatusLabel: NSTextField!
    var sourceDirStatusLabelColor: NSColor! = NSColor.redColor()
    
    @IBOutlet weak var targetDirTextField: NSTextField!
    @IBOutlet weak var targetDirStatusLabel: NSTextField!
    var targetDirStatusLabelColor: NSColor! = NSColor.redColor()

    @IBOutlet weak var versionLabel: NSTextField!
    
    @IBOutlet weak var arc42Label: NSButton!
    
    @IBOutlet weak var startButton: NSButtonCell!
    
    
    // MARK button actions
    // ===================
    @IBAction func sourceDirButton(sender: AnyObject) {
        selectSourceDirectory()
    }
    
    @IBAction func targetDirButton(sender: AnyObject) {
        selectTargetDirectory()
    }
    
    @IBAction func startButton(sender: AnyObject) {
    }
    
    @IBAction func cancelButton(sender: AnyObject) {
    }
   
    @IBAction func arc42LabelButton(sender: AnyObject) {
        print("arc42 label clicked")
    }
    
    
    
    // MARK override funcs
    // ====================
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setVersionLabel()
        
        disableStartButton()
        
        setSourceDirStatusLabel("no source directory selected")
        setTargetDirStatusLabel("no target directory selected")
        
    }

    
    override func awakeFromNib() {
    
        print("(arc42) View controller instance with view: \(self.view)")
    }
    
    
    // open directory chooser (NSOpenPanel)
    // ====================================
    private func selectSourceDirectory() {
        let openPanel = directoryOpenPanel("Select Source Directory")
        openPanel.beginWithCompletionHandler( {(result:Int) in
            if(result == NSFileHandlingPanelOKButton)
            {
                let fileURL = openPanel.URL!
                print("path = \(fileURL.path!)")
                
                // Now we have a directory selected. Its URL = fileURL
                self.sourceDirTextField.stringValue = "\(fileURL.path!)"
                self.sourceDirStatusLabelColor = NSColor.blueColor()
                self.setSourceDirStatusLabel("\(self.nrOfPdfFilesInFolder( fileURL )) pdf files found")
                
            }
        })
    }
    
    private func selectTargetDirectory() {
        let openPanel = directoryOpenPanel("Select Target Directory")
        openPanel.beginWithCompletionHandler( {(result:Int) in
            if(result == NSFileHandlingPanelOKButton)
            {
                let fileURL = openPanel.URL!
                print("path = \(fileURL.path!)")
                
                // Now we have a directory selected. Its URL = fileURL
                self.targetDirTextField.stringValue = "\(fileURL.path!)"
                self.targetDirStatusLabelColor = NSColor.blueColor()
                self.setTargetDirStatusLabel("\(self.nrOfPdfFilesInFolder( fileURL )) pdf files found")
                
            }
        })
    }
    
    // configure the directory open panel
    // ----------------------------------
    private func directoryOpenPanel( title: String ) -> NSOpenPanel {
        let openPanel = NSOpenPanel()
        openPanel.title = title
        openPanel.canChooseDirectories = true
        openPanel.allowsMultipleSelection = false
        openPanel.canCreateDirectories = false
        openPanel.canChooseFiles = false
 
        return openPanel
    }
    
    // count nr of pdf files
    // =====================
    private func nrOfPdfFilesInFolder( folderPath: NSURL ) -> Int {
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

    
    // initialization functions
    // ========================
    private func setVersionLabel() {
        // read current version info from application bundle
        let infoDictionary: NSDictionary = NSBundle.mainBundle().infoDictionary as NSDictionary!
        let shortVersionString = infoDictionary["CFBundleShortVersionString"] as! String
        
        versionLabel.stringValue = "\(appName), version \(shortVersionString)"
    }
    
    private func disableStartButton() {
        startButton.enabled = false
    }
    
    
    private func setSourceDirStatusLabel( sourceDirStatus: String ) {
        setLabelTextAndColor( sourceDirStatusLabel, text: sourceDirStatus, color: sourceDirStatusLabelColor)
    }
    
    private func setTargetDirStatusLabel( targetDirStatus: String) {
        setLabelTextAndColor( targetDirStatusLabel, text: targetDirStatus, color: targetDirStatusLabelColor)
        
    }
    
    private func setLabelTextAndColor( label: NSTextField!, text: String, color: NSColor!) {
        label.stringValue = text
        label.textColor = color
    }
}

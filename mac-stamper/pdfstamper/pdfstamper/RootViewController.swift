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
    
    
    var myModel: PdfStamperModel = PdfStamperModel.init()
    
    // MARK labels and textfields
    
    @IBOutlet weak var sourceDirTextField: NSTextField!
    @IBOutlet weak var sourceDirStatusLabel: NSTextField!
    var sourceDirStatusLabelColor: NSColor! = NSColor.redColor()
    
    @IBOutlet weak var targetDirTextField: NSTextField!
    @IBOutlet weak var targetDirStatusLabel: NSTextField!
    var targetDirStatusLabelColor: NSColor! = NSColor.redColor()

    @IBOutlet weak var overwriteButton: NSButtonCell!
    @IBOutlet weak var overwriteLabel: NSTextField!


    @IBOutlet weak var versionLabel: NSTextField!
    
    @IBOutlet weak var arc42Label: NSButton!
    
    @IBOutlet weak var startButton: NSButtonCell!
    
    
    @IBOutlet weak var helpButton: NSButtonCell!
    
    /// button actions
    /// ===================
    @IBAction func sourceDirButton(sender: AnyObject) {
        selectSourceDirectory()
    }
    
    @IBAction func targetDirButton(sender: AnyObject) {
        selectTargetDirectory()
    }
    
    @IBAction func startButton(sender: AnyObject) {
    }
    
    @IBAction func cancelButton(sender: AnyObject) {
        NSApplication.sharedApplication().terminate(self)
    }
    
    @IBAction func arc42LabelButton(sender: AnyObject) {
        print("arc42 label clicked")
    }
    
    
    
    @IBAction func helpButton(sender: AnyObject) {
    }
    
    // override funcs
    // ====================
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setVersionLabel()
        
        disableStartButton()
        
        setSourceDirStatusLabel("no source directory selected")
        setTargetDirStatusLabel("no target directory selected")
        
        sourceDirTextField.toolTip = "Select source directory with pdf files to be processed (stamped). No files will be modified."
    
        disableOverwriteButtonAndLabel()
        
        // TODO: better to have them enabled and check for changes with property observers/binding
        disableTextField( sourceDirTextField )
        disableTextField( targetDirTextField )
        
    }
    
    

    override func awakeFromNib() {
        
        print("awakeFromNib:  \(self.className)")
        
    }
    
    
    // open directory chooser (NSOpenPanel)
    // ====================================
    private func selectSourceDirectory() {
        let openPanel = directoryOpenPanel("Select Source Directory")
        
        if (openPanel.runModal() == NSFileHandlingPanelOKButton) {
            let fileURL = openPanel.URL!
            //print("path = \(fileURL.path!)")
            // TODO set values in MODEL, not directly!
            self.sourceDirTextField.stringValue = "\(fileURL.path!)"
            self.sourceDirStatusLabelColor = self.fieldValidityToColor( true )
            self.setSourceDirStatusLabel("\(FileUtil.nrOfPdfFilesInFolder( fileURL )) pdf files found")
        }
        
    }
    
    private func selectTargetDirectory() {
        let openPanel = directoryOpenPanel("Select Target Directory")
        
        if (openPanel.runModal() == NSFileHandlingPanelOKButton) {
            let fileURL = openPanel.URL!
            // Now we have a directory selected. Its URL = fileURL
            self.targetDirTextField.stringValue = "\(fileURL.path!)"
            self.targetDirStatusLabelColor = fieldValidityToColor(FileUtil.isDirectory(fileURL.path!))
            self.setTargetDirStatusLabel("\(FileUtil.nrOfPdfFilesInFolder( fileURL )) pdf files found")
        }
        
        // TODO: check status of start button
        
        enableOverwriteButtonAndLabel()
    }
    
    
    /// manual text field changes
    /// ==========================
    
    
    @IBAction func sourceDirectoryField(sender: AnyObject) {
        print("textField \(sender) action triggered")
    }
    
    @IBAction func targetDirTextField(sender: AnyObject) {
        
    }
    
    
    /// disable text field
    /// ------------------
    private func disableTextField(textField: NSTextField!) {
        textField.enabled = false
    }
    
    
    // create directory selection panel
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
    
    /// check if start button shall be enabled
    /// --------------------------------------
    private func checkStartButton() {
        // required conditions to enable startButton:
        // * source directory valid
        // * source directory contains >0 pdf files
        // * target directory valid
        // * no "twin" files (files with twins in source directory, identical name!)
        // OR
        // * overwrite-flag enabled
        
    }
    
    
    /// convert text field validity to label color
    private func fieldValidityToColor(valid: Bool) -> NSColor! {
        switch valid {
        case true:  return NSColor.blueColor()
        case false: return NSColor.redColor()
        }
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
    
    // overwrite files in target directory?
    private func disableOverwriteButtonAndLabel() {
        
        overwriteButton.state = 0
        overwriteButton.stringValue = ""
        overwriteButton.title = ""
        overwriteButton.enabled = false
        overwriteLabel.stringValue = "overwrite:"
        overwriteLabel.enabled = false
        overwriteLabel.textColor = NSColor.grayColor()

    }
    
    private func enableOverwriteButtonAndLabel() {
        overwriteButton.enabled = true
        overwriteLabel.enabled = true
        overwriteLabel.textColor = NSColor.redColor()
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

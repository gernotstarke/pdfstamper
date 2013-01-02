package org.arc42.pageNumberizer

import groovy.swing.SwingBuilder

import javax.swing.BoxLayout
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField
import java.awt.BorderLayout
import java.awt.Button
import java.awt.Color
import java.awt.Font


/**
 * Created with IntelliJ IDEA.
 * User: gstarke
 * Date: 30.12.12
 * Time: 17:46
 *
 */

class NumberizerUI {

    static final int DIR_TEXTFIELD_LENGTH = 50

    def sb = new SwingBuilder();

    String sourceDir
    String targetDir

    // some UI components we need
    JTextField tf_sourceDir
    JTextField tf_targetDir
    JTextField tf_messages

    JFrame frame

    //Button startButton

    public NumberizerUI() {


        frame = sb.frame( id: "numberizerFrame",
                    title: "Pdf File Numberizer", visible: true, resizable: false) {

            // create the Components we need to refer to later on...
            tf_sourceDir = sb.textField(id: "sourceDir", horizontalAlignment: JTextField.LEFT, preferredSize: [500, 30])

            tf_targetDir = sb.textField(id: "targetDir", horizontalAlignment: JTextField.LEFT, preferredSize: [500, 30])

            tf_messages = sb.textField(id: "messages", horizontalAlignment: JTextField.LEFT, preferredSize: [500, 30])


//            startButton = sb.button(text: "Start!",
//                    foreground: Color.BLUE,
//                    actionPerformed: {this.stampFiles( sourceDir, targetDir)}
//            )

            // our main  panes
            panel(id: "main") {
                borderLayout()

                // add empty label at PAGE_START to have nicer spacing
                label("  ", constraints: BorderLayout.PAGE_START)


                panel(constraints: BorderLayout.LINE_START) {
                    gridLayout(columns: 1, rows: 4,)

                    button(
                            text: "Source directory...",
                            actionPerformed: { this.chooseDirectory("Source directory", tf_sourceDir) })

                    button(
                            text: "Target directory...",
                            actionPerformed: { this.chooseDirectory("Target directory", tf_targetDir) })

                    label(" ")

                    //widget( startButton )
                    button(text: "Start!",
                            foreground: Color.BLUE,
                            actionPerformed: { this.stampFiles(sourceDir, targetDir) }
                    )
                }

                panel(id: "center", constraints: BorderLayout.CENTER) {
                    gridLayout(columns: 1, rows: 4)

                    widget(tf_sourceDir)
                    widget(tf_targetDir)

                    label(" ")

                    panel(id: "progressAndCancel") {
                        boxLayout(axis: BoxLayout.LINE_AXIS)

                        //progressBar(string: "processing")

                        box()
                        button(text: "Cancel", actionPerformed: { System.exit(0) })
                    }
                }


                // panel with logo and arc42.org
                panel(id: "logo", constraints: BorderLayout.LINE_END) {
                    boxLayout(axis: BoxLayout.PAGE_AXIS)

                    label(icon: imageIcon(file: "./pdfstamper-logo.png"))

                    panel() {
                        boxLayout( axis:  BoxLayout.LINE_AXIS)

                        label(" ")
                        glue()
                        label("arc42.org   ",
                                horizontalAlignment: JLabel.RIGHT,
                                font: ["Helvetica", Font.PLAIN, 9],
                                foreground: Color.BLUE)
                    }
                }

                //lower (south) part of frame - contains message area and arc42 hint
                panel(constraints: BorderLayout.PAGE_END) {
                    boxLayout(axis: BoxLayout.PAGE_AXIS)

                    separator()

                    widget(tf_messages)

                }

            }
        }


        frame.pack()
        frame.setVisible( true )

    }  // constructor




    public void chooseDirectory(String whichDirectory, JTextField textField) {
        println "choosing directory"

        String selectedDir
        String displayDir

        def openDirectoryDialog = new JFileChooser(
                dialogTitle: whichDirectory,
                fileSelectionMode: JFileChooser.DIRECTORIES_ONLY)


        if (openDirectoryDialog.showOpenDialog() == JFileChooser.APPROVE_OPTION) {

            selectedDir = openDirectoryDialog.selectedFile
            displayDir = selectedDir.length() < DIR_TEXTFIELD_LENGTH ?
                selectedDir :
                "..." + selectedDir.reverse().substring(0, DIR_TEXTFIELD_LENGTH - 3).reverse()

            textField.text = displayDir

        }
    }

    // it's not great - but the UI acts as controller here...

    void stampFiles(String sourceDir, String targetDir) {

    }

    public static void main(String[] args) {
        NumberizerUI numberizerUI = new NumberizerUI()

    }

}
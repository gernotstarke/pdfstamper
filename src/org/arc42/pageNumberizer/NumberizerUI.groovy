package org.arc42.pageNumberizer

import groovy.swing.SwingBuilder

import javax.swing.*
import java.awt.*

/**
 * Swing UI to select source and target directories for PdfStamper.
 * @author: gstarke
 * Date: 30.12.12
 */

class NumberizerUI {

    static final int DIR_TEXTFIELD_LENGTH = 50

    private PdfPageNumberizer

    private SwingBuilder swing = new SwingBuilder();

    private String sourceDir
    private String targetDir

    //some textfields we need
    private JTextField tf_sourceDir =
        swing.textField(id: "sourceDir",
                horizontalAlignment: JTextField.LEFT,
                preferredSize: [500, 30])

    private JTextField tf_targetDir =
        swing.textField(id: "targetDir",
                horizontalAlignment: JTextField.LEFT,
                preferredSize: [500, 30])

    private JTextField tf_messages =
        swing.textField(id: "messages",
                horizontalAlignment: JTextField.LEFT,
                preferredSize: [500, 30])

    // the buttons
    private JButton sourceSelectButton =
        swing.button(
                text: "Source directory...",
                actionPerformed: { this.chooseDirectory("Source directory", tf_sourceDir) })

    private JButton targetSelectButton =
        swing.button(
                text: "Target directory...",
                actionPerformed: { this.chooseDirectory("Target directory", tf_targetDir) })

    private JButton startButton =
        swing.button(text: "Start!",
                foreground: Color.BLUE,
                actionPerformed: { this.stampFiles(sourceDir, targetDir) })

    private JButton cancelButton =
        swing.button(text: "Cancel",
                actionPerformed: { System.exit(0) })

    // labels
    private JLabel arc42label =
        swing.label("arc42.org   ",
                horizontalAlignment: JLabel.RIGHT,
                font: ["Helvetica", Font.PLAIN, 9],
                foreground: Color.BLUE)


    private JFrame frame




    public NumberizerUI() {

        frame = swing.frame(id: "numberizerFrame",
                title: "Pdf File Numberizer", visible: true, resizable: false) {

            // our main  panes
            panel(id: "main") {
                borderLayout()

                // add empty label at PAGE_START to have nicer spacing
                label("  ", constraints: BorderLayout.PAGE_START)


                panel(constraints: BorderLayout.LINE_START) {
                    gridLayout(columns: 1, rows: 4,)

                    widget(sourceSelectButton)
                    widget(targetSelectButton)
                    glue()
                    widget(startButton)
                }

                panel(id: "center", constraints: BorderLayout.CENTER) {
                    gridLayout(columns: 1, rows: 4)

                    widget(tf_sourceDir)
                    widget(tf_targetDir)

                   glue()

                    panel(id: "progressAndCancel") {
                        boxLayout(axis: BoxLayout.LINE_AXIS)
                        glue()
                        progressBar(id: "progress", string: "processing", visible: false)
                        glue()
                        widget(cancelButton)
                    }
                }

                // panel with logo and arc42.org
                panel(id: "logo", constraints: BorderLayout.LINE_END) {
                    boxLayout(axis: BoxLayout.PAGE_AXIS)

                    label(icon: imageIcon(file: "./pdfstamper-logo.png"))

                    panel() {
                        boxLayout(axis: BoxLayout.LINE_AXIS)
                        glue()
                        widget( arc42label)
                    }
                }

                //lower (south) part of frame - contains messagePanel
                panel(constraints: BorderLayout.PAGE_END) {
                    boxLayout(axis: BoxLayout.PAGE_AXIS)
                    separator()
                    widget(tf_messages)
                }

            }
        }


        frame.pack()
        frame.setVisible(true)

    }  // constructor


    private void createUI() {

    }


    private void chooseDirectory(String whichDirectory, JTextField textField) {
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

    def stampFiles(String sourceDir, String targetDir) {

    }

    public static void main(String[] args) {
        NumberizerUI numberizerUI = new NumberizerUI()

    }

}
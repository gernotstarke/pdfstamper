application {
    title = 'arc42.org Pdf Stamper'
    startupGroups = ['pdfstamper']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "pdfstamper"
    'pdfstamper' {
        model      = 'pdfstamper.PdfstamperModel'
        view       = 'pdfstamper.PdfstamperView'
        controller = 'pdfstamper.PdfstamperController'
    }

}

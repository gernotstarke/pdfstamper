application {
    title = 'arc42.org Pdf Stamper'
    startupGroups = ['pdfstamper']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

}
mvcGroups {
    // MVC Group for "credits"
    'credits' {
        model      = 'pdfstamper.CreditsModel'
        view       = 'pdfstamper.CreditsView'
        controller = 'pdfstamper.DialogController'
    }

    // MVC Group for "license"
    'license' {
        model      = 'pdfstamper.LicenseModel'
        view       = 'pdfstamper.LicenseView'
        controller = 'pdfstamper.DialogController'
    }


    // MVC Group for "about"
    'about' {
        model      = 'pdfstamper.AboutModel'
        view       = 'pdfstamper.AboutView'
        controller = 'pdfstamper.AboutController'
    }

    // MVC Group for "pdfstamper"
    'pdfstamper' {
        model      = 'pdfstamper.PdfstamperModel'
        view       = 'pdfstamper.PdfstamperView'
        controller = 'pdfstamper.PdfstamperController'
    }

}

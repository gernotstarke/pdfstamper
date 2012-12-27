## Prerequisites

* all pdf files are located within (current or configured) directory


* all pdf files have A4 size and portrait orientation
* page bottom has enough free space to add pagenumber (and prefix)


## Requirements

* add page numbers to all pdf files within directory (current or configured)
* number consecutively


## Todo for PdfPageNumberizer

* check wether source directory is valid
* check wether target directory exists - if not: create
* check for at least one pdf file in sourceDir

* calculate appropriate location for pagenumber/prefix (bottom-center)

* for all files in directory:
    * open pdf file for stamping
    * numberize current file
    * create log/status output
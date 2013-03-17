

## What PdfStamper does for you

![the problem](./PdfStamper-Overview.png)

* add page numbers to a number of pdf files within (source) directory
* it numbers consecutively, starting with page 1

* PdfStamper NEVER modifies the source pdf's - it will ALWAYS copy them (page-by-page) to a target directory and add content only on the newly created copies.

* On the UI, you can configure a file-prefix and page-number-prefix. 

## Prerequisites

* all pdf files are located within (current or configured) directory


* all pdf files (should currently) have A4 size and portrait orientation
* page bottom has enough free space to add pagenumber and prefix

### System requirements
* You need a Java (6 or later) runtime installed on your machine.


## Limitations
Currently (in release 0.42), PdfStamper cannot:

* combine Pdf's into single file
* add graphics in header or footer
* add Pdf security to output files
* add the total-nr-of-pages 


## Bugs, Issues and Requests
Please use the issue-tracker at bitbucket.


## Documentation
The documentation will grow into an arc42 example - it's currently just a beginning.
Find the stuff in the /doc diretory (PdfStamper_arc42.md).


## Future Plans

### Software
* create an index: collect keywords from the pdf files, sort them and create a nicely formated index.

* stamp a header (in addition to the current footer)
* make the positions for page numbers configurable
* stamp pdf files of arbitrary page dimensions
* calculate the page number position on per-page-basis, instead of having a fix one
* preview generated files

 
### Documentation
* Use PdfStamper as an example for arc42.
 
 
 



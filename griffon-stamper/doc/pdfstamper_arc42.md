# Documentation for the arc42 pdfStamper, a page numberizer
# Documentation for the arc42 pdfStamper, a page numberizer

Version: May 4th 2013, Gernot Starke

(Star Wars day of the year: may the fourth be with you…)


See the [release notes](#header_relnotes) for (boring) organizational info.

##### Contents

1. [Intro and Requirements](#header_intro)         
2. [Constraints](#header_constraints)            
3. [Context](#header_context)
4. [Solution Strategy](#header_solution) 
5. [Building Blocks](#header_blocks)
6. [Crosscutting Concepts](#header_concepts)
    * Pdf handling (page layout, stamping, positioning text)
    * Domain model
    * Acceptance Testing (_behavior) 
    * Build 
7. [Developer Guide](#header_developer)
	* Directory layout 	
8. [Glossary](#header_glossary)
9. [Release Notes](#header_relnotes)

## [Intro and Requirements](id:header_intro)
We create PDF files from various sources (ppt, odt, doc, keynote, html...) and want to create nice printable handouts from these sources.

With these various document sources, it's neither possible to create consistent pagenumbers, nor to add custom header information.

![the problem](./images/AToPDF_the_Problem.png)

Instead of flushing down many many bucks into A****s hungry mouth I decided to create this solution on my own.

### Preconditions

1. The *content* of the files has been converted to pdf (so we're not concerned about the original data or file formats nor the tools to create/manipulate the original content).
2. The dimensions (pagesize) of the pdf files identical (we assume A4-portrait in most cases).
3. The existing pdf files are *not* encrypted.
  

### Use Cases

Ooops - the following use case diagram looks like a big app - which our little pdf stamper is not :-)

![use cases](./images/AToPdf_use_cases.jpg)

Brief description of these use cases:

##### Add page (and chapter) numbers to Pdf files
* number the pages consecutively, remember the last pagenumber from the previous file to calculate the first pagenumber of the current file.
* *evenify* on demand: add a blank page at the end of every file that has an odd number of pages. Reason: Odd numbers shall always be printed on *right* pages, even numbers are always an *left* pages. On double-sided books, even numbers are always on backsides… Without *evenification*, some chapters could start with an even pagenumber.
* Optionally add the number of the current file in the footer.

 
##### Add header to Pdf files
Same as adding page- and chapter numbers - but stamping a (constant) text in the header.

##### Generate index
In handouts with more than 100 pages, it get's difficult to find particular topics without an index. We want to support

##### Create course handouts
* optional: combine several pdf files into ONE.
* optional: add security, so people cannot (1) open, (2) print or (3) copy from the file. The Pdf file format and most Pdf readers support these security restrictions. Security is based upon passwords.
* optional: add logo and/or watermarks.
* 

### Architecture and Quality Goals

Priority | Quality Goal | Descriptive Scenarios
:-------: | :----------- | :--------------------
      1  | Low Cost      | no license fees
         |               |  
      2  | Reliability   | must never corrupt a pdf file
         |               | even in case of power loss, <br>         original files are preserved and unmodified.
         |               |
      3  | Understandability | systems shall serve as docu,
         |               |  implementation and testing example. 

---
## [Contraints](id:header_constraints)

Due to limited time and resources, we impose some limitations to our development effort:

* We currently expect paper to be A4 size, usually in portrait orientation.

* We don't
    * manipulate secured PDF's (files that require passwords to read or print their contents)
    * provide a graphical user interface (yet)
        handle corrupt PDF files
        
        
* We only:
    * guarantee results only for some well-known paper sizes
    * handle a few hundred pages per file, as all operations is currently performed in-memory

---
## [Context](id:header_context)

Input: directory with several pdf files.
Output: directory with stamped/secured/combined pdf files.

---
## [Solution Strategy](id:header_solution)
* Use the [iText Pdf][url_itext] library, as it is open source and easy to use
* Apply ATDD (acceptance test driven development) with [Spockframework][url_spock]
* Develop the application in releases of increasing complexity:
	1. First release can be used from an IDE only, no packaging
	2. Second release is usable from the command line
	3. Third release has graphical UI (based on Griffon) 
	
	
---
## [Building Blocks](id:header_blocks)



---
## [Crosscutting Concepts](id:header_concepts)

### Pdf page layout and dimensions

![](./images/Pdf_page_layout.png)

### Determine position of page number and header
Currently we want page numbers in the bottom, either 
* centered (for both even and odd numbered pages)
* at the outside (that means _right_ for odd pages, _left_ for even ones.) 

We need to calculate this position for every page, as pagesize or orientation might vary within PDF documents.


### Well known page sizes
Our most important page sizes are already predefined in iText in [the itextpdf.text.PageSize][url_itext_pagesizes] class (based upon itextpdf.text.Rectangle).

An example: determine the pagesize of A4 pages:

    import com.itextpdf.text.PageSize
    assert 595 == PageSize.A4.width
    assert 842 == PageSize.A4.height
    

### Determine size of page in pdf file

Calculating the size of a page we use an instance of iText.PdfReader as follows:
    
    import com.itextpdf.text.Rectangle

    private Point calculatePageNrPosition( int currentPageNumberInFile ) 
    {
        Rectangle rectangle = 
              reader.getPageSize(bcurrentPageNumberInFile );
        int width = rectangle.getRight()
        
        int heigth = rectangle.getTop()
    }

### Adding text to existing pdf files (aka stamping)

#### Adding blank pages (*evenify*)
##### Why add blank pages? What is evenification?
In book-like documents, content is printed on both front- and backside of pages. Numbering pages in books always starts with page #1 on the frontside of the first page.

In general, odd page numbers *always* appear on the right side, and are *always* on the frontside of pages.

This requirement leads to a slight issue when concatenating different Pdf files: If we want the first page of every file to appear on the right side of the final document, we need to add *filler* pages if the pagecount of the preceeding file is odd.

Sounds complicated? Look at an example.

###### All input files have EVEN pagecount
![input files with even pagecount](./images/even-sided-input.jpg )

###### An input file with ODD pagecount
![input files with odd  pagecount](./images/odd-sided-input.jpg )

In this case, the FIRST page of the SECOND input file might be printed on the backside of a page. If this page is, for example, the start of a new chapter in a book, you need a blank page to the rescue…



##### How blank pages are added
* itext.PdfCopy makes adding blank pages quite difficult. Not recommended.
* Generate a blank page in advance, with its own itext.PdfReader instance. Re-use this blank page.



 
---
## [Developer Guide](id:header_developer)
### Tools and Plugins 

#### Needed for Development

1. PdfStamper is developed in [Groovy][url_groovy], a dynamic language on the Java VM. Grab a JDK first, we recommend to install Groovy via [gvm][url_gvm], the awesome and unobtrusive Groovy package manager. 
   a. Install gvm via curl on the command line. Don't know curl? System.exit(1)
   b. Follow the instructions on the [gvm site][url_gvm] to install Groovy, version 2 or later.
2. PdfStampers' user interface has been build using [Griffon][url_griffon], version 1.2. Install via gvm.
3. Now it's a good time to checkout the sources...
4. With Griffon successfully installed, you need to install several Griffon plugins (from within the PdfStamper directory). This can be done with the following commands:
   a. griffon install-plugin miglayout
   b. griffon install-plugin jide-builder
   c. griffon install-plugin spock
   
5. The Pdf manipulation magic is done with [iTextPdf][url_itext] library, version 5 or later.

See [MigLayout][url_miglayout], [Jide-Builder][url_jidebuilder] and [Spockframework][url_spock] for details. 


##### Used for Documentation
1. Any Markdown editor
2. OmniGraffle, a Mac-OS based graphic/drawing tool.
3. Some UML diagrams exist in the architecture documentation. Most likely, nobody else wants to touch those…




### Development Environment
I'm addicted to [IntelliJ IDEA][url_intellij]) - a full-scale integrated development environment. It's a matter of taste, you should be able to work on PdfStamper using anything from Eclipse, NetBeans or any plain text editor.

Griffon-support is quite ok in IntelliJ - but have a command-line at hand.
 
### Building the application
Griffon provides build targets, up to the generation of OS-specific installers.

PdfStamper is currently only pre-build for Mac-OS, resulting in a .dmg file.
 
#### Building the Mac-OS Application

Run the
 
     griffon package mac
     
command. 

### Acceptance Testing
The app was developed in _acceptance test driven development_ (ATDD) style: Before I wrote productive code, I formulated the appropriate acceptance test as [Spock Specification][url_spock].

#### Preconditions for Testing
(aka infrastructure for testing)

A number of sample pdf files have to exist at specific locations. These are defined in the TestResources class:

    final static TEST_RESOURCES_DIR = "./test/resources/"
    final static EMPTY_DIR          = TEST_RESOURCES_DIR + "EmptyDir/"
    final static DIR_WITH_ONE_PDF   = TEST_RESOURCES_DIR + "OnePdf/"
    final static DIR_WITH_HUGE_PDF  = TEST_RESOURCES_DIR + "colored_XL_Pdf/"
    final static DIR_WITH_TWO_PDFs  = TEST_RESOURCES_DIR + "TwoPdfs/"
    final static DIR_WITH_MANY_PDFs = TEST_RESOURCES_DIR + "ManyPdfs/"


#### Major Test Case: Stamping the Correct Number of Pages
<tbd>

We stamp several pregenerated pdf files and compare the resulting pagecount.
 


---
## [Glossary](id:header_glossary)
---

acceptance test: a blackbox testcase of a system use case.

arc42: Template for software architecture documentation.

evenify:

itext:

pdf:

stamping:





---
## [Release Notes](id:header_relnotes)
---
* May 4th 2013: merged with previous version in atopdf-project.
* Feb 25th 2013: added release notes, glossary, updated use cases
* Dec 14th 2012: updated structure, included developer guide
* Dec 1st 2012: initial markdown version, converted from TiddlyWiki

---
[url_itext]: http://itextpdf.com "iText Pdf Library"
[url_spock]: http://www.spockframework.org "Spock Acceptance Test Framework"
[url_itext_pagesizes]: http://api.itextpdf.com/itext/com/itextpdf/text/PageSize.html

[url_gvm]: http://gvmtool.net/
[url_griffon]: http://griffon.codehaus.org/
[url_itext]: http://itextpdf.com "iText Pdf Library"
[url_spock]: http://www.spockframework.org "Spock Acceptance Test Framework"

[url_miglayout]:
[url_jidebuilder]:

[url_intellij]: http://www.jetbrains.com/idea/

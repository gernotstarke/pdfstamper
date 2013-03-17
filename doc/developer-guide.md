# Developer Documentation for PdfStamper 

### Contents

1. [Prerequisites - before you begin](#header_intro)
    * Tools and Plugins
    * Development Environment         
2. [Developer Guide](#header_developer)
	* Architecture Overview
	* Directory Layout 	



## [Prerequsites](id:header_intro)

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


#### Used for Documentation
1. Any Markdown editor
2. OmniGraffle, a Mac-OS based graphic/drawing tool.
3. Some UML diagrams exist in the architecture documentation. Most likely, nobody else wants to touch those…




## Development Environment
I'm addicted to [IntelliJ IDEA][url_intellij]) - a full-scale integrated development environment. It's a matter of taste, you should be able to work on PdfStamper using anything from Eclipse, NetBeans or any plain text editor.

Griffon-support is quite ok in IntelliJ - but have a command-line at hand.
 
 
---
## [Developer Guide](id:header_developer)

### Architecture Overview


### Directory Layout

### User Interface
#### MigLayout


#### Jide-Builder

### Backend: Pdf Processing

### Spock

---
[url_groovy]: http://groovy.codehaus.org/
[url_gvm]: http://gvmtool.net/
[url_griffon]: http://griffon.codehaus.org/
[url_itext]: http://itextpdf.com "iText Pdf Library"
[url_spock]: http://www.spockframework.org "Spock Acceptance Test Framework"

[url_miglayout]:
[url_jidebuilder]:

[url_intellij]: http://www.jetbrains.com/idea/
# json-to-pdf

This project aims to provide a way to declaratively generate PDF reports using JSON notation via the
 [clj-pdf](https://github.com/yogthos/clj-pdf) library.

An example document can be viewed [here](https://docs.google.com/viewer?url=https://github.com/yogthos/json-to-pdf/raw/master/example.pdf).


# Installation

### Gradle

```
repositories {
    jcenter()
    maven {
        url 'http://clojars.org/repo'
    }
}

compile "json-to-pdf:json-to-pdf:0.8.3"
```

### Maven

json-to-pdf is available from the [Clojars](https://clojars.org/) repo:

```xml
<repositories>
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>
</repositories>

<dependency>
  <groupId>json-to-pdf</groupId>
  <artifactId>json-to-pdf</artifactId>
  <version>0.8.3</version>
</dependency>
```
see [here](https://github.com/yogthos/json-to-pdf-example) for a complete sample project

## Usage

JSON documents are represented by an array containing one or more elements. The first element in the document must be a map containing the metadata.

```java
import cljpdf.text.BadElementException;
import cljpdf.text.Document;
import cljpdf.text.Image;
import cljpdf.text.pdf.PdfWriter;
import org.yogthos.JsonPDF;
import cljpdf.text.pdf.PdfPageEventHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws Exception {
        String jsonDoc1 = "[{}, [\"paragraph\", \"hello world\"]]";
        String jsonDoc2 = "[{\"pages\":true,\"orientation\":\"landscape\"}, [\"paragraph\", \"hello world\"]]";

        JsonPDF.writeToFile(jsonDoc1, "out.pdf", null);

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        JsonPDF.writeToStream(new ByteArrayInputStream(jsonDoc1.getBytes()),
                new FileOutputStream("outstream.pdf"), null);

        System.out.println(new File(".").getAbsolutePath());
        JsonPDF.writeToFile(jsonDoc1, "out.pdf", new HeaderFooter("resources/mandelbrot.jpg"));
    }

    static class HeaderFooter extends PdfPageEventHelper {
        private Image img;
        public HeaderFooter(String imagePath)
                throws BadElementException, MalformedURLException, IOException {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(imagePath);
            this.img = Image.getInstance(file.getPath());
            this.img.scaleToFit(100, 100);
            this.img.setAbsolutePosition(25,700);
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                writer.getDirectContent().addImage(this.img);
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }
}
```

## Document Format

### Metadata

All fields in the metadata section are optional:

```javascript
{"right-margin":10,
 "subject":"Some subject",
 "creator":"Jane Doe",
 "doc-header":["inspired by", "William Shakespeare"],
 "bottom-margin":25,
 "pages":true,
 "author":"John Doe",
 "header":"Page header text appears on each page",
 "left-margin":10,
 "title":"Test doc",
 "size":"a4",
 "letterhead":["A simple Letter head"],
 "orientation":"landscape",
 "font":{"size":11},
 "footer":
 "Page footer text appears on each page (includes page number)",
 "top-margin":20}

```

available page sizes:

```javascript
"a0"
"a1"
"a10"
"a2"
"a3"
"a4"
"a5"
"a6"
"a7"
"a8"
"a9"
"arch-a"
"arch-b"
"arch-c"
"arch-d"
"arch-e"
"b0"
"b1"
"b10"
"b2"
"b3"
"b4"
"b5"
"b6"
"b7"
"b8"
"b9"
"crown-octavo"
"crown-quarto"
"demy-octavo"
"demy-quarto"
"executive"
"flsa"
"flse"
"halfletter"
"id-1"
"id-2"
"id-3"
"large-crown-octavo"
"large-crown-quarto"
"ledger"
"legal"
"letter"
"note"
"penguin-large-paperback"
"penguin-small-paperback"
"postcard"
"royal-octavo"
"royal-quarto"
"small-paperback"
"tabloid"
 ```

The font defaults to A4 page size if none provided and the orientation defaults to portrait, unless "landscape" is specified.

#### Font

A font is defined by a map consisting of the following parameters, all parameters are optional

* "family": has following options: "courier":, "helvetica":, "times-roman":, "symbol":, "zapfdingbats": defaults to "helvetica":
* "size": is a number default is 11
* "style": has following options: "bold":, "italic":, "bold-italic":, "normal":, "strikethru":, "underline": defaults to "normal":
* "color": is a vector of [r g b] defaults to black

example font:

```javascript
{"style":"bold", "size":18, "family":"helvetica", "color":[0, 234, 123]}

```
note: Font styles are additive, for example setting style "italic": on the phrase, and then size 20 on a chunk inside the phrase, will result with the chunk having italic font of size 20. Inner elements can override style set by their parents.


### Document sections

Each document section is represented by a vector starting with a keyword identifying the section followed by an optional map of metadata and the contents of the section.

#### Anchor

tag "anchor":

optional metadata:

* "style": font
* "leading": number

content:

iText idiosynchorsies:

* when both font style and leading number are specified the content must be a string
* when leading number is specified content can be a chunk or a string
* when only font style is specified content must be a string
* if no font style or leading is specified then content can be a chunk, a phrase, or a string

```javascript
["anchor", {"style":{"size":15}, "leading":20}, "some anchor"]

["anchor", ["phrase", {"style":"bold"}, "some anchor phrase"]]

["anchor", "plain anchor"]

```

#### Chapter

tag "chapter":

optional metadata:

* none

content:

* string
* paragraph

```javascript
["chapter", "First Chapter"]

["chapter", ["paragraph", "Second Chapter"]]

```

#### Chunk

tag "chunk":

optional metadata:

* "sub": boolean sets chunk to subscript
* "super": boolean sets chunk to superscript

font metadata (refer to Font section for details)

* "family":
* "size":
* "style":
* "color":

```javascript
["chunk", {"style":"bold"}, "small chunk of text"]

["chunk", {"super":true}, "5"]

["chunk", {"sub":true}, "2"]

```

#### Heading

tag "heading":

optional metadata:

* "style": custom font for the heading
* "align": specifies alignement of heading possible valuse "left":, "center":, "right":

```javascript
["heading", "Lorem Ipsum"]

["heading", {"style": {"size":15}}, "Lorem Ipsum"]

["heading", {"style": {"size":10, "color":[100, 40, 150], "align":"right"}}, "Foo"]

```

#### Image

tag "image":

image data can be a base64 string, a string representing URL or a path to file,
images larger than the page margins will automatically be scaled to fit.

optional metadata:

* "scale": number - percentage relative to page size
* "xscale": number - percentage relative to page size
* "yscale": num - percentage relative to page size
* "width": num - set width for image: overrides scaling
* "height": num - set height for image: overrides scaling
* "align": "left":, "center":, "right":
* "annotation": ["title" "text"]
* "pad-left": number
* "pad-right": number
* "base64": boolean - if set the image is expected to be a Base64 string

```javascript
["image",
 {"xscale":0.5,
  "yscale":0.8,
  "align":"center",
  "annotation":["FOO", "BAR"],
  "pad-left":100,
  "pad-right":50}, ["read", "mandelbrot.jpg"]]

["image", "test/mandelbrot.jpg"]

["image", "http://clojure.org/space/showimage/clojure-icon.gif"]

```

#### Line

tag "line":

optional metadata:

* "dotted": boolean
* "gap": number ;space between dots if line is dotted

creates a horizontal line

```javascript
["line"]

["line", {"dotted":true}]

["line", {"dotted":true, "gap":10}]

```


#### List

tag "list":

optional metadata:

* "numbered":            boolean
* "lettered":            boolean
* "roman":               boolean
* "greek":               boolean
* "dingbats":            boolean
* "dingbats-char-num":   boolean
* "dingbatsnumber":      boolean
* "dingbatsnumber-type": boolean
* "symbol"               string (custom item prefix)

content:

* strings, phrases, or chunks


```javascript
["list", {"roman":true}, ["chunk", {"style":"bold"}, "a bold item"],
 "another item", "yet another item"]

```

#### Paragraph

tag "paragraph":

optional metadata:

* "indent": number
* "keep-together": boolean
* "leading": number
* "align": "left":, "center":, "right":

font metadata (refer to Font section for details)

* "family":
* "size":
* "style":
* "color":

content:

* one or more elements (string, chunk, phrase, paragraph)

```javascript
["paragraph", "a fine paragraph"]

["paragraph", {"keep-together":true, "indent":20}, "a fine paragraph"]

["paragraph",
 {"style":"bold",
  "size":10,
  "family":"halvetica",
  "color":[0, 255, 221]},
 "Lorem ipsum dolor sit amet, consectetur adipiscing elit."]

"font"

["paragraph", {"indent":50, "color":[0, 255, 221]},
 ["phrase", {"style":"bold", "size":18, "family":"halvetica"},
  "Hello Clojure!"]]

["paragraph", "256", ["chunk", {"super":true}, "5"], " or 128",
 ["chunk", {"sub":true}, "2"]]

```

#### Phrase

tag "phrase":

optional metadata:

* "leading": number

font metadata (refer to Font section for details)

* "family":
* "size":
* "style":
* "color":

content:

* strings and chunks


```javascript
["phrase", "some text here"]

["phrase",
 {"style":"bold",
  "size":18,
  "family":"halvetica",
  "color":[0, 255, 221]}, "Hello Clojure!"]

["phrase", ["chunk", {"style":"italic"}, "chunk one"],
 ["chunk", {"size":20}, "Big text"], "some other text"]

["phrase", "styles" : ["italic", "underline"]]
```

#### Section

tag "section":

Chapter has to be the root element for any sections. Subsequently sections can only be parented under chapters and other sections, a section must contain a title followed by the content

optional metadata:

* "indent": number

```javascript
["chapter", ["paragraph", {"color":[250, 0, 0]}, "Chapter"],
 ["section", "Section Title", "Some content"],
 ["section", ["paragraph", {"size":10}, "Section Title"],
  ["paragraph", "Some content"], ["paragraph", "Some more content"],
  ["section", {"color":[100, 200, 50]},
   ["paragraph", "Nested Section Title"],
   ["paragraph", "nested section content"]]]]

```

#### Spacer

tag "spacer":

creates a number of new lines equal to the number passed in (1 space is default)

```javascript
["spacer"]

["spacer", 5]

```

#### String

A string will be automatically converted to a paragraph

```
"this text will be treated as a paragraph"
```

#### Subscript

tag "subscript":

optional metadata:

* "style": font

creates a text chunk in subscript

```javascript
["subscript", "some subscript text"]

["subscript", {"style":"bold"}, "some bold subscript text"]

```

#### Superscript

tag "superscript":

optional metadata:

* "style": font

creates a text chunk in subscript

```javascript
["superscript", "some superscript text"]

["superscript", {"style":"bold"}, "some bold superscript text"]

```

#### Table

tag "table":

metadata:

* "align": table alignment on the page can be: "left":, "center":, "right":
* "background-color":  `[r g b]` (int values)
* "header": [{"backdrop-color": [r g b]} "column name" ...] if only a single column name is provided it will span all rows
* "spacing": number
* "padding": number
* "border": boolean
* "border-width": number
* "cell-border": boolean
* "width": number signifying the percentage of the page width that the table will take up
* "widths": vector list of column widths in percentage
* "header": is a vector of strings, which specify the headers for each column, can optionally start with metadata for setting header color
* "offset": number
* "num-cols": number

```javascript
["table",
 {"header":["Row 1", "Row 2", "Row 3"],
  "width":50,
  "border":false,
  "cell-border":false},
 [["cell", {"colspan":2}, "Foo"], "Bar"], ["foo1", "bar1", "baz1"],
 ["foo2", "bar2", "baz2"]]

["table", {"border-width":10, "header":["Row 1", "Row 2", "Row 3"]},
 ["foo", "bar", "baz"], ["foo1", "bar1", "baz1"],
 ["foo2", "bar2", "baz2"]]

["table",
 {"border":false,
  "widths":[2, 1, 1],
  "header":[{"color":[100, 100, 100]}, "Singe Header"]},
 ["foo", "bar", "baz"], ["foo1", "bar1", "baz1"],
 ["foo2", "bar2", "baz2"]]

["table",
 {"cell-border":false,
  "header":[{"color":[100, 100, 100]}, "Row 1", "Row 2", "Row 3"],
  "cellSpacing":20,
  "header-color":[100, 100, 100]},
 ["foo",
  ["cell",
   ["phrase",
    {"style":"italic",
     "size":18,
     "family":"halvetica",
     "color":[200, 55, 221]},
    "Hello Clojure!"]],
  "baz"],
 ["foo1", ["cell", {"color":[100, 10, 200]}, "bar1"], "baz1"],
 ["foo2", "bar2", "baz2"]]

```

#### Table Cell

Cells can be optionally used inside tables to provide specific style for table elements

tag "cell":

metadata:

* "color": `[r g b]` (int values)
* "colspan": number
* "rowspan": number
* "border": boolean
* "set-border": `["top": "bottom": "left": "right]`": list of enabled borders, pass empty vector to disable all borders
* "border-width": number
* "border-width-bottom": number
* "border-width-left": number
* "border-width-right": number
* "border-width-top": number

content:

Cell can contain any elements such as anchor, annotation, chunk, paragraph, or a phrase, which can each have their own style

note: Cells can contain other elements including tables

```javascript
["cell", {"colspan":2}, "Foo"]

["cell", {"colspan":3, "rowspan":2}, "Foo"]

["cell",
 ["phrase",
  {"style":"italic",
   "size":18,
   "family":"halvetica",
   "color":[200, 55, 221]}, "Hello Clojure!"]]

["cell", {"color":[100, 10, 200]}, "bar1"]

["cell",
 ["table",
  ["Inner table Col1", "Inner table Col2", "Inner table Col3"]]]

```

### Charting

tag "chart":

metadata:

* "type":        - bar-chart, line-chart, pie-chart
* "x-label":     - only used for line and bar charts
* "y-label":     - only used for line and bar charts
* "time-series": - only used in line chart
* "time-format": - can optionally be used with time-series to provide custom date formatting, defaults to "yyyy-MM-dd-HH:mm:ss"
* "horizontal":  - can be used with bar charts and line charts, not supported by time series
* "background":  - a vector of [r g b] integer values
* "title":       - the title of the chart

additional image metadata

* "scale": number - percentage relative to page size
* "width": num - set width for image: overrides scaling
* "height": num - set height for image: overrides scaling
* "align": "left|center|right"
* "annotation": ["title" "text"]
* "pad-left": number
* "pad-right": number

#### bar chart

```javascript
["chart",
 {"type":"bar-chart",
  "title":"Bar Chart",
  "background":[255,255,255],
  "x-label":"Items",
  "y-label":"Quality"}, [2, "Foo"], [4, "Bar"], [10, "Baz"]]

```

#### line chart

if "time-series": is set to true then items on x axis must be dates, the default format is "yyyy-MM-dd-HH"mm:ss"":, for custom formatting options refer [here](http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)

```javascript
["chart",
 {"type":"line-chart",
  "title":"Line Chart",
  "x-label":"checkpoints",
  "y-label":"units"},
 ["Foo", [1, 10], [2, 13], [3, 120], [4, 455], [5, 300], [6, 600]],
 ["Bar", [1, 13], [2, 33], [3, 320], [4, 155], [5, 200], [6, 300]]]

```

```javascript
["chart",
 {"x-label":"time",
  "y-label":"progress",
  "time-series":true,
  "title":"Time Chart",
  "type":"line-chart"},
 ["Incidents", ["2011-01-03-11:20:11", 200],
  ["2011-02-11-22:25:01", 400], ["2011-04-02-09:35:10", 350],
  ["2011-07-06-12:20:07", 600]]]

```

```javascript
["chart",
 {"type":"line-chart",
  "time-series":true,
  "time-format":"MM/yy",
  "title":"Time Chart",
  "x-label":"time",
  "y-label":"progress"},
 ["Occurances", ["01/11", 200], ["02/12", 400], ["05/12", 350],
  ["11/13", 600]]]

```

#### pie chart

```javascript
["chart", {"type":"pie-chart", "title":"Big Pie"}, ["One", 21],
 ["Two", 23], ["Three", 345]]

```

### A complete example

```javascript
  [{"right-margin":50,
   "subject":"Some subject",
   "creator":"Jane Doe",
   "doc-header":["inspired by", "William Shakespeare"],
   "bottom-margin":25,
   "author":"John Doe",
   "header":"page header",
   "left-margin":10,
   "title":"Test doc",
   "size":"a4",
   "footer":"page",
   "top-margin":20},

   ["heading", "Lorem Ipsum"],

   ["paragraph", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec non iaculis lectus. Integer vel libero libero. Phasellus metus augue, consequat a viverra vel, fermentum convallis sem. Etiam venenatis laoreet quam, et adipiscing mi lobortis sit amet. Fusce eu velit vitae dolor vulputate imperdiet. Suspendisse dui risus, mollis ut tempor sed, dapibus a leo. Aenean nisi augue, placerat a cursus eu, convallis viverra urna. Nunc iaculis pharetra pretium. Suspendisse sit amet erat nisl, quis lacinia dolor. Integer mollis placerat metus in adipiscing. Fusce tincidunt sapien in quam vehicula tincidunt. Integer id ligula ante, interdum sodales enim. Suspendisse quis erat ut augue porta laoreet."],

   ["paragraph", "Sed pellentesque lacus vel sapien facilisis vehicula. Quisque non lectus lacus, at varius nibh. Integer porttitor porttitor gravida. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus accumsan ante tincidunt magna dictum vulputate. Maecenas suscipit commodo leo sed mattis. Morbi dictum molestie justo eu egestas. Praesent lacus est, euismod vitae consequat non, accumsan in justo. Nam rhoncus dapibus nunc vel dignissim."],

   ["paragraph", "Nulla id neque ac felis tempor pretium adipiscing ac tortor. Aenean ac metus sapien, at laoreet quam. Vivamus id dui eget neque mattis accumsan. Aliquam aliquam lacinia lorem ut dapibus. Fusce aliquam augue non libero viverra ut porta nisl mollis. Mauris in justo in nibh fermentum dapibus at ut erat. Maecenas vitae fermentum lectus. Nunc dolor nisl, commodo a pellentesque non, tincidunt id dolor. Nulla tellus neque, consectetur in scelerisque vitae, cursus vel urna. Phasellus ullamcorper ultrices nisi ac feugiat."],


   ["table",
   {"header":[{"color":[100, 100, 100]}, "FOO"], "cellSpacing":20},
   ["foo",
    ["cell",
     ["phrase",
      {"style":"italic",
       "size":18,
       "family":"halvetica",
       "color":[200, 55, 221]},
      "Hello Clojure!"]],
    "baz"],
   ["foo1", ["cell", {"color":[100, 10, 200]}, "bar1"], "baz1"],
   ["foo2", "bar2",
    ["cell",
     ["table",
      ["Inner table Col1", "Inner table Col2", "Inner table Col3"]]]]],

   ["paragraph", "Suspendisse consequat, mauris vel feugiat suscipit, turpis metus semper metus, et vulputate sem nisi a dolor. Duis egestas luctus elit eget dignissim. Vivamus elit elit, blandit id volutpat semper, luctus id eros. Duis scelerisque aliquam lorem, sed venenatis leo molestie ac. Vivamus diam arcu, sodales at molestie nec, pulvinar posuere est. Morbi a velit ante. Nulla odio leo, volutpat vitae eleifend nec, luctus ac risus. In hac habitasse platea dictumst. In posuere ultricies nulla, eu interdum erat rhoncus ac. Vivamus rutrum porta interdum. Nulla pulvinar dui quis velit varius tristique dignissim sem luctus. Aliquam ac velit enim. Sed sed nisi faucibus ipsum congue lacinia. Morbi id mi in lectus vehicula dictum vel sed metus. Sed commodo lorem non nisl vulputate elementum. Fusce nibh dui, auctor a rhoncus eu, rhoncus eu eros."],

   ["paragraph", "Nulla pretium ornare nisi at pulvinar. Praesent lorem diam, pulvinar nec scelerisque et, mattis vitae felis. Integer eu justo sem, non molestie nisl. Aenean interdum erat non nulla commodo pretium. Quisque egestas ullamcorper lacus id interdum. Ut scelerisque, odio ac mollis suscipit, libero turpis tempus nulla, placerat pretium tellus purus eu nunc. Donec nec nisi non sem vehicula posuere et eget sem. Aliquam pretium est eget lorem lacinia in commodo nisl laoreet. Curabitur porttitor dignissim eros, nec semper neque tempor non. Duis elit neque, sagittis vestibulum consequat ut, rhoncus sed dui."],


  ["anchor", {"style":{"size":15}, "leading":20}, "some anchor"],

  ["anchor", ["phrase", {"style":"bold"}, "some anchor phrase"]],

  ["anchor", "plain anchor"],

  ["chunk", {"style":"bold"}, "small chunk of text"],

  ["phrase", "some text here"],

  ["phrase",
   {"style":"italic",
    "size":18,
    "family":"halvetica",
    "color":[0, 255, 221]},
   "Hello Clojure!"],

   ["chapter", ["paragraph", "Second Chapter"]],

   ["paragraph", {"keep-together":true, "indent":20},
   "a fine paragraph"],

   ["list", {"roman":true}, ["chunk", {"style":"bold"}, "a bold item"],
   "another item", "yet another item"],

   ["chapter", "Charts"],

   ["chart",
   {"type":"bar-chart",
    "title":"Bar Chart",
    "x-label":"Items",
    "y-label":"Quality"},
   [2, "Foo"], [4, "Bar"], [10, "Baz"]],

   ["chart",
   {"type":"line-chart",
    "title":"Line Chart",
    "x-label":"checkpoints",
    "y-label":"units"},
   ["Foo", [1, 10], [2, 13], [3, 120], [4, 455], [5, 300], [6, 600]],
   ["Bar", [1, 13], [2, 33], [3, 320], [4, 155], [5, 200], [6, 300]]],

   ["chart", {"type":"pie-chart", "title":"Big Pie"}, ["One", 21],
   ["Two", 23], ["Three", 345]],

   ["chart",
   {"type":"line-chart",
    "time-series":true,
    "title":"Time Chart",
    "x-label":"time",
    "y-label":"progress"},
   ["Incidents", ["2011-01-03-11:20:11", 200],
    ["2011-01-03-11:25:11", 400], ["2011-01-03-11:35:11", 350],
    ["2011-01-03-12:20:11", 600]]]]
```


# License
***
Distributed under LGPL, the same as [clj-pdf](https://github.com/yogthos/clj-pdf).

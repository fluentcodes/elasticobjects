[![Open Source Helpers](https://www.codetriage.com/fluentcodes/elasticobjects/badges/users.svg)](https://www.codetriage.com/fluentcodes/elasticobjects)
# Spring Boot Example

Target of this module is to create a runable spring boot web application with the help of templates. A running copy you find in [elasticobjects.org](elasticobjects.org). The pages were a copy of a wordpress page.

The input files are not part of the fat jar to avoid the annoying build process when changing a semicolon. So to start the application one not just use "java -jar example-springboot/target/example-springboot-0.7.0  
.jar"

#### Start
Instead with linux you have to start with

   java -cp example-springboot/target/example-springboot-0.9.4-SNAPSHOT.jar:example-springboot/input/ org.springframework.boot.loader.PropertiesLauncher

Or with windows use ";" instead of ":" as separator

   java -cp example-springboot/target/example-springboot-0.9.4-SNAPSHOT.jar;example-springboot/input/ org.springframework.boot.loader.PropertiesLauncher

#### Useed Modules
It uses configurations from the modules
* [elasticobjects](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects/src/main/resources)
* [elastic-objects-test](https://github.com/fluentcodes/elasticobjects/tree/master/elastic-objects-test/src/main/resources)

The starting point is the  
[Start class](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/Start.java).

The pages uses path parameters so the links are pure HTML links. The spring boot example is mostly a tool
to navigate through the used configurations.

### Web Controller
There are two Web-Controller classes defined.
* [WebEo](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEo.java) defines two generic post end points
* [WebEoGet](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEoGet.java) defines three special get end points using the path parameter to create an EO template call.

### ConfigController
The configurations are loaded in the [EOConfigCached.java](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/EOConfigCached.java)
as a singleton.

### Templates
#### Page Templates
There are thre templates defined for rendering the page
* [ContentPage.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/ContentPage.tpl) for the content
* [ExamplesPage.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/ExamplesPage.tpl) for the examples
+ [ConfigsStartPage.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/ConfigsStartPage.tpl) for entry configuration start
+ [ConfigsPage.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/ConfigsPage.tpl) for navigate through configuration

#### Content Templates
The content will be loaded as a template within the [Content.tpl](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/content/Content.tpl)
from
* [docs](https://github.com/fluentcodes/elasticobjects/tree/master/example-springboot/input/content/docs)
* [root](https://github.com/fluentcodes/elasticobjects/tree/master/example-springboot/input/content/root)

#### Template Parts
Three part are loaded straigtforward:
* [Header.html](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Header.html)
* [Footer.html](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Footer.html)

The [Svg.html](https://github.com/fluentcodes/elasticobjects/blob/master/example-springboot/input/web/Svg.html) for the responsiveness of the example site will be loaded as a file without parsing content.


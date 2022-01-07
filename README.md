[![Open Source Helpers](https://www.codetriage.com/fluentcodes/elasticobjects/badges/users.svg)](https://www.codetriage.com/fluentcodes/elasticobjects)

> A detailed documentation with interactive examples you find at [elasticobjects.org](https://www.elasticobjects.org/examples/ExamplesStart.html).  
> This website is an example by itself. Its build by one generic spring boot endpoint and  
> EO template calls.

<h1>EO - Elastic Objects</h1>
<p>
Elastic Objects is a java framework using typed JSON for looseless communication with
composed objects by one generic endpoint.
</p>
<p>
Functionality is provided by special
<nobreak><a href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>call</a></nobreak>
objects with a generic execution method using a <strong>source path</strong> as <strong>input</strong> and
a <strong>target path</strong> for <strong>output</strong>.
</p>
<p>
Compared with todays RPC concepts its more a "Remote Object Call" (ROC) architecture. One can easily
create new calls for almost anything. A client can create a typed JSON message addressing call objects
in any combination.
</p>
<p>
There are some predefined generic  calls e.g. for
<a href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/files/FileReadCall.java">files</a>,
<a href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists/CsvSimpleReadCall.java">csv</a>,
<a href="eo-xlsx/src/main/java/org/fluentcodes/projects/elasticobjects/calls/xlsx/XlsxReadCall.java">Excel</a>,
<a href="eo-db/src/main/java/org/fluentcodes/projects/elasticobjects/calls/db/DbSqlReadCall.java">database</a> or
<a href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateCall.java">templates</a>.
The calls implemented are used in the two applications:
<ul>
<li>the documentation on
<a href="http://elasticobjects.org/">http://elasticobjects.org/"</a> in module
<a href="example-springboot">example-springboot</a></li>
<li> the <a href="builder">builder</a> for generation java code and configurations.
</li>
</ul>
</p>



<h3>Elastic Objects</h5>
<p>
    Elastic Objects is a generic object wrapper skin
    with <strong>typed</strong> path methods to an java object skeleton.
    Typed objects are embedded in an untyped map structure.
</p>
<img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/eoTree.svg" width="200" style="margin:20px;"/>

<p>
Some code examples you will find in <a href="http://elasticobjects.org/eo/EO.html">http://elasticobjects.org/eo/EO.html</a>.
</p>
<h3>Model Configurations</h5>
    For the access to the embedded java objects EO
    is provided by preloaded <a href="http://elasticobjects.org/configs/ModelConfig.html">model configurations</a> in JSON.
</p>
<img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/eoModel.svg" width="200" style="margin:20px;"/>

<h3>Call Types</h5>
<p>A special
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>Call</a>
 bean with a
    generic execution method offers <strong>functionality</strong>. Its has the following important fields: </p>

<ul>
    <li>
<a href="http://elasticobjects.org/config/FieldConfig/sourcePath">&equiv;sourcePath</a>
 for the location of the input</li>
    <li>
<a href="http://elasticobjects.org/config/FieldConfig/targetPath">&equiv;targetPath</a>
 for storing the output</li>
    <li>
<a href="http://elasticobjects.org/config/FieldConfig/condition">&equiv;condition</a>
</li>
</ul>
<img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/eoCall.svg" width="200" style="margin:20px;"/>

<h3>Pseudo JSON Example</h5>
<p>
    The following pseudo code would call the execute method in the <strong>ACall</strong> instance,
    which uses the <strong>AnObject</strong> object provided in <em>input</em> path
    and store the result in <em>target</em> path.

    {
        "(AnObject)input":{...},
        "(ACall)target:{"sourcePath":"input"}
    }
</p>

<h3>A CSV Example</h5>
<p>
This example is executable on
<a href="https://www.elasticobjects.org/Home.html#templateResourceCallHtml">elasticobjects.org</a>.
</p>

    {
    "data": {
        "(CsvSimpleReadCall)csv":{
             "configKey"="AnObject.csv"
        },
        "(TemplateResourceCall)abc":{
            "configKey":"table.tpl",
            "sourcePath":"/data/csv",
            "targetPath":"/_asTemplate"
        }
    },
    "asTemplate":true
    }


<h4>Elements</h4>
<ul>
    <li>
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists/CsvSimpleReadCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>CsvSimpleReadCall</a>
 reads
<nobreak><a target="github" href="elastic-objects-test/src/main/resources/input/assets/bt/AnObject.csv"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject.csv</a>
 and store it under the path "/data/csv"</li>
    <li>
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateResourceCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>TemplateResourceCall</a>
 use /data/csv as input
<nobreak><a target="github" href="example-springboot/src/main/resources/templates/table.tpl"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>table.tpl</a>
 and store it under the path "_asTemplate"</li>
</ul>
<a href="http://elasticobjects.org/config/ModelConfig/TemplateResourceCall">&equiv;TemplateResourceCall</a>
 is part of the core module.
    Templates are just files with certain placeholders .
</p>





<h3>Sending Template</h5>
<p>
    The following example is executable on
    <a href="https://www.elasticobjects.org/Home.html#templateCall">elasticobjects.org</a>
</p>

    <h1>An Example Template</h1>
    
    <h2>CSV</h2>
    #{CsvSimpleReadCall->AnObject.csv, data/csv}.
    #{TemplateResourceCall->table.tpl, data/csv}.
    
    <h2>Excel</h2>
    #{XlsxReadCall->AnObject.xlsx:test, data/xlsx}.
    #{TemplateResourceCall->table.tpl, data/xlsx}.
    
    <h2>DB</h2>
    #{DbSqlReadCall->h2:mem:basic, h2:mem:basic:AnObject, data/db}.
    #{TemplateResourceCall->table.tpl, data/db}.


<h4>Short Form</h4>
<p>In the template a short form of one call JSON is used:

     #{CsvSimpleReadCall->AnObject.csv, data/csv}.

is equivalent to

    @{"(CsvSimpleReadCall)":{
         "fileConfigKey":"AnObject.csv",
         "targetPath":"data/csv"     
    }}.

and

    #{TemplateResourceCall->table.tpl, data/csv}.

is equivalent to

    @{"(TemplateResourceCall)":{
         "fileConfigKey"="table.tpl",
         "sourcePath": "data/csv"    
    }}.
 </p>


<h4>Elements</h4>
<p>
   On elasticobjects.org a second endpoint is defined for receiving templates via
   <a target="github" href="example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEo.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>WebEo.java</a>.
</p>
<ul>
    <li>
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists/CsvSimpleReadCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>CsvSimpleReadCall</a>
 with
<nobreak><a target="github" href="elastic-objects-test/src/main/resources/input/assets/bt/AnObject.csv"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject.csv</a>
</li>
    <li>
<nobreak><a target="github" href="eo-xlsx/src/main/java/org/fluentcodes/projects/elasticobjects/calls/xlsx/XlsxReadCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>XlsxReadCall</a>
 with
<nobreak><a target="github" href="example-springboot/input/data/lists/AnObject.xlsx"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject.xlsx:test</a>
</li>
    <li>
<nobreak><a target="github" href="eo-db/src/main/java/org/fluentcodes/projects/elasticobjects/calls/db/DbQueryCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>DbQueryCall</a>
 with
<nobreak><a target="github" href="eo-db/src/main/resources//DbSqlConfig.json"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>DbSqlConfig.json</a></nobreak></li>
</ul>
<p>
    After each read call the
<nobreak><a target="github" href="elastic-objects-test/src/main/java/org/fluentcodes/projects/elasticobjects/domain/test/AnObject.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject</a>
 example data a
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateResourceCall.java"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>TemplateResourceCall</a>
 will render this
    data with
<nobreak><a target="github" href="example-springboot/src/main/resources/templates/table.tpl"> <img src="https://raw.githubusercontent.com/fluentcodes/elasticobjects/master/example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>table.tpl</a>
 again.
</p>

<h3>Other Examples</h5>
<h4>Service Examples</h4>
<p>
    Under <a href="http://elasticobjects.org/examples/ExamplesStart.html">http://elasticobjects.org/examples/ExamplesStart.html</a>
    you find further working editable service examples:
</p>
<ul>
<li><a href="http://elasticobjects.org/examples/TheGreetingCall.html">TheGreetingCall</a></li>
<li><a href="http://elasticobjects.org/examples/FileCall.html">FileCall</a></li>
<li><a href="http://elasticobjects.org/examples/JsonCall.html">JsonCall</a></li>
<li><a href="http://elasticobjects.org/examples/ListCall.html">ListCall</a></li>
<li><a href="http://elasticobjects.org/examples/DbCall.html">DbCall</a></li>
<li><a href="http://elasticobjects.org/examples/TemplateCall.html">TemplateCall</a></li>
<li><a href="http://elasticobjects.org/examples/ConfigsCall.html">ConfigsCall</a></li>
</ul>




<h4>Direct Java Usage</h4>
<p>
    Under the
<a href="http://elasticobjects.org/eo/EO.html">http://elasticobjects.org/eo/EO.html</a>
    you find examples to use EO in a java code context.

<ul>
    <li><a href="http://elasticobjects.org/eo/Compare.html">compare</a></li>
    <li><a href="http://elasticobjects.org/eo/Merge.html">merge</a></li>
    <li><a href="http://elasticobjects.org/eo/Transform.html">transform</a></li>
</ul>
</p>

<h3>Conclusion</h3>
<p>
The project has now version 0.9.2 and it's good enough for a proof of concept. For
the hyped microservice architectures it would offer an incredible flexibility compared with
RPC API solutions.
</p>

<!--# Elastic Objects
<p>
Elastic Objects is an object wrapper around the skeleton of hierarchical Java objects.
<a href="elasticobjects.org/config/AnObject">AnObject</a> is a real test class for this and that....
</p>

    AnObject anObject = new AnObject();
    anObject.setMyString("value");
    EoRoot eoRoot = EoRoot.OFanObject);
<p>
It offers
<strong>path</strong>
methods to create, set or access objects.
</p>

    eoRoot.set(anObject, "a/b/c");
    AnObject anObject2 = eoRoot.get("a/b/c");
    EO eoChild = eoRoot.getEo("a/b/c");
    AnObject anObject3 = eoChild.get();

<p>
    The path could be a typed path:
</p>

    eoRoot.set("value", "a/(AnObject)b/myString");

<p>
   And JSON serialization and deserialization uses the same pattern:
</p>

    {
        "a":{
            "(AnObject)b":{
                 "myString":"value"
            }
        }
    }
</p>
<p>
    In a service application a client is free to combine any typed data in a JSON message
    by one generic endpoint.
</p>

    {
        "(AnObject)input":{...},
        "(ACall)target:{"sourcePath":"input"}
    }

<p>
   <a href="elasticobjects.org/config/Call">Calls</a> allow to add functionality with in the same pattern,
   since calls are just beans with a
generic execute method with EO as input.
</p>-->

### Modules
Actually three modules are deployed to [Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

#### elastic-objects
The [core](elastic-objects) has actually no dependencies beside Log4j  
and is rather small with a jar size of approximately 270 KB. It already includes the template calls.
```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.8.0</version>
    </dependency>
```

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/elastic-objects">
<font size="1">mvn repository</font>
</a></div>

#### elastic-objects-test
The objectives [elastic-objects-test](elastic-objects-test) is providing all tests for elastic-object module together with a main package providing test helper and test objects to other modules.

#### examples-springboot
[examples-springboot](examples-springboot)
are the sources for the spring boot web example on
[http://www.elasticobjects.org](http://www.elasticobjects.org).

#### eo-csv
[eo-csv](eo-csv) offers calls and configurations for reading and writing csv files using [OpenCsv](https://mvnrepository.com/artifact/com.opencsv/opencsv).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-csv</artifactId>
        <version>0.8.0</version>
    </dependency>
    
    <div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-csv">
<font size="1">mvn repository</font>
</a></div>

#### eo-db
[eo-db](eo-db)
is provide the execution of some sql configurations as list or as query.

#### eo-xlsx
[eo-xlsx](eo-xlsx) offers calls and configurations for reading and writing xlsx files using [Apache POI](https://mvnrepository.com/artifact/org.apache.poi/poi).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-xlsx</artifactId>
        <version>0.8.0</version>
    </dependency>

#### builder
[builder](eo-builder) offers calls and configurations  
for generating sources like java classes or json configuration by a simple Excel file.

<div align="right" style="font-size:10px"><a href="#page"><font size="2">top</font></a></div>

### Links
* https://tech.signavio.com/2017/json-type-information
* https://www.json.org/json-en.html
* https://de.wikipedia.org/wiki/JSON-LD
* http://restcookbook.com/Mediatypes/json/
* https://gkulshrestha.wordpress.com/2013/11/16/embedding-type-information-in-json-posted-to-web-api/
* https://www.newtonsoft.com/json/help/html/T_Newtonsoft_Json_TypeNameHandling.htm
* https://de.wikipedia.org/wiki/JavaScript_Object_Notation
* http://jsonp.eu/
* https://github.com/json-path/JsonPath
[![Open Source Helpers](https://www.codetriage.com/fluentcodes/elasticobjects/badges/users.svg)](https://www.codetriage.com/fluentcodes/elasticobjects)

> A detailed documentation with interactive examples you find at [elasticobjects.org](https://www.elasticobjects.org/examples/ExamplesStart.html).  
> The web site itself is build by one generic spring boot endpoint and  
> EO template calls.

# (EO) Elastic Objects

Elastic Objects offers path access methods to java object trees. The underlying
access to java object is passed via
**object configurations** identified by a key.

The json serialization/deserialization could include the object configuration key. This allows
typesafe communication without endpoints or web frameworks.

## Core (elastic-objects)
### Module
The [core](elastic-objects) has actually no dependencies beside Log4j and is rather small with a jar size of approximately 90 KB.

    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.9.3</version>
    </dependency>

<div align="right" id="mvn">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/elastic-objects">mvn repository</a></div>

### Examples
<p>
The following examples you find in
<nobreak><a href="elastic-objects-test/src/test/java/org/fluentcodes/projects/elasticobjects/documentation/EOReadmeTest.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;EOReadmeTest</a></nobreak>.
The test object is
<nobreak><a href="elastic-objects-test/src/main/java/org/fluentcodes/projects/elasticobjects/domain/test/AnObject.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;AnObject</a></nobreak>
with the configurations
<nobreak><a href="elastic-objects-test/src/main/resources/ModelConfig.json"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;ModelConfig.json</a></nobreak>
and
<nobreak><a href="elastic-objects-test/src/main/resources/FieldConfig.json"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;FieldConfig.json</a></nobreak>
.
</p>

#### Get and Set

Some get and set operations.

    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
    EOInterfaceScalar child = root.set("test", "myAnObject", "myString");
    
    assertEquals("test", root.get("myAnObject", "myString"));
    assertEquals("test", child.get());

#### Underlying object
The set changes the objects value by the model configuration:

    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
    root.set("test", "myAnObject", "myString");
    
    AnObject anObject = (AnObject) root.get();
    assertEquals("test", anObject.getMyAnObject().getMyString());    

#### String Path Representation

One can use a path string representation similar to a unix.

    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
    EOInterfaceScalar child = root.set("test", "myAnObject/myString");
    
    assertEquals("test", root.get("myAnObject/myString"));
    assertEquals("test", child.get());

#### Remove child

One can remove a branch from the object tree in a path way.

    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
    EOInterfaceScalar child = root.set("test", "myAnObject/myString");
    
    EO parent = child.remove();
    assertFalse(parent.hasEo("myString"));
    
    AnObject parentObject = (AnObject) parent.get();
    assertNull(parentObject.getMyString());    

#### Field Length Restrictions

The myString field has a max size of 20. It will be checked when set a value:

    EoRoot root = EoRoot.ofClass(CONFIG_MAPS, AnObject.class);
    assertEquals(AnObject.class, root.getModelClass());g
    Assertions.assertThatThrownBy(
            ()->{root.set("test01234567890123456789", "myString");})
            .isInstanceOf(EoException.class)
            .hasMessageContaining("Problem creating child at '/' with key 'myString' with value 'test01234567890123456789' with message String value for field 'test01234567890123456789' has size 24 bigger than max length 20.");

This example has also an interactive example at https://www.elasticobjects.org/examples/AnObjectTooLong.html.

#### Restrictions to a Map
<p>
It's possible to add field configurations to a map. In
<nobreak><a href="elastic-objects-test/src/test/resources/ModelConfig.json"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;ModelConfig.json</a></nobreak>
the map configuration "AnObjectMap" is defined.
</p>
Here the previous example with a length example but the underlying object is a map:

    EoRoot root = EoRoot.ofClassName(CONFIG_MAPS, "AnObjectMap");
    assertEquals(LinkedHashMap.class.getSimpleName(), root.get().getClass().getSimpleName());
    Assertions.assertThatThrownBy(
            ()->{root.set("test01234567890123456789", "myString");})
            .isInstanceOf(EoException.class)
                .hasMessageContaining("Problem creating child at '/' with key 'myString' with value 'test01234567890123456789' with message String value for field 'test01234567890123456789' has size 24 bigger than max length 20.");

This example has also an interactive example at https://www.elasticobjects.org/examples/AnObjectMapTooLong.html.

##### Field does not exist

When field definition are set, also names will be checked:

    EoRoot root = EoRoot.ofClassName(CONFIG_MAPS, "AnObjectMap");
    Assertions.assertThatThrownBy(
            ()->{root.set("test", "notValid");})
            .isInstanceOf(EoException.class)
            .hasMessageContaining("Problem creating child at '/' with key 'notValid' with value 'test' with message No field defined for 'notValid'.");

This example has also an interactive example at https://www.elasticobjects.org/examples/AnObjectMapFieldNotExists.html.

#### Typed JSON

The default json representation contains keys of the model configurations:

    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
    EOInterfaceScalar child = root.set("test", "myAnObject", "myString");
    assertEquals("{\n" +
            "  \"_rootmodel\": \"AnObject\",\n" +
            "  \"(AnObject)myAnObject\": {\n" +
            "    \"myString\": \"test\"\n" +
            "  }\n" +
            "}", root.toJson());

#### From JSON

This typed json will mapped to the appropriate object class when deserialized:

    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
    root.set("test", "myAnObject", "myString");
    String json = root.toJson();      
     
    EoRoot rootFromJson = EoRoot.ofValue(CONFIG_MAPS, json);       
    assertEquals(AnObject.class, rootFromJson.get().getClass());
    
    AnObject myAnObject = (AnObject)rootFromJson.get();
    assertEquals("test", myAnObject.getMyAnObject().getMyString());

#### Compare
The comparision of two objects with same key/values also of different types are rather simple to implement.

Here a map will be compared with AnObject.

    final Map map = new HashMap();
    map.put("myString", "value1");

    final AnObject anObject = new AnObject();
    anObject.setMyString("value2");

    final EoRoot rootMap = EoRoot.ofValue(CONFIG_MAPS, map);
    final EoRoot rootAnObject = EoRoot.ofValue(CONFIG_MAPS, anObject);

    assertEquals("/myString: value1 <> value2", rootMap.compare(rootAnObject));

## Calls (eo-calls)
### eo-calls Module
The [calls](elastic-objects) module with a jar size of about 150 KB offers some basic calls for files and directories, simple csv or templates with a role permission concept.

    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-calls</artifactId>
        <version>0.9.2</version>
    </dependency>

  <div align="right" style="font-size:9px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-calls">mvn repository</font></a></div>


### eo-calls Examples
<p>
The call classes implementing the
<nobreak><a href="elastic-objects/src/test/java/org/fluentcodes/projects/elasticobjects/calls/Call.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;Call</a></nobreak>
interface offer functionality. A bunch of calls including file access or
templates you find in
<nobreak><a href="eo-calls/src/test/java/org/fluentcodes/projects/elasticobjects/"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;eo-calls</a></nobreak>.
</p>

<p>
 The following examples using
 <nobreak><a
 href="eo-calls/src/main/java/org/fluentcodes/projects/elasticobjects/values/SinusValueCall.java"> <img
 src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"
 />&nbsp;SinusValueCall</a></nobreak>
are found in
<nobreak><a href="eo-calls/src/test/java/org/fluentcodes/projects/elasticobjects/documentation/EOReadmeTest.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>&nbsp;EOReadmeTest</a></nobreak>.
</p>

#### Java Example

<p>
The generic execute method has EO as input. Here we set the field key "source" to 2.1 and direcly call
 <nobreak><a
 href="eo-calls/src/main/java/org/fluentcodes/projects/elasticobjects/values/SinusValueCall.java"> <img
 src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"
 />&nbsp;SinusValueCall</a></nobreak>.
</p>

    final Call call = new SinusValueCall();
    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new HashMap());
    EOInterfaceScalar child = root.set(2.1, "source");
    assertEquals(2.1, child.get());

    assertEquals(Double.valueOf(0.8632093666488737), call.execute(child));

#### JSON Example

This call could be also embedded in some arbitrary json using the "sourcePath" as input. The value target will be used implicitly as "targetPath" value.

    EoRoot root = EoRoot.ofValue(CONFIG_MAPS, "{\n" +
            "  \"(Double)source\":1,\n" +
            "  \"(SinusValueCall)/target\": {\n" +
            "    \"sourcePath\": \"/source\"\n" +
            "  }\n" +
            "}");
    root.execute();
    assertEquals("{\n" +
            "  \"source\": 1.0,\n" +
            "  \"target\": 0.8414709848078965\n" +
            "}", root.toJson(JSONSerializationType.STANDARD));

This example has also an interactive example at https://www.elasticobjects.org/examples/SinusValueCall.html.

#### Template Example

This json will be interpreted in an arbitrary text file via template call with the "@{...}" pattern. Here the target _asString will return the result to the template instead of setting the target in json.

        EoRoot root = EoRoot.of(CONFIG_MAPS);
        String template = "START - @{\n" +
                "  \"(Double)source\":1,\n" +
                "  \"(SinusValueCall)_asString\": {\n" +
                "    \"sourcePath\": \"/source\"\n" +
                "  }\n" +
                "}. - END";
        Call call = new TemplateCall(template);
        assertEquals("START -0.8414709848078965 - END", call.execute(root));

This example has also an interactive example at https://www.elasticobjects.org/examples/SinusValueCallTemplate.html.

## Csv (eo-csv)
[eo-csv](eo-csv) offers calls and configurations for reading and writing csv files  using [OpenCsv](https://mvnrepository.com/artifact/com.opencsv/opencsv).

    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-csv</artifactId>
        <version>0.9.3</version>
    </dependency>

<div align="right" style="font-size:9px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-csv">mvn repository</a>
</div>

Interactive examples you can find  at https://www.elasticobjects.org/examples/ListCall.html.

## Database (eo-db)
[eo-db](eo-db)
is experimental providing the execution of sql configurations as list or as query.

    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-db</artifactId>
        <version>0.9.3</version>
    </dependency>

<div align="right">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-db" style="font-size:9px;">mvn repository</font></a></div>

## Excel (eo-xlsx)
[eo-xlsx](eo-xlsx) offers calls and configurations for reading and writing xlsx files using [Apache POI](https://mvnrepository.com/artifact/org.apache.poi/poi).

    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-xlsx</artifactId>
        <version>0.9.3</version>
    </dependency>

<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-xlsx" style="font-size:9px;">mvn repository</font></a></div>

Interactive examples you can find  at https://www.elasticobjects.org/examples/ExcelCall.html.

## Other Modules
### elastic-objects-test
The objectives [elastic-objects-test](elastic-objects-test) is providing all tests for elastic-object module together with a main package providing test helper and test objects to other modules.

### examples-springboot
[examples-springboot](example-springboot)
are the sources for the spring boot web example on
[http://www.elasticobjects.org](http://www.elasticobjects.org).

## Background

<p>Functionality is provided by special
<nobreak><a href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>call</a></nobreak>
objects with a generic execution method using a <strong>source path</strong> as <strong>input</strong> and
a <strong>target path</strong> for <strong>output</strong>.
</p>

Compared with todays RPC concepts its more a "Remote Object Call" (ROC) architecture. One can easily
create new calls for almost anything. A client can create a typed JSON message addressing call objects
in any combination.

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



### Elastic Objects
<p>
    Elastic Objects is a generic object wrapper skin
    with <strong>typed</strong> path methods to an java object skeleton.
    Typed objects are embedded in an untyped map structure.
</p>
<img src="example-springboot/src/main/resources/static/pics/eoTree.svg" width="200" style="margin:20px;"/>

<p>
Some code examples you will find in <a href="http://elasticobjects.org/eo/EO.html">http://elasticobjects.org/eo/EO.html</a>.
</p>

### Model Configurations

For the access to the embedded java objects EO
    is provided by preloaded <a href="http://elasticobjects.org/configs/ModelConfig.html">model configurations</a> in JSON.
</p>
<img src="example-springboot/src/main/resources/static/pics/eoModel.svg" width="200" style="margin:20px;"/>

### Call Types
<p>A special
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>Call</a>
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
<img src="example-springboot/src/main/resources/static/pics/eoCall.svg" width="200" style="margin:20px;"/>

### Pseudo JSON Example
<p>
    The following pseudo code would call the execute method in the <strong>ACall</strong> instance,
    which uses the <strong>AnObject</strong> object provided in <em>input</em> path
    and store the result in <em>target</em> path.

    {
        "(AnObject)input":{...},
        "(ACall)target:{"sourcePath":"input"}
    }
</p>

###  A CSV Example

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


### Elements

<ul>
    <li>
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists/CsvSimpleReadCall.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>CsvSimpleReadCall</a>
 reads
<nobreak><a target="github" href="elastic-objects-test/src/main/resources/input/assets/bt/AnObject.csv"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject.csv</a>
 and store it under the path "/data/csv"</li>
    <li>
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateResourceCall.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>TemplateResourceCall</a>
 use /data/csv as input
<nobreak><a target="github" href="example-springboot/src/main/resources/templates/table.tpl"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>table.tpl</a>
 and store it under the path "_asTemplate"</li>
</ul>
<a href="http://elasticobjects.org/config/ModelConfig/TemplateResourceCall">&equiv;TemplateResourceCall</a>
 is part of the core module.
    Templates are just files with certain placeholders .
</p>





### Sending Template

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


#### Short Form

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


#### Elements

<p>
   On elasticobjects.org a second endpoint is defined for receiving templates via
   <a target="github" href="example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/web/WebEo.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>WebEo.java</a>.
</p>
<ul>
    <li>
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/lists/CsvSimpleReadCall.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>CsvSimpleReadCall</a>
 with
<nobreak><a target="github" href="elastic-objects-test/src/main/resources/input/assets/bt/AnObject.csv"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject.csv</a>
</li>
    <li>
<nobreak><a target="github" href="eo-xlsx/src/main/java/org/fluentcodes/projects/elasticobjects/calls/xlsx/XlsxReadCall.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>XlsxReadCall</a>
 with
<nobreak><a target="github" href="example-springboot/input/data/lists/AnObject.xlsx"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject.xlsx:test</a>
</li>
    <li>
<nobreak><a target="github" href="eo-db/src/main/java/org/fluentcodes/projects/elasticobjects/calls/db/DbQueryCall.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>DbQueryCall</a>
 with
<nobreak><a target="github" href="eo-db/src/main/resources//DbSqlConfig.json"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>DbSqlConfig.json</a></nobreak></li>
</ul>
<p>
    After each read call the
<nobreak><a target="github" href="elastic-objects-test/src/main/java/org/fluentcodes/projects/elasticobjects/domain/test/AnObject.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>AnObject</a>
 example data a
<nobreak><a target="github" href="elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates/TemplateResourceCall.java"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>TemplateResourceCall</a>
 will render this
    data with
<nobreak><a target="github" href="example-springboot/src/main/resources/templates/table.tpl"> <img src="example-springboot/src/main/resources/static/pics/github.png" height="12" width="12" " style="margin:0px 4px 0px 6px;"/>table.tpl</a>
 again.
</p>

### Other Examples
#### Service Examples

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




### Direct Java Usage
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

## Conclusion

<p>
The project has now version 0.9.3 and it's good enough for a proof of concept. For
the microservice architectures it offer an incredible flexibility compared with
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


## Links
* https://tech.signavio.com/2017/json-type-information
* https://www.json.org/json-en.html
* https://de.wikipedia.org/wiki/JSON-LD
* http://restcookbook.com/Mediatypes/json/
* https://gkulshrestha.wordpress.com/2013/11/16/embedding-type-information-in-json-posted-to-web-api/
* https://www.newtonsoft.com/json/help/html/T_Newtonsoft_Json_TypeNameHandling.htm
* https://de.wikipedia.org/wiki/JavaScript_Object_Notation
* http://jsonp.eu/
* https://github.com/json-path/JsonPath
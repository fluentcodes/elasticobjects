<div align="right" clear="left">
<font size="2">
<a href="#path">Path</a><br>
<a href="#json">JSON</a><br>
<a href="#under-the-hood">Under the Hood</a><br>
<a href="#further-documentation">Further-Documentation</a><br>
<a href="#packages">Packages</a><br>
<a href="#status">Status</a><br>
</font>
</div>

# Elastic Objects

Elastic Objects is a small java application framework for handling complex objects via [path](#path). 

It's serialization with [json](#json) has some specialties:
* [embedded type directives](#typed) offers looseless data exchange without webservers or REST. 
* [unmapped fields](#unmapped) starting with "_" allow integration of extra information in JSON like comments.
* every type directive implementing the [Call bean](#calls) trigger an execution on the target system. 

<div align="right" style="font-size:10px"><a href="#top"><font size="2">top</font></a></div>

#### Path
 [EO](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/EO.java) allows creating, accessing and modifing complex Java objects via path. Non existing elements will be created automatically. 


    EO child = eo.set("value","level0/level1/level2/level3");
    assertThat(child.get()).isEqualTo("value");
    assertThat(eo.get("level0/level1/level2/level3")).isEqualTo("value");
<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/EoSetScalarTest">
<font size="2"><a href="#packages">example</a><br></font>
</a></div>

One can integrate typed objects in a complex structure and access it without loosing the type.

    BasicTest bt = new BasicTest()
       .setTestString("value");
    eo.set(bt, "level0");
    assertThat(eo.get("level0/testString")).isEqualTo("value");
    assertThat(eo.getEo("level0").getModelClass()).isEqualTo(BasicTest.class);
<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapSetBtTest">
<font size="2"><a href="#packages">example</a><br></font>
</a></div>
Objects will be automatically mapped to the existing model class. This allows easy merge and conversion of objects with same names.

    final EO eo = ProviderRootTestScope.createEo(Map.class);
    final BasicTest bt = new BasicTest()
       .setTestString("value");
    eo.mapObject(bt);
    assertThat(((Map)eo.get())get()"testString")).isEqualTo("value");
    assertThat(eo.getModelClass()).isEqualTo(Map.class);

<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapObjectBtTest">
<font size="2"><a href="#packages">example</a><br></font>
</a></div>

The last example the other way round:

    final EO eo = ProviderRootTestScope.createEo(BasicTest.class);
    final Map map = new HashMap()
    map.put("testString", "value");
    eo.mapObject(map);
    assertThat(((BasicTest)eo.get()).getTestString()).isEqualTo("value");
    assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);

<div align="right" style="font-size:10px">
<a href="https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/assets/EoMapObjectBtTest">
<font size="2"><a href="#packages">example</a><br></font>
</a></div>

Elastic objects offers some nice tools for objects which can be used in a native solution. 

<div align="right" style="font-size:10px"><a href="#top"><font size="2">top</font></a></div>

#### JSON
The serialization/deserialization implementation extends some limitations of standard json 
It allows 
* embed type directives in the name allows typesafe transfer
* embed information with names starting with "_" (e.g _comment)
* embed calling functionality with special call classes implementing an execute method.

##### Untyped
Standard JSON will be interpreted to standard untyped objects like map or list.
```
{
	"level0":{
		"testString":"value"
    }
}
```


```
eo.mapObject(jsonString);
assertEquals("value", child.get(eo.get("level0/testString"));
assertEquals(Map.class, eo.getEo("level0").getModelClass());
```
##### Typed
The type directive is embedded in the name in java-style: (Type)name

    {
	   "(BasicTest)level0":{
		    "testString":"value"
       }
    }

    

    eo.mapObject(jsonString);
    assertEquals("value", eo.get(eo.get("level0/testString"));
    assertEquals(BasicTest.class, eo.getEo("level0").getModelClass());
    assertEquals("value", ((BasicTest)eo.get("level0")).getTestString());


##### Unmapped
All fieldnames starting with _ will not be mapped to the underlying object:
```
{
	"level0":{
		"(BasicTest)testString":"value",
        "_comment":"_comment is not a field of the BasicTest.class"
    }
}
```

```
eo.mapObject(jsonString);
assertEquals("value", eo.get(eo.get("level0/testString"));
Assertions.assertThat(eo.get(eo.get("level0/_comment")).contains("BasicTest.class");
assertEquals(BasicTest.class, eo.getEo("level0").getModelClass());
```
##### Calls
With the [call]((https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/Call.java) type beans containing the execute method its very easy to embed function calls. 

This simple [example](https://github.com/fluentcodes/elasticobjects/blob/master/eo-test/src/test/java/org/fluentcodes/projects/elasticobjects/calls/values/SinusValueCallTest.java) computes the [sinus](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/SinusValueCall.java) from 1 and replace it. 
```
{
  "(Double)source":1,
  "(List)_calls": {
    "(SinusValueCall)0": {
      "sourcePath": "/source"
    }
  }
}
```

```
eo.mapObject(jsonString);
eo.execute();
Assertions.assertThat(eo.get("source")).isEqualTo(0.8414709848078965);
Assertions.assertThat(eo.getEo("source").isChanged()).isTrue();
```
<div align="right" style="font-size:10px"><a href="#top"><font size="2">top</font></a></div>

This is a rather small example and the implementation [SinusValueCall](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/SinusValueCall.java) is minimal and no restrictions for execution need to be made, since only a value is set to EO.

Other examples for these simple calls you can find under the [calls/values](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/values/) package.

When it comes to read or write something on the server, it's a more complex topic. The following Calls are implemented: 

* [File Access](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/files)
* [JSON File Access](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/json)
* [String Separated File Access](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/scs)
* [Templates](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/calls/templates)

These calls use also configurations with a permission part. 

### Under The Hood


<div align="right" style="font-size:10px"><a href="#top"><font size="2">top</font></a></div>


### Further documentation

An in depth documentation is created on this [github wiki](https://github.com/fluentcodes/elasticobjects/wiki) and the site http://elasticobjects.com



<div align="right" style="font-size:10px"><a href="#top"><font size="2">top</font></a></div>


### Packages
Actually you find here three modules deployed on [Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

#### elastic-objects
The [core](https://github.com/fluentcodes/elasticobjects/tree/master/eo) has actually no dependencies beside Log4j and is rather small with approximately 160 KB.
```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.2.0-SNAPSHOT</version>
    </dependency>
```

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo">
<font size="1">mvn repository</font>
</a></div>

#### eo-csv
[eo-csv](https://github.com/fluentcodes/elasticobjects/tree/master/eo-csv) offers calls and configurations for reading and writing csv files using [OpenCsv](https://mvnrepository.com/artifact/com.opencsv/opencsv).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-csv</artifactId>
        <version>0.2.0-SNAPSHOT</version>
    </dependency>

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-csv">
<font size="1">mvn repository</font>
</a></div>
#### eo-xlsx
[eo-xlsx](https://github.com/fluentcodes/elasticobjects/tree/master/eo-xlsx) offers calls and configurations for reading and writing xlsx files using [Apache POI](https://mvnrepository.com/artifact/org.apache.poi/poi).


    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>eo-xlsx</artifactId>
        <version>0.2.0-SNAPSHOT</version>
    </dependency>

<div align="right" style="font-size:10px">
<a href="https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-xlsx">
<font size="1">mvn repository</font>
</a></div>

<div align="right" style="font-size:10px"><a href="#top"><font size="2">top</font></a></div>

### Status
After a lot of breaks the java version is now in a state I could accept as "fit" to the concept. It's basic mechanism works direct and with minimal implementation flourish.


### Links
* https://help.github.com/articles/licensing-a-repository/

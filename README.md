# Elastic Objects

Elastic Objects
is a small java application framework
for handling complex objects.
An in depth documentation is created on this [github wiki](https://github.com/fluentcodes/elasticobjects/wiki)
and the site http://elasticobjects.com


Its core
[EO](https://github.com/fluentcodes/elasticobjects/blob/master/elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/eo/EO.java)
is a wrapper
for to creating, accessing and modifing Java Object via path.
It has a type safe and ordered
 JSON serialization and deserialization for moving data and
calls.

```
EO child = eo.add("level0/level1/level2/key")
   .set("value"));
assertEquals("value", child.get());
assertEquals("value", eo.get("level0/level1/level2/key"));
```


### Packages
We have actually four packages here.
They jars you find on
[Maven Central](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects).

#### elastic-objects
The core has actually no dependencies beside Log4j and is rather small with approximately 250 KB.
The version 0.1.2 you find on

```
    <dependency>
        <groupId>org.fluentcodes.projects.elasticobjects</groupId>
        <artifactId>elastic-objects</artifactId>
        <version>0.1.2</version>
    </dependency>
```

#### eo-test-resources
This jar is merely for tests here and demos. For the
[spring boot demo](https://github.com/fluentcodes/eo-example-springboot)
this package is used.

#### eo-csv
[eo-csv](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-csv) is the jar
generated by the
[actions-csv](https://github.com/fluentcodes/elasticobjects/tree/master/actions-csv)
module. It offers csv calls and configs using
[OpenCsv](https://mvnrepository.com/artifact/com.opencsv/opencsv)

#### eo-xlsx
[eo-xlsx](https://mvnrepository.com/artifact/org.fluentcodes.projects.elasticobjects/eo-xlsx)
is the jar
generated by the
[actions-xlsx](https://github.com/fluentcodes/elasticobjects/tree/master/actions-xlsx)
module.
It offers xlsx calls and configs using
[Apache POI](https://mvnrepository.com/artifact/org.apache.poi/poi).

#### In Work
The following modules are prepared to this project:
* builder: A generic builder with some common template e.g. for beans.
* builder-eo: The builder I used to generate stuff within eo.
* eo-db: Calls for direct JDBC access.
* eo-hibernate: Calls using hibernate for db access.


### Links
* https://help.github.com/articles/licensing-a-repository/

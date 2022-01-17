package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public enum Modules {
    SP("example-springboot/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    SPR("example-springboot/src/main/resources/"),
    SPT("example-springboot/src/test/java/org/fluentcodes/projects/elasticobjects/"),
    SPTR("example-springboot/src/test/resources/"),
    SPI("example-springboot/input/"),

    B("builder/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    BR("builder/src/main/resources/"),
    BT("builder/src/test/java/org/fluentcodes/projects/elasticobjects/"),
    BTR("builder/src/test/resources/"),

    BEO("builder/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    BEOR("builder/src/main/resources/"),
    BEOT("builder/src/test/java/org/fluentcodes/projects/elasticobjects/"),
    BEOTR("builder/src/test/resources/"),

    EO("elastic-objects/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    EOR("elastic-objects/src/main/resources/"),

    EOT("elastic-objects-test/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    EOTR("elastic-objects-test/src/main/resources/"),
    EOTT("elastic-objects-test/src/test/java/org/fluentcodes/projects/elasticobjects/"),
    EOTTR("elastic-objects-test/src/test/resources/"),

    EOC("eo-calls/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    EOCR("eo-calls/src/main/resources/"),
    EOCT("eo-calls/src/test/java/org/fluentcodes/projects/elasticobjects/"),
    EOCTR("eo-calls/src/test/resources/"),

    EOX("eo-xlsx/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    EOXR("eo-xlsx/src/main/resources/"),
    EOXT("eo-xlsx/src/test/java/org/fluentcodes/projects/elasticobjects/"),
    EOXTR("eo-xlsx/src/test/resources/"),

    EODB("eo-db/src/main/java/org/fluentcodes/projects/elasticobjects/"),
    EODBR("eo-db/src/main/resources/"),
    EODBT("eo-db/src/test/java/org/fluentcodes/projects/elasticobjects/"),
    EODBTR("eo-db/src/test/resources/");

   private String dir;
   private String directory;

   Modules(final String directory) {
        this.directory = directory;
    }
    public static String findDirectory(final String dir) {
       if (dir==null) {
           throw new EoException("Problem getting dirctory with null abbreviation");
       }
       try {
           return valueOf(dir).getDirectory();
       }
       catch (Exception e) {
           throw new EoException("Problem find directory with abbreviation '" + dir + "'");
       }
    }

    public String getDirectory() {
        return directory;
    }
}

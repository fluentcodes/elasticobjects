package org.fluentcodes.projects.elasticobjects.calls.generate;

public enum Moduls {
    BUILDER("builder"),
    BUILDER_EO("builder-eo"),
    BUILDER_HIPSTER("builder-project"),
    EO("elastic-objects"),
    EO_CSV("eo-csv"),

    EO_DB("eo-db"),
    EO_TEST("elastic-objects-test"),
    EO_XLSX("eo-xlsx"),
    SP("example-springboot"),
    ALL(".*");
    private String name;
    Moduls(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

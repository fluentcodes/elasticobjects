package org.fluentcodes.projects.elasticobjects.models;

public interface ModelPropertiesInterface {

    Boolean getAbstract();

    default boolean hasAbstract() {
        return getFinal() != null;
    }

    default Boolean isAbstract() {
        return hasAbstract() && getAbstract();
    }

    Boolean getCreate();

    default boolean hasCreate() {
        return getCreate() != null;
    }

    default boolean isCreate() {
        return hasCreate() && getCreate();
    }

    String getDefaultImplementation();

    default boolean hasDefaultImplementation() {
        return getDefaultImplementation() != null && !getDefaultImplementation().isEmpty();
    }

    Boolean getDbAnnotated();

    default boolean hasDbAnnotated() {
        return getDbAnnotated() != null;
    }

    default Boolean isDbAnnotated() {
        return hasDbAnnotated() && getDbAnnotated();
    }

    Boolean getFinal();

    default boolean hasFinal() {
        return getFinal() != null;
    }

    default boolean isFinal() {
        return hasFinal() && getFinal();
    }

    String getIdKey();

    default boolean hasIdKey() {
        return getIdKey() != null && !getIdKey().isEmpty();
    }

    String getNaturalKeys();

    default boolean hasNaturalKeys() {
        return getNaturalKeys() != null && !getNaturalKeys().isEmpty();
    }

    String getTable();

    default boolean hasTable() {
        return getTable() != null && !getTable().isEmpty();
    }

}

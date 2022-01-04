package org.fluentcodes.projects.elasticobjects.models;

public interface ModelInterface {
    String DEFAULT_IMPLEMENTATION = "defaultImplementation";
    String SHAPE_TYPE = "shapeType";
    String F_CREATE = "create";
    String CLASS_PATH = "classPath";
    String ID_KEY = "idKey";
    String NATURAL_KEYS = "naturalKeys";
    String TABLE = "table";
    String BEAN = "bean";
    String JAVASCRIPT_TYPE = "javascriptType";
    String DB_ANNOTATED = "dbAnnotated";
    String F_ABSTRACT = "abstract";

    String getTable();

    default boolean hasTable() {
        return getTable() != null && !getTable().isEmpty();
    }

    String getIdKey();

    default boolean hasIdKey() {
        return getIdKey() != null && !getIdKey().isEmpty();
    }

    String getNaturalKeys();

    default boolean hasNaturalKeys() {
        return getNaturalKeys() != null && !getNaturalKeys().isEmpty();
    }

    String getModelKey();

    default boolean hasModelKey() {
        return getModelKey() != null && !getModelKey().isEmpty();
    }

    default String getKey() {
        if (hasModelKey()) return getModelKey();
        return "";
    }

    default boolean hasKey() {
        return !getModelKey().isEmpty();
    }

    String getPackagePath();

    default boolean hasPackagePath() {
        return getPackagePath() != null && !getPackagePath().isEmpty();
    }

    String getSuperKey();

    default boolean hasSuperKey() {
        return getSuperKey() != null && !getSuperKey().isEmpty();
    }

    String getInterfaces();

    default boolean hasInterfaces() {
        return getInterfaces() != null && !getInterfaces().isEmpty();
    }

    Boolean getCreate();

    default boolean hasCreate() {
        return getCreate() != null;
    }

    default boolean isCreate() {
        return hasCreate() && getCreate();
    }

    Boolean getProperty();

    default boolean hasProperty() {
        return getProperty() != null;
    }

    default boolean isProperty() {
        return hasProperty() && getProperty();
    }

    default boolean hasShapeType() {
        return getShapeType() != null;
    }

    ShapeTypes getShapeType();

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

    Boolean getAbstract();

    default boolean hasAbstract() {
        return getFinal() != null;
    }

    default Boolean isAbstract() {
        return hasAbstract() && getAbstract();
    }

    Boolean getFinal();

    default boolean hasFinal() {
        return getFinal() != null;
    }

    default boolean isFinal() {
        return hasFinal() && getFinal();
    }

    Boolean getOverride();

    default boolean hasOverride() {
        return getOverride() != null;
    }

    default boolean isOverride() {
        return hasOverride() && getOverride();
    }


    String getBean();

    default boolean hasBean() {
        return getBean() != null && !getBean().isEmpty();
    }

}

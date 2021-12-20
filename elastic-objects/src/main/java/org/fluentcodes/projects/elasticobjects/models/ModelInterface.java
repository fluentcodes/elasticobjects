package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;
import java.util.Set;

public interface ModelInterface extends ConfigInterface {
    String DEFAULT_IMPLEMENTATION = "defaultImplementation";
    String SHAPE_TYPE = "shapeType";
    String CREATE = "create";
    String CLASS_PATH = "classPath";
    String ID_KEY = "idKey";
    String NATURAL_KEYS = "naturalKeys";
    String TABLE = "table";
    String BEAN = "bean";
    String JAVASCRIPT_TYPE = "javascriptType";
    String DB_ANNOTATED = "dbAnnotated";
    String ABSTRACT = "abstract";

    Map<String, FieldConfig> getFieldMap();

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
        if (hasNaturalId()) return getNaturalId();
        return "";
    }

    default boolean hasKey() {
        return !getModelKey().isEmpty();
    }

    String getPackagePath();

    default boolean hasPackagePath() {
        return getPackagePath() != null && !getPackagePath().isEmpty();
    }

    Set<String> getFieldKeys();

    default boolean hasFields() {
        return getFieldKeys().isEmpty();
    }


    default FieldBeanInterface getField(final String key) {
        return getFieldMap().get(key);
    }

    default boolean hasField(final String key) {
        return getFieldMap().containsKey(key);
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

    Boolean getDbAnnotated();

    default boolean hasDbAnnotated() {
        return getDbAnnotated() != null;
    }

    default Boolean isDbAnnotated() {
        return (hasDbAnnotated() && getDbAnnotated()) || false;
    }

    default boolean isList() {
        return (this instanceof ModelConfigList);
    }

    default boolean isMap() {
        return (this instanceof ModelConfigMap);
    }

    default boolean isScalar() {
        return (this instanceof ModelConfigScalar);
    }

    default boolean isObject() {
        return (this instanceof ModelConfigObject);
    }

    default boolean isCall() {
        return getModelKey().endsWith("Call");
    }

    default boolean isInterface() {
        return getShapeType() == ShapeTypes.INTERFACE;
    }

    default boolean isContainer() {
        return !isScalar();
    }

    default boolean isNumber() {
        return false;
    }

    default boolean hasModel() {
        return true;
    }

    default boolean isNull() {
        return false;
    }

    default boolean isEnum() {
        return false;
    }

    default boolean isJsonIgnore(final String key) {
        return hasField(key) && getField(key).isJsonIgnore();
    }

    default boolean isProperty(final String key) {
        return hasField(key) && getField(key).isProperty();
    }
}

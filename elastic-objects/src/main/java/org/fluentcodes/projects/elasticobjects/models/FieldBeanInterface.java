package org.fluentcodes.projects.elasticobjects.models;

/*.{javaHeader}|*/

/**
 * Access methods for field properties map and get method definitions for final fields.
 *
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 06:06:25 CET 2021
 */
public interface FieldBeanInterface extends ConfigInterface {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    String F_DEFAULT = "default";
    String F_FIELD_KEY = "fieldKey";
    String F_FIELD_NAME = "fieldName";
    String F_FINAL = "final";
    String F_GENERATED = "generated";
    String F_JAVASCRIPT_TYPE = "javascriptType";
    String F_JSON_IGNORE = "jsonIgnore";
    String F_LENGTH = "length";
    String F_MAX = "max";
    String F_MIN = "min";
    String F_MODEL_KEYS = "modelKeys";
    String F_NOT_NULL = "notNull";
    String F_OVERRIDE = "override";
    String F_PROPERTY = "property";
    String F_STATIC_NAME = "staticName";
    String F_SUPER = "super";
    String F_TRANSIENT = "transient";
    String F_UNIQUE = "unique";
    /*.{}.*/

    /*.{javaAccessors}|*/
    Boolean getDefault();

    default boolean hasDefault() {
        return getDefault() != null;
    }

    default boolean isDefault() {
        return hasDefault() && getDefault();
    }

    String getFieldKey();

    default String getKey() {
        if (hasNaturalId()) return getNaturalId();
        if (hasFieldKey()) return getFieldKey();
        return "";
    }

    default boolean hasKey() {
        return !getKey().isEmpty();
    }

    default boolean hasFieldKey() {
        return getFieldKey() != null && !getFieldKey().isEmpty();
    }

    String getFieldName();

    default boolean hasFieldName() {
        return getFieldName() != null && !getFieldName().isEmpty();
    }

    Boolean getFinal();

    default boolean hasFinal() {
        return getFinal() != null;
    }

    default boolean isFinal() {
        return hasFinal() && getFinal();
    }

    Boolean getGenerated();

    default boolean hasGenerated() {
        return getGenerated() != null;
    }

    default boolean isGenerated() {
        return hasGenerated() && getGenerated();
    }

    String getJavascriptType();

    default boolean hasJavascriptType() {
        return getJavascriptType() != null;
    }

    Boolean getJsonIgnore();

    default boolean hasJsonIgnore() {
        return getJsonIgnore() != null;
    }

    default boolean isJsonIgnore() {
        return hasJsonIgnore() && getJsonIgnore();
    }

    Integer getLength();

    default boolean hasLength() {
        return getLength() != null;
    }

    Integer getMax();

    default boolean hasMax() {
        return getMax() != null;
    }

    Integer getMin();

    default boolean hasMin() {
        return getMin() != null;
    }

    String getModelKeys();

    default boolean hasModelKeys() {
        return getModelKeys() != null && !getModelKeys().isEmpty();
    }

    Boolean getNotNull();

    default boolean hasNotNull() {
        return getNotNull() != null;
    }

    default boolean isNotNull() {
        return hasNotNull() && getNotNull();
    }

    Boolean getOverride();

    default boolean hasOverride() {
        return getOverride() != null;
    }

    default boolean isOverride() {
        return hasOverride() && getOverride();
    }

    Boolean getProperty();

    default boolean hasProperty() {
        return getProperty() != null;
    }

    default boolean isProperty() {
        return hasProperty() && getProperty();
    }

    Boolean getStaticName();

    default boolean hasStaticName() {
        return getStaticName() != null;
    }

    default boolean isStaticName() {
        return hasStaticName() && getStaticName();
    }

    Boolean getSuper();

    default boolean hasSuper() {
        return getSuper() != null;
    }

    default boolean isSuper() {
        return hasSuper() && getSuper();
    }

    Boolean getTransient();

    default boolean hasTransient() {
        return getTransient() != null;
    }

    default boolean isTransient() {
        return hasTransient() && getTransient();
    }

    Boolean getUnique();

    default boolean hasUnique() {
        return getUnique() != null;
    }

    default boolean isUnique() {
        return hasUnique() && getUnique();
    }

    /*.{}.*/
    default Object getDefaultValue() {
        return null;
    }

    default boolean hasDefaultValue() {
        return getDefaultValue() != null;
    }

    default boolean hasSize() {
        return hasMax() || hasMin();
    }


    ModelInterface getParentModel();

    default boolean hasParentModel() {
        return getParentModel() != null;
    }

    default boolean hasParentModelKey() {
        return hasParentModel() && getParentModel().hasModelKey();
    }

    default String getModelKey() {
        if (!hasParentModelKey()) return "";
        if (this.hasParentModelKey()) return getParentModel().getModelKey();
        if (hasParentModelNaturalId()) return getParentModel().getNaturalId();
        return "";
    }

    default boolean hasParentModelNaturalId() {
        return hasParentModel() && getParentModel().hasNaturalId();
    }
}



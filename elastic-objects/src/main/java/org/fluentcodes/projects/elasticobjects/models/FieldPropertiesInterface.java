package org.fluentcodes.projects.elasticobjects.models;

/**
 * Interface methods for field properties in {@link FieldBeanProperties} and {@link FieldConfigProperties}.
 *
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 10 06:06:25 CET 2022
 */
public interface FieldPropertiesInterface {

    Boolean getDefault();

    default boolean isDefault() {
        return hasDefault() && getDefault();
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

    Integer getMax();

    default boolean hasMax() {
        return getMax() != null;
    }

    Integer getMin();

    default boolean hasMin() {
        return getMin() != null;
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

    default boolean hasDefault() {
        return this.getDefault() != null;
    }

    default boolean hasSize() {
        return hasMax() || hasMin();
    }

}



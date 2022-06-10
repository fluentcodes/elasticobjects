package org.fluentcodes.projects.elasticobjects.models;

/*.{javaHeader}|*/

/**
 * Access methods for field properties map and get method definitions for final fields.
 *
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 06:06:25 CET 2021
 */
public interface FieldInterface extends ConfigInterface {
    /*.{}.*/

    String F_FIELD_KEY = "fieldKey";
    String F_MODEL_KEYS = "modelKeys";

    String getFieldKey();

    default boolean hasFieldKey() {
        return getFieldKey() != null && !getFieldKey().isEmpty();
    }

    String getModelKeys();

    default boolean hasModelKeys() {
        return getModelKeys() != null && !getModelKeys().isEmpty();
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
        return "";
    }

}



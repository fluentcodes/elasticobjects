package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EOInterfaceModel {
    Models getModels();

    default Class<?> getModelClass() {
        return getModels().getModelClass();
    }

    default ModelConfig getModel() {
        return getModels().getModel();
    }

    default boolean isTransient(final String fieldName) {
        return getModel().hasField(fieldName) && getModel().getField(fieldName).isTransient();
    }

    default boolean isJsonIgnore(final String fieldName) {
        return getModel().hasField(fieldName) && getModel().getField(fieldName).getProperties().isJsonIgnore();
    }

    default boolean isContainer() {
        return !isScalar();
    }

    default boolean isList() {
        return getModels().isList();
    }

    default boolean isObject() {
        return getModels().isObject();
    }

    default boolean isScalar() {
        return getModels().isScalar();
    }

    default boolean isMap() {
        return getModels().isMap();
    }

    default ConfigMaps getConfigMaps() {
        return getModels().getConfigMaps();
    }

    default Scope getScope() {
        return getConfigMaps().getScope();
    }
}

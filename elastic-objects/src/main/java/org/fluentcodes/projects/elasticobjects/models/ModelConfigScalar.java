package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigScalar extends ModelConfig {
    public static final String CONFIG_MODEL_KEY = "ModelConfigScalar";

    public ModelConfigScalar(ConfigBean bean, final ConfigMaps configMaps) {
        this((ModelBean) bean, configMaps);
    }

    public ModelConfigScalar(ModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }

    public boolean isNumber() {
        return getModelClass().getSimpleName().matches("(Integer|Float|Double|Long)");
    }

    public boolean isEnum() {
        return (getModelClass().isEnum());
    }

}

package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;

public class ProviderConfigMaps {
    public static final ConfigMaps CONFIG_MAPS = new ConfigMaps(Scope.TEST);

    public static final EoRoot createEoWithClasses(Class... classes) {
        return EoRoot.ofClass(CONFIG_MAPS, classes);
    }

    public static final EoRoot createEo() {
        return EoRoot.of(CONFIG_MAPS);
    }

    public static final EoRoot createEo(Object value) {
        return EoRoot.ofValue(CONFIG_MAPS, value);
    }

    public static final ModelConfig findModel(final Class eoClass) {
        return CONFIG_MAPS.findModel(eoClass);
    }

    public static final ModelBean createModelBean(final Class eoClass) {
        ModelConfig config = CONFIG_MAPS.findModel(eoClass);
        return new ModelBean(config);
    }

    public static final Map findModelMap(final Class eoClass) {
        ModelConfig config = CONFIG_MAPS.findModel(eoClass);
        EoRoot modelConfigMap = EoRoot.ofClass(CONFIG_MAPS, Map.class);
        modelConfigMap.setSerializationType(JSONSerializationType.STANDARD);
        modelConfigMap.map(config);
        return (Map) modelConfigMap.get();
    }

    public static final Models createModels(Class... classes) {
        return new Models(CONFIG_MAPS, classes);
    }
}
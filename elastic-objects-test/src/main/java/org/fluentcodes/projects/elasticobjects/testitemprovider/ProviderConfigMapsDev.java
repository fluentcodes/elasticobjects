package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.models.ModelFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelFactoryFromConfigurations;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class ProviderConfigMapsDev {
    public static final ConfigMaps CONFIG_MAPS_DEV = new ConfigMaps(Scope.DEV);

    public static final EoRoot createEoWithClasses(Class... classes) {
        return EoRoot.ofClass(CONFIG_MAPS_DEV, classes);
    }

    public static final Models createModels(Class... classes) {
        return new Models(CONFIG_MAPS_DEV, classes);
    }

    public static final EoRoot createEo() {
        return EoRoot.of(CONFIG_MAPS_DEV);
    }

    public static final EoRoot createEo(Object value) {
        return EoRoot.ofValue(CONFIG_MAPS_DEV, value);
    }

    public static final EoRoot assertCompare(IEOObject in, String toCompare) {
        String stringify = new EOToJSON().toJson(in);
        Assert.assertEquals(toCompare, stringify);
        return createEo(stringify);
    }

    public static final Map<String, FieldBean> createFieldBeanMap() {
        return new FieldFactory(CONFIG_MAPS_DEV).createBeanMap();
    }

    public static final Map<String, ModelBean> createModelBeanMap() {
        return new ModelFactoryFromConfigurations(CONFIG_MAPS_DEV).createBeanMap();
    }

    public static final Map<String, ModelInterface> createModelConfigMap() {
        Map<String, ModelInterface> modelConfigMap = new HashMap<>();
        Map<String, ModelBean> modelBeanMap = new ModelFactoryFromConfigurations(CONFIG_MAPS_DEV).createBeanMap();
        for (Map.Entry<String, ModelBean> entry: modelBeanMap.entrySet()) {
            modelConfigMap.put(entry.getKey(), createModelConfig(entry.getValue()));
        }
        return modelConfigMap;
    }

    public static final ModelConfigObject createModelConfig(ModelBean bean) {
        return new ModelConfigObject(bean, CONFIG_MAPS_DEV);
    }

}
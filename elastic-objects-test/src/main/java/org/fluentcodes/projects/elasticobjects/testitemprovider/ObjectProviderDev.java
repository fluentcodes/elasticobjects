package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatMap;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldConfigObject;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigMap;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.models.ModelFactoryFromConfigurations;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_FIELDS;
import static org.junit.Assert.assertEquals;

public class ObjectProviderDev {
    private ObjectProviderDev() {}
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

    public static final EoRoot createRootFromJson(String json, Class... classes) {
        EoRoot root = createEoWithClasses(classes);
        root.map(json);
        return root;
    }

    public static final Map<String, Map<String, Object>> readConfigMap(Class<? extends Config> configClass) {
        return new IOClasspathEOFlatMap<Map<String,Object>>
                (CONFIG_MAPS_DEV, configClass.getSimpleName() + ".json", Map.class)
                .read();
    }

    public static final Map<String, Object> readConfigMap(Class<? extends Config> configClass, final String configKey) {
        return readConfigMap(configClass).get(configKey);
    }

    public static final Map<String, Object> createMapFromJson(String json) {
        EoRoot root = createRootFromJson(json, Map.class);
        return (Map<String, Object>)root.get();
    }

    public static final ModelBean readModelBean(String configKey) {
        return new ModelBean(readConfigMap(ModelConfig.class, configKey));
    }

    public static final FieldBean readFieldBean(String configKey) {
        return new FieldBean(readConfigMap(FieldConfig.class, configKey));
    }

    public static final ModelBean createModelBeanFromJson(String json) {
        return new ModelBean(createMapFromJson(json));
    }

    public static final ModelConfig readModelConfig(String configKey) {
        return new ModelConfigObject(readModelBean(configKey), CONFIG_MAPS_DEV);
    }

    public static final ModelConfig readModelConfigMap(String configKey) {
        return new ModelConfigMap(readModelBean(configKey), CONFIG_MAPS_DEV);
    }

    public static final FieldConfig readFieldConfig(String configKey) {
        return new FieldConfigObject(readFieldBean(configKey), CONFIG_MAPS_DEV);
    }

    public static final ModelConfig createModelConfigFromJson(String json) {
        return new ModelConfigObject(createModelBeanFromJson(json), CONFIG_MAPS_DEV);
    }



    public static final EoRoot createEo(Object value) {
        return EoRoot.ofValue(CONFIG_MAPS_DEV, value);
    }

    public static final EoRoot assertCompare(EO in, String toCompare) {
        String stringify = new EOToJSON().toJson(in);
        Assert.assertEquals(toCompare, stringify);
        return createEo(stringify);
    }

    public static final Map<String, FieldBean> createFieldBeanMap() {
        return new FieldFactory(CONFIG_MAPS_DEV).createBeanMap();
    }

    public static final Map<String, ModelBean> assertCreateModelBeanMap() {
        return new ModelFactoryFromConfigurations(CONFIG_MAPS_DEV).createBeanMap();
    }

    public static final Map<String, ModelConfig> createModelConfigMap() {
        Map<String, ModelConfig> modelConfigMap = new HashMap<>();
        Map<String, ModelBean> modelBeanMap = new ModelFactoryFromConfigurations(CONFIG_MAPS_DEV).createBeanMap();
        for (Map.Entry<String, ModelBean> entry: modelBeanMap.entrySet()) {
            modelConfigMap.put(entry.getKey(), createModelConfig(entry.getValue()));
        }
        return modelConfigMap;
    }

    public static final ModelConfigObject createModelConfig(ModelBean bean) {
        return new ModelConfigObject(bean, CONFIG_MAPS_DEV);
    }

    public static String createJson(final Object value, final String... keys) {
        if (keys.length == 0) {
            throw new EoException("keys length must at least 1.");
        }
        StringBuilder builder = new StringBuilder("{\"");
        builder.append(keys[keys.length-1]);
        builder.append("\":");
        if (value instanceof String) {
            builder.append("\"");
            builder.append(value);
            builder.append("\"");
        }
        else {
            builder.append(value);
        }
        builder.append("}");
        for (int i = keys.length-2; i>=0;i--) {
            StringBuilder wrapper = new StringBuilder("{\"");
            wrapper.append(keys[i]);
            wrapper.append("\":");
            wrapper.append(builder);
            wrapper.append("}");
            builder = wrapper;
        }
        return builder.toString();
    }

    public static EoRoot createRoot(final Object value, final String... keys) {
        EoRoot modelRoot = createEo(createJson(value, keys));
        Object toCompare = modelRoot.get(keys);
        assertEquals(value, toCompare);
        return modelRoot;
    }

    public static Map<String, Object> createMap(final Object value, final String... keys) {
        return (Map<String, Object>) createRoot(value, keys).get();
    }

    public static ModelBean createModelBeanWithFieldKey(final String fieldKey, final String key, final Object value) {
        return new ModelBean(createMap(value, F_FIELDS, fieldKey, key));
    }

    public static ModelConfig createModelConfigWithFieldKey(final String fieldKey, final String key, final Object value) {
        return new ModelConfigObject(createModelBeanWithFieldKey(fieldKey, key, value), CONFIG_MAPS_DEV);
    }

    public static ModelBean createModelBeanWithFieldProperty(final String fieldKey, final String key, final Object value) {
        return new ModelBean(createMap(value, F_FIELDS, fieldKey, F_PROPERTIES, key));
    }

    public static ModelConfig createModelConfigWithFieldProperty(final String fieldKey, final String key, final Object value) {
        return new ModelConfigObject(createModelBeanWithFieldProperty(fieldKey, key, value), CONFIG_MAPS_DEV);
    }

    public static ModelBean createModelBeanWithProperty(final String key, final Object value) {
        return new ModelBean(createMap(value, F_PROPERTIES, key));
    }

    public static ModelConfig createModelConfigWithProperty(final String key, final Object value) {
        return new ModelConfigObject(createModelBeanWithProperty(key, value), CONFIG_MAPS_DEV);
    }

    public static ModelBean createModelBean(final String key, final Object value) {
        return new ModelBean(createMap(value, key));
    }

    public static ModelConfig createModelConfig(final String key, final Object value) {
        return new ModelConfigObject(createModelBean(key, value), CONFIG_MAPS_DEV);
    }

    public static FieldBean createFieldBean(final String key, final Object value) {
        return new FieldBean(createMap(value, key));
    }

    public static FieldBean createFieldBeanProperty(final String key, final Object value) {
        return new FieldBean(createMap(value, F_PROPERTIES, key));
    }

    public static FieldConfig createFieldConfig(final String key, final Object value) {
        return new FieldConfigObject(createFieldBean(key, value), CONFIG_MAPS_DEV);
    }

    public static FieldConfig createFieldConfigProperty(final String key, final Object value) {
        return new FieldConfigObject(createFieldBeanProperty(key, value), CONFIG_MAPS_DEV);
    }
}
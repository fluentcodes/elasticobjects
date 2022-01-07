package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatMap;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
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

    public static final Map createMapFromJson(String json) {
        EoRoot root = createRootFromJson(json, Map.class);
        return (Map)root.get();
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

    public static final FieldConfig readFieldConfig(String configKey) {
        return new FieldConfig(readFieldBean(configKey), CONFIG_MAPS_DEV);
    }

    public static final ModelConfig createModelConfigFromJson(String json) {
        return new ModelConfigObject(createModelBeanFromJson(json), CONFIG_MAPS_DEV);
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

    public static String createKeyValueJson(final String key, final Object value) {
        if (value instanceof String) {
            return "{\"" + key + "\":\"" + value + "\"}";
        }
        return "{\"" + key + "\":" + value + "}";
    }

    public static String createLevel1Json(final String key1, final String key, final Object value) {
        return "{\"" + key1 + "\":" + createKeyValueJson(key, value) + "}";
    }

    public static String createLevel2Json(final String key2, final String key1, final String key, final Object value) {
        return "{\"" + key2 + "\":" + createLevel1Json(key1, key, value) + "}";
    }

    public static String createLevel3Json(final String key3, final String key2, final String key1, final String key, final Object value) {
        return "{\"" + key3 + "\":" + createLevel2Json(key2, key1, key, value) + "}";
    }

    public static EoRoot assertCreateKeyValueRoot(final String key, final Object value) {
        EoRoot modelRoot = createEo(createKeyValueJson(key, value));
        Object toCompare = modelRoot.get(key);
        assertEquals(value, toCompare);
        return modelRoot;
    }

    public static EoRoot assertCreateLevel1Root(final String key1, final String key, final Object value) {
        EoRoot modelRoot = createEo(createLevel1Json(key1, key, value));
        Object toCompare = modelRoot.get(key1, key);
        assertEquals(value, toCompare);
        return modelRoot;
    }

    public static EoRoot assertCreateLevel2Root(final String key2, final String key1, final String key, final Object value) {
        EoRoot modelRoot = createEo(createLevel2Json(key2, key1, key, value));
        Object toCompare = modelRoot.get(key2, key1, key);
        assertEquals(value, toCompare);
        return modelRoot;
    }

    public static EoRoot assertCreateLevel3Root(final String key3, final String key2, final String key1, final String key, final Object value) {
        EoRoot modelRoot = createEo(createLevel3Json(key3, key2, key1, key, value));
        Object toCompare = modelRoot.get(key3, key2, key1, key);
        assertEquals(value, toCompare);
        return modelRoot;
    }

    public static Map assertCreateKeyValueMap(final String key, final Object value) {
        Map<String, Object> map = (Map<String, Object>) assertCreateKeyValueRoot(key, value).get();
        Object toCompare = map.get(key);
        assertEquals(value, toCompare);
        return map;
    }

    public static Map assertCreateLevel1Map(final String key1, final String key, final Object value) {
        Map<String, Object> map1 = (Map<String, Object>) assertCreateLevel1Root(key1, key, value).get();
        Map<String, Object> map = (Map<String, Object>)map1.get(key1);
        Object toCompare = map.get(key);
        assertEquals(value, toCompare);
        return map1;
    }

    public static Map assertCreateLevel2Map(final String key2, final String key1, final String key, final Object value) {
        Map<String, Object> map2 = (Map<String, Object>) assertCreateLevel2Root(key2, key1, key, value).get();
        Map<String, Object> map1 = (Map<String, Object>)map2.get(key2);
        Map<String, Object> map = (Map<String, Object>)map1.get(key1);
        Object toCompare = map.get(key);
        assertEquals(value, toCompare);
        return map2;
    }

    public static Map assertCreateLevel3Map(final String key3, final String key2, final String key1, final String key, final Object value) {
        Map<String, Object> map3 = (Map<String, Object>) assertCreateLevel3Root(key3, key2, key1, key, value).get();
        Map<String, Object> map2 = (Map<String, Object>)map3.get(key3);
        Map<String, Object> map1 = (Map<String, Object>)map2.get(key2);
        Map<String, Object> map = (Map<String, Object>)map1.get(key1);
        Object toCompare = map.get(key);
        assertEquals(value, toCompare);
        return map3;
    }

    public static ModelBean createModelBeanWithFieldKey(final String fieldKey, final String key, final Object value) {
        return new ModelBean(assertCreateLevel2Map(F_FIELDS, fieldKey, key, value));
    }

    public static ModelBean createModelBeanWithFieldProperty(final String fieldKey, final String key, final Object value) {
        return new ModelBean(assertCreateLevel3Map(F_FIELDS, fieldKey, F_PROPERTIES, key, value));
    }

    public static ModelConfig createModelConfigWithFieldProperty(final String fieldKey, final String key, final Object value) {
        return new ModelConfigObject(createModelBeanWithFieldProperty(fieldKey, key, value), CONFIG_MAPS_DEV);
    }

    public static ModelConfig createModelConfigWithFieldKey(final String fieldKey, final String key, final Object value) {
        return new ModelConfigObject(createModelBeanWithFieldKey(fieldKey, key, value), CONFIG_MAPS_DEV);
    }

    public static ModelBean createModelBeanWithProperty(final String key, final Object value) {
        return new ModelBean(assertCreateLevel1Map(F_PROPERTIES, key, value));
    }

    public static ModelConfig createModelConfigWithProperty(final String key, final Object value) {
        return new ModelConfigObject(createModelBeanWithProperty(key, value), CONFIG_MAPS_DEV);
    }

    public static ModelBean createModelBean(final String key, final Object value) {
        return new ModelBean(assertCreateKeyValueMap(key, value));
    }

    public static ModelConfig createModelConfig(final String key, final Object value) {
        return new ModelConfigObject(createModelBean(key, value), CONFIG_MAPS_DEV);
    }

    public static FieldBean createFieldBean(final String key, final Object value) {
        return new FieldBean(assertCreateKeyValueMap(key, value));
    }

    public static FieldBean createFieldBeanProperty(final String key, final Object value) {
        return new FieldBean(assertCreateLevel1Map(F_PROPERTIES, key, value));
    }

    public static FieldConfig createFieldConfig(final String key, final Object value) {
        return new FieldConfig(createFieldBean(key, value), CONFIG_MAPS_DEV);
    }

    public static FieldConfig createFieldConfigProperty(final String key, final Object value) {
        return new FieldConfig(createFieldBeanProperty(key, value), CONFIG_MAPS_DEV);
    }
}
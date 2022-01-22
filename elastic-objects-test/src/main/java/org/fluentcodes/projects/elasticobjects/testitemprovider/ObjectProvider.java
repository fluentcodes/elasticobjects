package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_FIELDS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createJson;
import static org.junit.Assert.assertEquals;

public class ObjectProvider {
    private ObjectProvider() {

    }

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

    public static final ModelConfig findModel(final Class<?> eoClass) {
        return CONFIG_MAPS.findModel(eoClass);
    }

    public static final ModelConfig findModel(final String modelName) {
        return CONFIG_MAPS.findModel(modelName);
    }

    public static final ModelBean createModelBean(final Class<?> eoClass) {
        ModelConfig config = CONFIG_MAPS.findModel(eoClass);
        return new ModelBean(config);
    }

    public static final Models createModels(Class... classes) {
        return new Models(CONFIG_MAPS, classes);
    }

    static EoRoot createModelBeanRoot(final String json) {
        EoRoot root = EoRoot.ofClass(CONFIG_MAPS, ModelBean.class);
        root.map(json);
        return root;
    }

    public static ModelBean createModelBeanWithFieldKey(final String fieldKey, final String key, final Object value) {
        EoRoot root = createModelBeanRoot(ObjectProviderDev.createJson(value, F_FIELDS, fieldKey, key));
        assertEquals(value, root.get(F_FIELDS, fieldKey, key));
        return (ModelBean) root.get();
    }

    public static ModelBean createModelBeanWithFieldProperty(final String fieldKey, final String key, final Object value) {
        EoRoot root = createModelBeanRoot(ObjectProviderDev.createJson(value, F_FIELDS, fieldKey, F_PROPERTIES, key));
        assertEquals(value, root.get(F_FIELDS, fieldKey, F_PROPERTIES, key));
        return (ModelBean) root.get();
    }

    public static ModelBean createModelBeanWithProperty(final String key, final Object value) {
        EoRoot root = createModelBeanRoot(ObjectProviderDev.createJson(value, F_PROPERTIES, key));
        assertEquals(value, root.get(F_PROPERTIES, key));
        return (ModelBean) root.get();
    }

    public static ModelBean createModelBean(final String key, final Object value) {
        EoRoot root = createModelBeanRoot(ObjectProviderDev.createJson(value, key));
        return (ModelBean) root.get();
    }

    public static String toStringWithMap(Object value) {
        EoRoot root = EoRoot.ofClass(CONFIG_MAPS, Map.class);
        root.map(value);
        return new EOToJSON(JSONSerializationType.STANDARD).setSpacer("").toJson(root);
    }

    public static EoRoot createRoot(final Class<?> rootClass, final Object value, final String... keys) {
        EoRoot root = createEoWithClasses(rootClass);
        root.map(createJson(value, keys));
        return root;
    }

    public static Object createObject(final Class<?> rootClass, final Object value, final String... keys) {
        return createRoot(rootClass, value, keys).get();
    }

    public static EOInterfaceScalar createAnObjectEo(final String... childPath) {
        EoRoot eo = createEo();
        EOInterfaceScalar child = eo.set(new AnObject(), childPath);
        return child;
    }

    public static EOInterfaceScalar createAnObjectEo(final String key, final Object value) {
        EOInterfaceScalar child = createAnObjectEo("test");
        child.set(value, key);
        return child;
    }
}
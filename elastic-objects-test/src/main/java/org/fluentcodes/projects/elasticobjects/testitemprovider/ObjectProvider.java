package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Date;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_FIELDS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createKeyValueJson;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createLevel1Json;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createLevel2Json;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createLevel3Json;
import static org.junit.Assert.assertEquals;

public class ObjectProvider {
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

    public static final Models createModels(Class... classes) {
        return new Models(CONFIG_MAPS, classes);
    }

    public static Object createBean(final Class<?> mappingClass, final String fieldName, final Object value) {
        EoRoot root = createEoWithClasses(mappingClass);
        root.map(ObjectProviderDev.createKeyValueJson(fieldName, value));
        return root.get();
    }

    static EoRoot createModelBeanRoot(final String json) {
        EoRoot root = EoRoot.ofClass(CONFIG_MAPS, ModelBean.class);
        root.map(json);
        return root;
    }

    public static ModelBean createModelBeanWithFieldKey(final String fieldKey, final String key, final Object value) {
        EoRoot root = createModelBeanRoot(createLevel2Json(F_FIELDS, fieldKey, key, value));
        assertEquals(value, root.get(F_FIELDS, fieldKey, key));
        return (ModelBean)root.get();
    }

    public static ModelBean createModelBeanWithFieldProperty(final String fieldKey, final String key, final Object value) {
        EoRoot root = createModelBeanRoot(createLevel3Json(F_FIELDS, fieldKey, F_PROPERTIES, key, value));
        assertEquals(value, root.get(F_FIELDS, fieldKey, F_PROPERTIES, key));
        return (ModelBean)root.get();
    }

    public static ModelBean createModelBeanWithProperty(final String key, final Object value) {
        EoRoot root = createModelBeanRoot(createLevel1Json(F_PROPERTIES, key, value));
        assertEquals(value, root.get(F_PROPERTIES, key));
        return (ModelBean)root.get();
    }

    public static ModelBean createModelBean(final String key, final Object value) {
        EoRoot root = createModelBeanRoot(createKeyValueJson( key, value));
        if (root.get(key) instanceof Enum) {
            assertEquals(value, root.get(key).toString());
        }
        else if (key.equals(F_SCOPE)) {
        }
        else if (root.get(key) instanceof Date && value instanceof Long) {
            assertEquals(new Date((Long)value), root.get(key));
        }
        else {
            assertEquals(value, root.get(key));
        }
        return (ModelBean)root.get();
    }

    public static String toStringWithMap(Object value) {
        EoRoot root = EoRoot.ofClass(CONFIG_MAPS, Map.class);
        root.map(value);
        return new EOToJSON(JSONSerializationType.STANDARD).setSpacer("").toJson(root);
    }

}
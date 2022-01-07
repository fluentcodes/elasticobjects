package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ABSTRACT;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_CREATE;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_DB_ANNOTATED;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_DEFAULT_IMPLEMENTATION;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ID_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_NATURAL_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_TABLE;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanPropertiesDevTest.createModelBeanProperties;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.CONFIG_MAPS_DEV;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ModelConfigPropertiesDevTest {
    static ModelConfigProperties createModelConfigProperties(final String key, final Object value) {
        return new ModelConfigProperties(createModelBeanProperties(key, value));
    }

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(ModelConfigProperties.class.getSimpleName());
        assertNull(bean.getSuperKey());
        assertEquals(8, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(ModelConfigProperties.class.getSimpleName());
        assertTrue(config.getProperties().getCreate());
        assertEquals(8, config.getFields().size());
    }

    @Test
    public void createModelConfigProperties_abstract_with_serialization() {
        ModelConfigProperties properties = createModelConfigProperties(F_ABSTRACT, true);
        assertEquals(true, properties.getAbstract());

    }

    @Test
    public void createModelConfigProperties_create() {
        ModelConfigProperties properties = createModelConfigProperties(F_CREATE, true);
        assertEquals(true, properties.getCreate());
    }

    @Test
    public void createModelConfigProperties_dbAnnotated() {
        ModelConfigProperties properties = createModelConfigProperties(F_DB_ANNOTATED, true);
        assertEquals(true, properties.getDbAnnotated());
    }

    @Test
    public void createModelConfigProperties_defaultImplementation() {
        ModelConfigProperties properties = createModelConfigProperties(F_DEFAULT_IMPLEMENTATION, "TreeMap");
        assertEquals("TreeMap", properties.getDefaultImplementation());
    }

    @Test
    public void createModelConfigProperties_idKey() {
        ModelConfigProperties properties = createModelConfigProperties(F_ID_KEY, "id");
        assertEquals("id", properties.getIdKey());
    }

    @Test
    public void createModelConfigProperties_naturalKeys() {
        ModelConfigProperties properties = createModelConfigProperties(F_NATURAL_KEYS, "id");
        assertEquals("id", properties.getNaturalKeys());
    }

    @Test
    public void createModelConfigProperties_table() {
        ModelConfigProperties properties = createModelConfigProperties(F_TABLE, "table");
        assertEquals("table", properties.getTable());
    }
}

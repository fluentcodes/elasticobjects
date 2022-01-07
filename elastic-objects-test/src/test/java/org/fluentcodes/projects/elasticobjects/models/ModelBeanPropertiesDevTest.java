package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ABSTRACT;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_CREATE;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_DB_ANNOTATED;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_DEFAULT_IMPLEMENTATION;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ID_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_NATURAL_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_TABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ModelBeanPropertiesDevTest {

    static ModelBeanProperties createModelBeanProperties(final String key, final Object value) {
        EoRoot root = ObjectProviderDev.assertCreateKeyValueRoot(key, value);
        return new ModelBeanProperties((Map) root.get());
    }

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(ModelBeanProperties.class.getSimpleName());
        assertNull(bean.getSuperKey());
        assertEquals(8, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(ModelBeanProperties.class.getSimpleName());
        assertTrue(config.getProperties().getCreate());
        assertEquals(8, config.getFields().size());
    }

    @Test
    public void createModelBeanProperties_abstract() {
        ModelBeanProperties properties = createModelBeanProperties(F_ABSTRACT, true);
        assertEquals(true, properties.getAbstract());
    }

    @Test
    public void createModelBeanProperties_create() {
        ModelBeanProperties properties = createModelBeanProperties(F_CREATE, true);
        assertEquals(true, properties.getCreate());
    }

    @Test
    public void createModelBeanProperties_dbAnnotated() {
        ModelBeanProperties properties = createModelBeanProperties(F_DB_ANNOTATED, true);
        assertEquals(true, properties.getDbAnnotated());
    }

    @Test
    public void createModelBeanProperties_defaultImplementation() {
        ModelBeanProperties properties = createModelBeanProperties(F_DEFAULT_IMPLEMENTATION, "TreeMap");
        assertEquals("TreeMap", properties.getDefaultImplementation());
    }

    @Test
    public void createModelBeanProperties_idKey() {
        ModelBeanProperties properties = createModelBeanProperties(F_ID_KEY, "id");
        assertEquals("id", properties.getIdKey());
    }

    @Test
    public void createModelBeanProperties_naturalKeys() {
        ModelBeanProperties properties = createModelBeanProperties(F_NATURAL_KEYS, "id");
        assertEquals("id", properties.getNaturalKeys());
    }

    @Test
    public void createModelBeanProperties_table() {
        ModelBeanProperties properties = createModelBeanProperties(F_TABLE, "table");
        assertEquals("table", properties.getTable());
    }
}

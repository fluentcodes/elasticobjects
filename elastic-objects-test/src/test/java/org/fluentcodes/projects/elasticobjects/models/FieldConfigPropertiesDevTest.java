package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.*;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanPropertiesDevTest.createFieldBeanProperties;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.toStringWithMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FieldConfigPropertiesDevTest {

    static FieldConfigProperties createFieldConfigProperties(final String key, final Object value) {
        ConfigMaps configMaps = CONFIG_MAPS;
        return new FieldConfigProperties(createFieldBeanProperties(key, value));
    }

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(FieldConfigProperties.class.getSimpleName());
        assertNull(bean.getSuperKey());
        assertEquals(16, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(FieldConfigProperties.class.getSimpleName());
        assertTrue(config.getProperties().getCreate());
        assertEquals(16, config.getFields().size());
    }

    @Test
    public void createFieldConfigProperties_default() {
        FieldConfigProperties properties = createFieldConfigProperties(F_DEFAULT, true);
        assertEquals(true, properties.getDefault());
        assertEquals("{\"default\": true}", toStringWithMap(properties));
    }

    @Test
    public void createFieldConfigProperties_fieldName() {
        FieldConfigProperties properties = createFieldConfigProperties(F_FIELD_NAME, "name");
        assertEquals("name", properties.getFieldName());
    }

    @Test
    public void createFieldConfigProperties_final() {
        FieldConfigProperties properties = createFieldConfigProperties(F_FINAL, true);
        assertEquals(true, properties.getFinal());
    }

    @Test
    public void createFieldConfigProperties_generated() {
        FieldConfigProperties properties = createFieldConfigProperties(F_GENERATED, true);
        assertEquals(true, properties.getGenerated());
    }

    @Test
    public void createFieldConfigProperties_jsonIgnore() {
        FieldConfigProperties properties = createFieldConfigProperties(F_JSON_IGNORE, true);
        assertEquals(true, properties.getJsonIgnore());
    }

    @Test
    public void createFieldConfigProperties_length() {
        FieldConfigProperties properties = createFieldConfigProperties(F_LENGTH, 1);
        assertEquals(Integer.valueOf(1), properties.getLength());
    }

    @Test
    public void createFieldConfigProperties_max() {
        FieldConfigProperties properties = createFieldConfigProperties(F_MAX, 1);
        assertEquals(Integer.valueOf(1), properties.getMax());
    }

    @Test
    public void createFieldConfigProperties_min() {
        FieldConfigProperties properties = createFieldConfigProperties(F_MIN, 1);
        assertEquals(Integer.valueOf(1), properties.getMin());
    }

    @Test
    public void createFieldConfigProperties_notNull() {
        FieldConfigProperties properties = createFieldConfigProperties(F_NOT_NULL, true);
        assertEquals(true, properties.getNotNull());
    }

    @Test
    public void createFieldConfigProperties_override() {
        FieldConfigProperties properties = createFieldConfigProperties(F_OVERRIDE, true);
        assertEquals(true, properties.getOverride());
    }

    @Test
    public void createFieldConfigProperties_property() {
        FieldConfigProperties properties = createFieldConfigProperties(F_PROPERTY, true);
        assertEquals(true, properties.getProperty());
    }

    @Test
    public void createFieldConfigProperties_staticName() {
        FieldConfigProperties properties = createFieldConfigProperties(F_STATIC_NAME, true);
        assertEquals(true, properties.getStaticName());
    }

    @Test
    public void createFieldConfigProperties_super() {
        FieldConfigProperties properties = createFieldConfigProperties(F_SUPER, true);
        assertEquals(true, properties.getSuper());
    }

    @Test
    public void createFieldConfigProperties_transient() {
        FieldConfigProperties properties = createFieldConfigProperties(F_TRANSIENT, true);
        assertEquals(true, properties.getTransient());
    }

    @Test
    public void createFieldConfigProperties_unique() {
        FieldConfigProperties properties = createFieldConfigProperties(F_UNIQUE, true);
        assertEquals(true, properties.getUnique());
    }
}

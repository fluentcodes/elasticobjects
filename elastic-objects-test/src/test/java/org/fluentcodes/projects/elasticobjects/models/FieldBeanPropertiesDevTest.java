package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FieldBeanPropertiesDevTest {

    static FieldBeanProperties createFieldBeanProperties(final String key, final Object value) {
        EoRoot root = ObjectProviderDev.createRoot(value, key);
        return new FieldBeanProperties((Map)root.get());
    }

    static FieldConfigProperties createFieldConfigProperties(final String key, final Object value) {
        return new FieldConfigProperties(createFieldBeanProperties(key, value));
    }

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(FieldBeanProperties.class.getSimpleName());
        assertNull(bean.getSuperKey());
        assertEquals(16, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(FieldBeanProperties.class.getSimpleName());
        assertTrue(config.getProperties().getCreate());
        assertEquals(16, config.getFields().size());
    }

    @Test
    public void createFieldBeanProperties_default() {
        FieldBeanProperties properties = createFieldBeanProperties(F_DEFAULT, true);
        assertEquals(true, properties.getDefault());
    }

    @Test
    public void createFieldBeanProperties_fieldName() {
        FieldBeanProperties properties = createFieldBeanProperties(F_FIELD_NAME, "name");
        assertEquals("name", properties.getFieldName());
    }

    @Test
    public void createFieldBeanProperties_final() {
        FieldBeanProperties properties = createFieldBeanProperties(F_FINAL, true);
        assertEquals(true, properties.getFinal());
    }

    @Test
    public void createFieldBeanProperties_generated() {
        FieldBeanProperties properties = createFieldBeanProperties(F_GENERATED, true);
        assertEquals(true, properties.getGenerated());
    }

    @Test
    public void createFieldBeanProperties_jsonIgnore() {
        FieldBeanProperties properties = createFieldBeanProperties(F_JSON_IGNORE, true);
        assertEquals(true, properties.getJsonIgnore());
    }

    @Test
    public void createFieldBeanProperties_length() {
        FieldBeanProperties properties = createFieldBeanProperties(F_LENGTH, 1);
        assertEquals(Integer.valueOf(1), properties.getLength());
    }

    @Test
    public void createFieldBeanProperties_max() {
        FieldBeanProperties properties = createFieldBeanProperties(F_MAX, 1);
        assertEquals(Integer.valueOf(1), properties.getMax());
    }

    @Test
    public void createFieldBeanProperties_min() {
        FieldBeanProperties properties = createFieldBeanProperties(F_MIN, 1);
        assertEquals(Integer.valueOf(1), properties.getMin());
    }

    @Test
    public void createFieldBeanProperties_notNull() {
        FieldBeanProperties properties = createFieldBeanProperties(F_NOT_NULL, true);
        assertEquals(true, properties.getNotNull());
    }

    @Test
    public void createFieldBeanProperties_override() {
        FieldBeanProperties properties = createFieldBeanProperties(F_OVERRIDE, true);
        assertEquals(true, properties.getOverride());
    }

    @Test
    public void createFieldBeanProperties_property() {
        FieldBeanProperties properties = createFieldBeanProperties(F_PROPERTY, true);
        assertEquals(true, properties.getProperty());
    }

    @Test
    public void createFieldBeanProperties_staticName() {
        FieldBeanProperties properties = createFieldBeanProperties(F_STATIC_NAME, true);
        assertEquals(true, properties.getStaticName());
    }

    @Test
    public void createFieldBeanProperties_super() {
        FieldBeanProperties properties = createFieldBeanProperties(F_SUPER, true);
        assertEquals(true, properties.getSuper());
    }

    @Test
    public void createFieldBeanProperties_transient() {
        FieldBeanProperties properties = createFieldBeanProperties(F_TRANSIENT, true);
        assertEquals(true, properties.getTransient());
    }

    @Test
    public void createFieldBeanProperties_unique() {
        FieldBeanProperties properties = createFieldBeanProperties(F_UNIQUE, true);
        assertEquals(true, properties.getUnique());
    }
}

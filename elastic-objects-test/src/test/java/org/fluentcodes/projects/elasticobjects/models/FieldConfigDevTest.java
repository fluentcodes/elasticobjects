package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_DEFAULT;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_FIELD_NAME;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_MODEL_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ID_KEY;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.toStringWithMap;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev.readFieldConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Werner on 17.11.2021.
 */
public class FieldConfigDevTest {
    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(FieldConfig.class.getSimpleName());
        assertEquals("Config", bean.getSuperKey());
        assertEquals(3, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(FieldConfig.class.getSimpleName());
        assertEquals(true, config.getProperties().getCreate());
        assertEquals(3, config.getFields().size());
    }

    @Test
    public void readFieldConfig_Default(){
        FieldConfig fieldConfig = readFieldConfig(FieldBeanProperties.F_DEFAULT);
        assertEquals(F_DEFAULT,  fieldConfig.getFieldKey());
    }

    @Test
    public void readFieldConfig_FieldName(){
        FieldConfig fieldConfig = readFieldConfig(FieldBeanProperties.F_FIELD_NAME);
        assertEquals(F_FIELD_NAME, fieldConfig.getFieldKey());
    }

    @Test
    public void readFieldConfig_final(){
        FieldConfig fieldConfig = readFieldConfig(FieldBeanProperties.F_FINAL);
        assertEquals("final", fieldConfig.getFieldKey());
    }

    @Test
    public void createFieldConfig_fieldKey() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_FIELD_KEY, "NAME");
        assertEquals("NAME", config.getFieldKey());
    }

    @Test
    public void createFieldConfig_modelKeys() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_MODEL_KEYS, "Map,String");
        assertEquals("Map,String", config.getModelKeys());
    }

    @Test
    public void createFieldConfigProperty_default(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_DEFAULT, true);
        assertTrue( config.getProperties().getDefault());
        assertEquals("{\"properties\": {\"default\": true}}", toStringWithMap(config));
    }

    @Test
    public void createFieldConfigProperty_fieldName(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_FIELD_NAME, "fieldName");
        assertEquals("fieldName", config.getProperties().getFieldName());
        assertEquals("{\"properties\": {\"fieldName\": \"fieldName\"}}", toStringWithMap(config));
    }

    @Test
    public void createFieldConfigProperty_final(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_FINAL, true);
        assertTrue(config.getProperties().getFinal());
    }

    @Test
    public void createFieldConfigProperty_generated(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_GENERATED, true);
        assertTrue(config.getProperties().getGenerated());
    }

    @Test
    public void createFieldConfigProperty_idKey(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(F_ID_KEY, "test");
        assertEquals("test", config.getProperties().getIdKey());
    }

    @Test
    public void createFieldConfigProperty_javascriptType(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_JAVASCRIPT_TYPE, "string");
        assertEquals("string", config.getProperties().getJavascriptType());
    }

    @Test
    public void createFieldConfigProperty_jsonIgnore(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_JSON_IGNORE, true);
        assertTrue(config.getProperties().getJsonIgnore());
    }

    @Test
    public void createFieldConfigProperty_max(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_MAX, 1);
        assertEquals(new Integer(1), config.getProperties().getMax());
    }

    @Test
    public void createFieldConfigProperty_min(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_MIN, 1);
        assertEquals(new Integer(1), config.getProperties().getMin());
    }

    @Test
    public void createFieldConfigProperty_notNull(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_NOT_NULL, true);
        assertTrue(config.getProperties().getNotNull());
    }

    @Test
    public void createFieldConfigProperty_override(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_OVERRIDE, true);
        assertTrue(config.getProperties().getOverride());
    }

    @Test
    public void createFieldConfigProperty_property(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_PROPERTY, true);
        assertTrue(config.getProperties().getProperty());
    }

    @Test
    public void createFieldConfigProperty_staticName(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_STATIC_NAME, true);
        assertTrue(config.getProperties().getStaticName());
    }


    @Test
    public void createFieldConfigProperty_super(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_SUPER, true);
        assertTrue(config.getProperties().getSuper());
    }

    @Test
    public void createFieldConfigProperty_transient(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_TRANSIENT, true);
        assertTrue(config.getProperties().getTransient());
    }

    @Test
    public void createFieldConfigProperty_unique(){
        FieldConfig config = ObjectProviderDev.createFieldConfigProperty(FieldBeanProperties.F_UNIQUE, true);
        assertTrue(config.getProperties().getUnique());
    }

}

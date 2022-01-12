package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_DESCRIPTION;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_MODEL_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ABSTRACT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FieldBeanDevTest {
    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(FieldBean.class.getSimpleName());
        assertEquals("ConfigBean", bean.getSuperKey());
        assertEquals(3, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(FieldBean.class.getSimpleName());
        assertEquals(true, config.getProperties().getCreate());
        assertEquals(3, config.getFields().size());
    }

    @Test
    public void new__toString() {
        FieldBean fieldBean = new FieldBean();
        assertEquals("", fieldBean.toString());
    }

    @Test
    public void new_naturalId() {
        FieldBean fieldBean = new FieldBean("test");
        Assertions.assertThat(fieldBean.toString()).hasToString("test");
    }

    @Test
    public void new_naturalId_modelKeys__toString__String_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setModelKeys("String");
        Assertions.assertThat(fieldBean.toString()).hasToString("(String)field");
    }

    @Test
    public void new_naturalId_modelKeys_parentModel__toString__String_Model_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setParentModel(new ModelBean("Model"));
        fieldBean.setModelKeys("String");
        Assertions.assertThat(fieldBean.toString()).hasToString("(String)Model.field");
    }

    @Test
    public void new_naturalId_parent__toString__Model_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setParentModel(new ModelBean("Model"));
        Assertions.assertThat(fieldBean.toString()).hasToString("Model.field");
    }

    @Test
    public void new_fieldKey__toString__Model_field() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setFieldKey("field");
        Assertions.assertThat(fieldBean.toString()).hasToString("field");
    }

    @Test
    public void createFieldBean_FieldKey() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_FIELD_KEY, "NAME");
        assertEquals("NAME", fieldBean.getFieldKey());
    }

    @Test
    public void createFieldBean_ModelKeys() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_MODEL_KEYS, "Map,String");
        assertEquals("Map,String", fieldBean.getModelKeys());
    }

    @Test
    public void createFieldBeanProperty() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBeanProperty(F_FINAL, true);
        assertTrue(fieldBean.getProperties().getFinal());
    }

    @Test
    public void createAbstract() {
        final String serialized = "{\n" +
                "  \"abstract\": {\n" +
                "    \"module\": \"elastic-objects\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"modelKeys\": \"Boolean\",\n" +
                "    \"description\": \"If true the generated model will be initially set as abstract class.\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"fieldKey\": \"abstract\",\n" +
                "    \"properties\": {\n" +
                "      \"property\": true,\n" +
                "      \"default\": true\n" +
                "    }\n" +
                "  }" +
                "}";
        EoRoot root = ObjectProviderDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>) root.get();
        Map<String, Object> fieldConfigMap = (Map<String, Object>) beanMap.get(F_ABSTRACT);
        FieldBean bean = new FieldBean(fieldConfigMap);
        assertNotNull(bean);
        assertEquals(F_ABSTRACT, bean.getFieldKey());
        assertEquals(F_ABSTRACT, bean.getNaturalId());
        bean.setDefault();
        FieldConfig fieldConfig = (FieldConfig) bean.createConfig(ObjectProviderDev.CONFIG_MAPS_DEV);
        assertEquals(F_ABSTRACT, fieldConfig.getFieldKey());
        assertEquals(F_ABSTRACT, fieldConfig.getNaturalId());
    }

    @Test
    public void createDescription() {
        final String serialized = "{  \"description\": {\n" +
                "    \"module\": \"elastic-objects\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"modelKeys\": \"String\",\n" +
                "    \"description\": \"A description of the model used by every model extending BaseClassImpl. \",\n" +
                "    \"expose\": \"WEB\",\n" +
                "    \"fieldKey\": \"description\",\n" +
                "    \"length\": 512\n" +
                "  }}";
        EoRoot root = ObjectProviderDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>) root.get();
        Map<String, Object> fieldConfigMap = (Map<String, Object>) beanMap.get(F_DESCRIPTION);
        FieldBean bean = new FieldBean(fieldConfigMap);
        assertNotNull(bean);
        bean.setDefault();
        assertEquals(F_DESCRIPTION, bean.getFieldKey());
        assertEquals(F_DESCRIPTION, bean.getNaturalId());
        XpectEoJunit4.assertStatic(bean);
    }
}

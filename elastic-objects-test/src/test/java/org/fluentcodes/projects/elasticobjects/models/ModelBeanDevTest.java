package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CONFIG_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_OVERRIDE;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_FIELDS;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ABSTRACT;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_SUPER_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.SHAPE_TYPE;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.assertCreateLevel2Map;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.assertCreateLevel2Root;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createLevel2Json;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelBean;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelBeanWithFieldKey;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelBeanWithFieldProperty;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelBeanWithProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ModelBeanDevTest {
    public static final String JSON = "{\n" +
            "    \"module\": \"elastic-objects\",\n" +
            "    \"moduleScope\": \"main\",\n" +
            "    \"fields\": {\n" +
            "      \"ModelBean.fields\": {\n" +
            "      },\n" +
            "      \"interfaces\": {\n" +
            "        \"override\": true\n" +
            "      },\n" +
            "      \"modelKey\": {\n" +
            "        \"override\": true\n" +
            "      },\n" +
            "      \"ModelBean.properties\": {},\n" +
            "      \"packagePath\": {\n" +
            "        \"override\": true\n" +
            "      },\n" +
            "      \"shapeType\": {\n" +
            "        \"jsonSerialized\": false,\n" +
            "        \"transient\": true,\n" +
            "        \"default\": true\n" +
            "      },\n" +
            "      \"superKey\": {\n" +
            "        \"override\": true\n" +
            "      }\n" +
            "    },\n" +
            "    \"interfaces\": \"ModelInterface\",\n" +
            "    \"superKey\": \"ConfigBean\",\n" +
            "    \"expose\": \"WEB\",\n" +
            "    \"description\": \"A bean container class for Model values\",\n" +
            "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.models\",\n" +
            "    \"modelKey\": \"ModelBean\",\n" +
            "    \"properties\": {\n" +
            "      \"create\": true\n" +
            "    },\n" +
            "    \"author\": \"Werner Diwischek\"\n" +
            "  }";

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(ModelBean.class.getSimpleName());
        assertEquals("ConfigBean", bean.getSuperKey());
        assertEquals(7, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(ModelBean.class.getSimpleName());
        assertEquals(true, config.getProperties().getCreate());
        assertEquals(7, config.getFields().size());
    }

    @Test
    public void createRootFromJson() {
        EoRoot root = ObjectProviderDev.createRootFromJson(JSON, Map.class);
        assertEquals(true, root.get(F_FIELDS, F_INTERFACES, F_OVERRIDE));
    }

    @Test
    public void createMapFromJson() {
        Map map = ObjectProviderDev.createMapFromJson(JSON);
        assertEquals("org.fluentcodes.projects.elasticobjects.models", map.get(PACKAGE_PATH));
    }

    @Test
    public void createModelBeanFromJson() {
        ModelBean bean = ObjectProviderDev.createModelBeanFromJson(JSON);
        assertEquals("org.fluentcodes.projects.elasticobjects.models", bean.getPackagePath());
    }

    @Test
    public void createModelConfigFromJson() {
        ModelConfig config = ObjectProviderDev.createModelConfigFromJson(JSON);
        assertEquals("org.fluentcodes.projects.elasticobjects.models", config.getPackagePath());
        assertEquals(7, config.getFields().size());
    }

    @Test
    public void newEmpty__toString__BEAN() {
        ModelBean model = new ModelBean();
        Assertions.assertThat(model.toString()).isEqualTo("(null)null");
    }

    @Test
    public void newModel__toString__BEAN_Model() {
        ModelBean model = new ModelBean("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(null)Model");
    }

    @Test
    public void newEmpty_modelKey_Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean();
        model.setModelKey("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(null)null");
    }

    @Test
    public void newEmpty_naturalId_Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean();
        model.setNaturalId("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(null)Model");
    }

    @Test
    public void newEmpy_set_ShapeTypes_LIST__getShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void new_set_ShapeTypes_LIST__properties_get_ShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void new_empty__setFinal_true__isFinal_true() {
        ModelBean modelBean = new ModelBean();
        modelBean.getProperties().setFinal(true);
        Assertions.assertThat(modelBean.getProperties().isFinal()).isTrue();
    }

    @Test
    public void new_empty__addField_test__getFieldBean_not_null() {
        ModelBean modelBean = new ModelBean();
        modelBean.addField("test");
        Assertions.assertThat(modelBean.getField("test")).isNotNull();
    }

    @Test
    public void new_ArrayListList__isList__true() {
        ModelBean modelBean = new ModelBean(ArrayList.class, ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void createModelBean_modelKey() {
        ModelBean modelBean = createModelBean(F_MODEL_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces", modelBean.getModelKey());
    }

    @Test
    public void createModelBeanJson_miscValues() {
        String value = createLevel2Json(F_FIELDS, "fieldKey", "key", "value");
        assertEquals("{\"fields\":{\"fieldKey\":{\"key\":\"value\"}}}", value);
    }

    @Test
    public void assertCreateModelBeanRoot_miscValues() {
        assertCreateLevel2Root(F_FIELDS, "fieldKey", "key", "value");
    }

    @Test
    public void assertCreateModelBeanMap_miscValues() {
        assertCreateLevel2Map(F_FIELDS, "fieldKey", "key", "value");
    }

    @Test
    public void createModelBeanWithFieldKey_module() {
        ModelBean bean = createModelBeanWithFieldKey("fieldKey", F_MODULE, "moduleValue");
        assertNotNull(bean.getFields());
        assertNotNull(bean.getField("fieldKey"));
        assertEquals("moduleValue", bean.getField("fieldKey").getModule());
    }

    @Test
    public void createModelBeanWithFieldProperty_module() {
        ModelBean bean = createModelBeanWithFieldProperty("fieldKey", F_FINAL, true);
        assertNotNull(bean.getFields());
        assertNotNull(bean.getField("fieldKey"));
        assertTrue(bean.getField("fieldKey").getProperties().getFinal());
    }

    @Test
    public void createModelBeanWithProperty_abstract() {
        ModelBean bean = createModelBeanWithProperty(F_ABSTRACT, true);
        assertNotNull(bean.getProperties());
        assertTrue(bean.getProperties().getAbstract());
    }

    @Test
    public void createModelKey() {
        ModelBean modelBean = createModelBean(F_MODEL_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces", modelBean.getModelKey());
    }

    @Test
    public void testShapeType() {
        ModelBean modelBean = createModelBean(SHAPE_TYPE, "STRING");
        assertEquals(ShapeTypes.STRING, modelBean.getShapeType());
    }

    @Test
    public void testSuperKey() {
        ModelBean modelBean = createModelBean(F_SUPER_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces", modelBean.getSuperKey());
    }

    @Test
    public void testPackagePath() {
        ModelBean modelBean = createModelBean(PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        assertEquals("org.fluentcodes.projects.elasticobjects", modelBean.getPackagePath());
    }

    @Test
    public void testConfigModelKey() {
        ModelBean modelBean = createModelBean(F_CONFIG_MODEL_KEY, "CONFIG");
        assertEquals("CONFIG", modelBean.getConfigModelKey());
    }

    @Test
    public void testExpose() {
        ModelBean modelBean = createModelBean(F_EXPOSE, "WEB");
        assertEquals(Expose.WEB, modelBean.getExpose());
    }

    @Test
    public void testModuleScope() {
        ModelBean modelBean = createModelBean(F_MODULE_SCOPE, "MODULE_SCOPE");
        assertEquals("MODULE_SCOPE", modelBean.getModuleScope());
    }

    @Test
    public void testModule() {
        ModelBean modelBean = createModelBean(F_MODULE, "MODULE");
        assertEquals("MODULE", modelBean.getModule());
    }

    @Test
    public void testScope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        ModelBean modelBean = createModelBean(F_SCOPE, "DEV,PROD");
        assertEquals(result, modelBean.getScope());
    }

    @Test
    public void testAuthor() {
        ModelBean modelBean = createModelBean(F_AUTHOR, "Author");
        assertEquals("Author", modelBean.getAuthor());
    }

    @Test
    public void testCreationDate() {
        ModelBean modelBean = createModelBean(F_CREATION_DATE, 1);
        assertEquals(new Date(1L), modelBean.getCreationDate());
    }

    @Test
    public void testId() {
        ModelBean modelBean = createModelBean(F_ID, 1);
        assertEquals(new Long(1L), modelBean.getId());
    }

    @Test
    public void testNaturalId() {
        ModelBean modelBean = createModelBean(F_NATURAL_ID, "natural");
        assertEquals("natural", modelBean.getNaturalId());
    }
}

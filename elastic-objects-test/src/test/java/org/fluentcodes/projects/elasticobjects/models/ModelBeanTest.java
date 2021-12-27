package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_ID;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_CONFIG_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FIELD_NAME;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.SHAPE_TYPE;
import static org.junit.Assert.assertEquals;

public class ModelBeanTest implements IModelConfigCreateTests {
    public static void assertModel(final String fieldName, final Object value, final Object expectedResult) {
        ModelBean bean = createModelBean(fieldName, value);
        Object propertyValue = bean.getProperties().get(fieldName);
        assertEquals("Problem for " + fieldName, expectedResult, propertyValue);
    }

    public static ModelBean createModelBean(final String fieldName, final Object value) {
        final String serialized = "{     \"" + F_FIELD_NAME + "\": {\n" +
                "        \"" + fieldName + "\": " + value + "\n" +
                "      }\n}}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>) root.get();
        Map<String, Object> propertyFieldMap = (Map<String, Object>) beanMap.get(F_FIELD_NAME);
        ModelBean modelBean = new ModelBean(propertyFieldMap);
        return modelBean;
    }

    @Override
    public Class<?> getModelConfigClass() {
        return ModelBean.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void empty__toString__BEAN() {
        ModelBean model = new ModelBean();
        Assertions.assertThat(model.toString()).isEqualTo("(null)");
    }

    @Test
    public void Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(null)Model");
    }

    @Test
    public void modelKey_Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean();
        model.setModelKey("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(null)Model");
    }

    @Test
    public void naturalId_Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean();
        model.setNaturalId("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(null)Model");
    }

    @Test
    public void set_ShapeTypes_LIST__getShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void set_ShapeTypes_LIST__properties_get_ShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void eo_set_ShapeTypes_LIST__get_ShapeType__LIST() {
        EoRoot eo = ProviderConfigMaps.createEo(new ModelBean());
        eo.set(ShapeTypes.LIST, SHAPE_TYPE);
        Assertions.assertThat(eo.get(SHAPE_TYPE)).isEqualTo(ShapeTypes.LIST);
    }

    @Ignore("Check for later")
    @Test
    public void empty__getJavascriptType__EoInternalExcection() {
        ModelBean modelBean = new ModelBean();
        Assertions.assertThatThrownBy(() -> {
            modelBean.getJavascriptType();
        })
                .isInstanceOf(EoInternalException.class);
    }

    @Ignore("Check for later")
    @Test
    public void empty__modelKey_String__getJavascriptType__string() {
        ModelBean modelBean = new ModelBean();
        modelBean.setModelKey(String.class.getSimpleName());
        Assertions.assertThat(modelBean.getJavascriptType())
                .isEqualTo("string");
    }


    @Ignore("Check for later")
    @Test
    public void empty__modelKey_Float__getJavascriptType__number() {
        ModelBean modelBean = new ModelBean();
        modelBean.setModelKey(Float.class.getSimpleName());
        Assertions.assertThat(modelBean.getJavascriptType())
                .isEqualTo("number");
    }

    @Test
    public void empty__setFinal_true__isFinal_true() {
        ModelBean modelBean = new ModelBean();
        modelBean.setFinal(true);
        Assertions.assertThat(modelBean.isFinal()).isTrue();
    }

    @Test
    public void empty__addField_test__getFieldBean_not_null() {
        ModelBean modelBean = new ModelBean();
        modelBean.addField("test");
        Assertions.assertThat(modelBean.getFieldBean("test")).isNotNull();
    }

    @Test
    public void ArrayListList__isList__true() {
        ModelBean modelBean = new ModelBean(ArrayList.class, ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void testExample() {
        assertModel(F_FINAL, true, true);
    }

    @Test
    public void testModelKey() {
        ModelBean modelBean = createModelBean(MODEL_KEY, "\"FieldBeanInterfaces\"");
        assertEquals("FieldBeanInterfaces", modelBean.getModelKey());
    }

    @Test
    public void testShapeType() {
        ModelBean modelBean = createModelBean(SHAPE_TYPE, "\"STRING\"");
        assertEquals(ShapeTypes.STRING, modelBean.getShapeType());
    }

    @Test
    public void testSuperKey() {
        ModelBean modelBean = createModelBean(SUPER_KEY, "\"FieldBeanInterfaces\"");
        assertEquals("FieldBeanInterfaces", modelBean.getSuperKey());
    }

    @Test
    public void testSuperKeyNull() {
        ModelBean modelBean = createModelBean(SUPER_KEY, null);
        assertEquals(null, modelBean.getSuperKey());
    }

    @Test
    public void testPackagePath() {
        ModelBean modelBean = createModelBean(PACKAGE_PATH, "\"org.fluentcodes.projects.exlasticobjects\"");
        assertEquals("org.fluentcodes.projects.exlasticobjects", modelBean.getPackagePath());
    }

    @Test
    public void testConfigModelKey() {
        ModelBean modelBean = createModelBean(F_CONFIG_MODEL_KEY, "\"CONFIG\"");
        assertEquals("CONFIG", modelBean.getConfigModelKey());
    }

    @Test
    public void testConfigModelKeyNullThenDefault() {
        ModelBean modelBean = createModelBean(F_CONFIG_MODEL_KEY, null);
        assertEquals(ModelConfigObject.class.getSimpleName(), modelBean.getConfigModelKey());
    }

    @Test
    public void testExpose() {
        ModelBean modelBean = createModelBean(F_EXPOSE, "\"WEB\"");
        assertEquals(Expose.WEB, modelBean.getExpose());
    }

    @Test
    public void testModuleScope() {
        ModelBean modelBean = createModelBean(F_MODULE_SCOPE, "\"MODULE\"");
        assertEquals("MODULE", modelBean.getModuleScope());
    }

    @Test
    public void testModule() {
        ModelBean modelBean = createModelBean(F_MODULE, "\"MODULE\"");
        assertEquals("MODULE", modelBean.getModule());
    }

    @Test
    public void testScope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        ModelBean modelBean = createModelBean(F_SCOPE, "\"DEV,PROD\"");
        assertEquals(result, modelBean.getScope());
    }

    @Test
    public void testAuthor() {
        ModelBean modelBean = createModelBean(F_AUTHOR, "\"Author\"");
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
        ModelBean modelBean = createModelBean(F_NATURAL_ID, "\"natural\"");
        assertEquals("natural", modelBean.getNaturalId());
    }

}

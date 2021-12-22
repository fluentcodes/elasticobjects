package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
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
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_FIELD_NAME;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_GENERATED;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_LENGTH;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_MODEL_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_NOT_NULL;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_OVERRIDE;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_PROPERTY;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_UNIQUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FieldBeanTest {

    public static void assertField(final String fieldName, final Object value, final Object expectedResult){
        FieldBean bean = createFieldBean(fieldName, value);
        assertEquals("Problem for " + fieldName, expectedResult, bean.getProperties().get(fieldName));
    }

    public static FieldBean createFieldBean(final String fieldName, final Object value){
        final String serialized =                 "{     \"" + F_FIELD_NAME + "\": {\n" +
                "        \"" + fieldName + "\": "+ value + "\n" +
                "      }\n}}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> propertyFieldMap =  (Map<String, Object>)beanMap.get(F_FIELD_NAME);
        return new FieldBean(propertyFieldMap);
    }

    @Test
    public void empty__toString__empty() {
        FieldBean fieldBean = new FieldBean();
        assertEquals("", fieldBean.toString());
    }

    @Test
    public void naturalId_test__toString__test() {
        FieldBean fieldBean = new FieldBean("test");
        Assertions.assertThat(fieldBean.toString()).hasToString("test");
    }

    @Test
    public void naturalId_field_modelKeys_String__toString__String_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setModelKeys("String");
        Assertions.assertThat(fieldBean.toString()).hasToString("(String)field");
    }

    @Test
    public void naturalId_field_modelKeys_String_modelBean_Model__toString__String_Model_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setParentModel(new ModelBean("Model"));
        fieldBean.setModelKeys("String");
        Assertions.assertThat(fieldBean.toString()).hasToString("(String)Model.field");
    }

    @Test
    public void naturalId_field_modelBean_Model__toString__Model_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setParentModel(new ModelBean("Model"));
        Assertions.assertThat(fieldBean.toString()).hasToString("Model.field");
    }

    @Test
    public void fieldKey_field_modelBean_Model__toString__Model_field() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setFieldKey("field");
        Assertions.assertThat(fieldBean.toString()).hasToString("field");
    }


    @Test
    public void set_notNull_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setNotNull(true);
        Assertions.assertThat(fieldBean.getNotNull()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(F_NOT_NULL)).isTrue();
        EoRoot eo = ProviderConfigMaps.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(F_NOT_NULL)).isTrue();
    }

    @Test
    public void set_override_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setOverride(true);
        Assertions.assertThat(fieldBean.getOverride()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(F_OVERRIDE)).isTrue();
        EoRoot eo = ProviderConfigMaps.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(F_OVERRIDE)).isTrue();
    }

    @Test
    public void set_generated_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setGenerated(true);
        Assertions.assertThat(fieldBean.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(F_GENERATED)).isTrue();
        EoRoot eo = ProviderConfigMaps.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(F_GENERATED)).isTrue();
    }

    @Test
    public void set_generated_true__merge__true() {
        FieldBean fieldBeanOverwritten = new FieldBean();
        FieldBean fieldBean = new FieldBean();
        fieldBean.setGenerated(true);
        fieldBeanOverwritten.merge(fieldBean);
        Assertions.assertThat(fieldBean.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(F_GENERATED)).isTrue();
        Assertions.assertThat(fieldBeanOverwritten.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBeanOverwritten.getProperties().get(F_GENERATED)).isTrue();
        EoRoot eo = ProviderConfigMaps.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(F_GENERATED)).isTrue();
    }

    @Test
    public void set_scope_DEV__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setScope(Arrays.asList(new Scope[]{Scope.DEV}));
        Assertions.assertThat(fieldBean.getScope()).isNotEmpty();
        EoRoot eo = ProviderConfigMaps.createEo(fieldBean);
        Assertions.assertThat((List)eo.get(F_SCOPE)).isNotEmpty();
    }

    @Test
    public void createByElement() {
        final String serialized = "{  \"FieldBean\": {\n" +
                "    \"module\": \"elastic-objects\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": \"fieldKey, modelKeys, length, notNull, override, final, generated, super, javascriptType, transient, min, max, unique, fieldName, jsonIgnore, staticName, default, property\",\n" +
                "    \"interfaces\": \"FieldBeanInterface\",\n" +
                "    \"superKey\": \"ConfigBean\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"The basic bean container class for the configuration class@FieldConfig. Also used as a base for building source code. \",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.models\",\n" +
                "    \"modelKey\": \"FieldBean\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": true,\n" +
                "      \"override\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1607468400000\n}" +
                "}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(FieldBean.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
    }

    @Test
    public void testExample(){
        assertField(F_FIELD_NAME, "\"NAME\"", "NAME");
    }

    @Test
    public void testFieldKey(){
        FieldBean fieldBean = createFieldBean(F_FIELD_KEY, "\"NAME\"");
        assertEquals("NAME", fieldBean.getFieldKey());
    }

    @Test
    public void testModelKeys(){
        FieldBean fieldBean = createFieldBean(F_MODEL_KEYS, "\"Map,String\"");
        assertEquals("Map,String", fieldBean.getModelKeys());
    }

    @Test
    public void testConfigModelKey(){
        FieldBean fieldBean = createFieldBean(F_CONFIG_MODEL_KEY, "\"CONFIG\"");
        assertEquals("CONFIG", fieldBean.getConfigModelKey());
    }

    @Test
    public void testExpose(){
        FieldBean fieldBean = createFieldBean(F_EXPOSE, "\"WEB\"");
        assertEquals(Expose.WEB, fieldBean.getExpose());
    }

    @Test
    public void testModuleScope(){
        FieldBean fieldBean = createFieldBean(F_MODULE_SCOPE, "\"MODULE\"");
        assertEquals("MODULE", fieldBean.getModuleScope());
    }

    @Test
    public void testModule(){
        FieldBean fieldBean = createFieldBean(F_MODULE, "\"MODULE\"");
        assertEquals("MODULE", fieldBean.getModule());
    }

    @Test
    public void testScope(){
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        FieldBean fieldBean = createFieldBean(F_SCOPE, "\"DEV,PROD\"");
        assertEquals(result, fieldBean.getScope());
    }

    @Test
    public void testAuthor(){
        FieldBean fieldBean = createFieldBean(F_AUTHOR, "\"Author\"");
        assertEquals("Author", fieldBean.getAuthor());
    }

    @Test
    public void testCreationDate(){
        FieldBean fieldBean = createFieldBean(F_CREATION_DATE, 1);
        assertEquals(new Date(1L), fieldBean.getCreationDate());
    }

    @Test
    public void testId(){
        FieldBean fieldBean = createFieldBean(F_ID, 1);
        assertEquals(new Long(1L), fieldBean.getId());
    }

    @Test
    public void testNaturalId(){
        FieldBean fieldBean = createFieldBean(F_NATURAL_ID, "\"natural\"");
        assertEquals("natural", fieldBean.getNaturalId());
    }


}

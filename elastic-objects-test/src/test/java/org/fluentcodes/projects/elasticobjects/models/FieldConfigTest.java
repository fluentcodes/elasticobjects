package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.*;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanTest.createFieldBean;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Werner on 17.11.2021.
 */
public class FieldConfigTest implements IConfigurationTests {

    public static FieldConfig createFieldConfig(final String fieldName, final Object value){
        FieldBean bean = createFieldBean(fieldName, value);
        return new FieldConfig(bean, ProviderConfigMapsDev.CONFIG_MAPS_DEV);
    }

    @Override
    public Class<?> getModelConfigClass() {
        return FieldConfig.class;
    }

    @Override
    @Test
    public void createThrowsEoException() {
        assertCreateThrowingException();
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
    public void getDescription() {
        FieldConfig fieldConfig = (FieldConfig) CONFIG_MAPS.find(FieldConfig.class, "description");
        assertThat(fieldConfig).isNotNull();
    }

    @Test
    public void createByElement() {
        final String serialized = "{\"FieldConfig\": {\n" +
                "    \"module\": \"elastic-objects\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": {\n" +
                "      \"fieldKey\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"modelKeys\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"length\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"modelList\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"default\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"fieldName\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"final\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"generated\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"javascriptType\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"jsonIgnore\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"notNull\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "     \"max\": {\n" +
                "       \"final\": true,\n" +
                "       \"property\": false\n" +
                "     },\n" +
                "      \"min\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"override\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"property\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"staticName\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"super\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"transient\": {\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      },\n" +
                "      \"unique\":{\n" +
                "        \"final\": true,\n" +
                "        \"property\": false\n" +
                "      }\n" +
                "    },\n" +
                "    \"interfaces\": \"FieldConfigInterface\",\n" +
                "    \"superKey\": \"ConfigConfig\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Immutabel EO field configuration will be initalized by @FieldBean object.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.models\",\n" +
                "    \"modelKey\": \"FieldConfig\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false,\n" +
                "      \"final\": true,\n" +
                "      \"override\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1539727200000\n}" +
                "  }";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(FieldConfig.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
    }

    @Test
    public void testDefault(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_DEFAULT, true);
        assertTrue( fieldConfig.getProperties().getDefault());
    }

    @Test
    public void testFieldName(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_FIELD_NAME, "\"fieldName\"");
        assertEquals("fieldName", fieldConfig.getProperties().getFieldName());
    }

    @Test
    public void testFinal(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_FINAL, true);
        assertTrue(fieldConfig.getProperties().getFinal());
    }

    @Test
    public void testGenerated(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_GENERATED, true);
        assertTrue(fieldConfig.getProperties().getGenerated());
    }

    @Test
    public void testJavascriptType(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_JAVASCRIPT_TYPE, "\"string\"");
        assertEquals("string", fieldConfig.getProperties().getJavascriptType());
    }

    @Test
    public void testJsonIgnore(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_JSON_IGNORE, true);
        assertTrue(fieldConfig.getProperties().getJsonIgnore());
    }

    @Test
    public void testLength(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_LENGTH, 1);
        assertEquals(new Integer(1), fieldConfig.getProperties().getLength());
    }

    @Test
    public void testMax(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_MAX, 1);
        assertEquals(new Integer(1), fieldConfig.getProperties().getMax());
    }

    @Test
    public void testMin(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_MIN, 1);
        assertEquals(new Integer(1), fieldConfig.getProperties().getMin());
    }

    @Test
    public void testNotNull(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_NOT_NULL, true);
        assertTrue(fieldConfig.getProperties().getNotNull());
    }

    @Test
    public void testOverride(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_OVERRIDE, true);
        assertTrue(fieldConfig.getProperties().getOverride());
    }

    @Test
    public void testProperty(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_PROPERTY, true);
        assertTrue(fieldConfig.getProperties().getProperty());
    }

    @Test
    public void testStaticName(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_STATIC_NAME, true);
        assertTrue(fieldConfig.getProperties().getStaticName());
    }


    @Test
    public void testSuper(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_SUPER, true);
        assertTrue(fieldConfig.getProperties().getSuper());
    }

    @Test
    public void testTransient(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_TRANSIENT, true);
        assertTrue(fieldConfig.getProperties().getTransient());
    }

    @Test
    public void testUnique(){
        FieldConfig fieldConfig = createFieldConfig(FieldBeanProperties.F_UNIQUE, true);
        assertTrue(fieldConfig.getProperties().getUnique());
    }

    @Test
    public void testFieldKey(){
        FieldConfig fieldConfig = createFieldConfig(F_FIELD_KEY, "\"NAME\"");
        assertEquals("NAME", fieldConfig.getFieldKey());
    }

    @Test
    public void testModelKeys(){
        FieldConfig fieldConfig = createFieldConfig(F_MODEL_KEYS, "\"Map,String\"");
        assertEquals("Map,String", fieldConfig.getModelKeys());
    }

    @Test
    public void testExpose(){
        FieldConfig fieldConfig = createFieldConfig(F_EXPOSE, "\"WEB\"");
        assertEquals(Expose.WEB, fieldConfig.getExpose());
    }

    @Test
    public void testModuleScope(){
        FieldConfig fieldConfig = createFieldConfig(F_MODULE_SCOPE, "\"MODULE\"");
        assertEquals("MODULE", fieldConfig.getModuleScope());
    }

    @Test
    public void testModule(){
        FieldConfig fieldConfig = createFieldConfig(F_MODULE, "\"MODULE\"");
        assertEquals("MODULE", fieldConfig.getModule());
    }

    @Test
    public void testScope(){
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        FieldConfig fieldConfig = createFieldConfig(F_SCOPE, "\"DEV,PROD\"");
        assertEquals(result, fieldConfig.getScope());
    }

    @Test
    public void testAuthor(){
        FieldConfig fieldConfig = createFieldConfig(F_AUTHOR, "\"Author\"");
        assertEquals("Author", fieldConfig.getAuthor());
    }

    @Test
    public void testCreationDate(){
        FieldConfig fieldConfig = createFieldConfig(F_CREATION_DATE, 1);
        assertEquals(new Date(1L), fieldConfig.getCreationDate());
    }

    @Test
    public void testId(){
        FieldConfig fieldConfig = createFieldConfig(F_ID, 1);
        assertEquals(new Long(1L), fieldConfig.getId());
    }

    @Test
    public void testNaturalId(){
        FieldConfig fieldConfig = createFieldConfig(F_NATURAL_ID, "\"natural\"");
        assertEquals("natural", fieldConfig.getNaturalId());
    }

}

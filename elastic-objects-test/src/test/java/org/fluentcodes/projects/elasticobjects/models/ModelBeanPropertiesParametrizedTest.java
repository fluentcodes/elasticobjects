package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.*;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.F_ABSTRACT;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.BEAN;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.F_CREATE;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.DB_ANNOTATED;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.DEFAULT_IMPLEMENTATION;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.ID_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.JAVASCRIPT_TYPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.NATURAL_KEYS;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ModelBeanPropertiesParametrizedTest {
    private String fieldName;
    private Object value;
    private Object expectedResult;
    private ModelBean modelBean;
    @Before
    public void initialize() {
        modelBean = new ModelBean();
    }
    public ModelBeanPropertiesParametrizedTest(String fieldName, Object value, Object expectedResult) {
        this.fieldName = fieldName;
        this.value = value;
        this.expectedResult = expectedResult;
    }

    public static void assertProperties(final String fieldName, final Object value, final Object expectedResult){
        final String serialized =                 "{     \"" + F_FIELD_NAME + "\": {\n" +
                "\"properties\":{\"" + fieldName + "\": "+ value + "\n}" +
                "      }\n}}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> propertyFieldMap =  (Map<String, Object>)beanMap.get(F_FIELD_NAME);
        ModelBean bean = new ModelBean(propertyFieldMap);
        assertEquals("Problem for " + fieldName, expectedResult, bean.getProperties().get(fieldName));
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][] {
                {F_ABSTRACT, true, true },
                {F_ABSTRACT, false, false },
                {F_ABSTRACT, null, null },
                { BEAN, "\"bean\"", "bean" },
                { BEAN, null, null },
                {F_CREATE, true, true },
                {F_CREATE, false, false },
                {F_CREATE, null, null },
                { DB_ANNOTATED, true, true },
                { DB_ANNOTATED, false, false },
                { DB_ANNOTATED, null, null },
                { DEFAULT_IMPLEMENTATION, "\"defaultClass\"", "defaultClass" },
                { DEFAULT_IMPLEMENTATION, null, null },
                { F_FINAL, true, true },
                { F_FINAL, false, false },
                { F_FINAL, null, null },
                { ID_KEY, "\"idkey\"", "idkey" },
                { ID_KEY, null, null },
                { JAVASCRIPT_TYPE, "\"string\"", "string" },
                { JAVASCRIPT_TYPE, null, null },
                { NATURAL_KEYS, "\"naturalKeys\"", "naturalKeys" },
                { NATURAL_KEYS, null, null },
                { F_OVERRIDE, true, true },
                { F_OVERRIDE, false, false },
                { F_OVERRIDE, null, null },
                { F_PROPERTY, true, true },
                { F_PROPERTY, false, false },
                { F_PROPERTY, null, null }
        });
    }
    @Test
    public void testModelProperty(){
            ModelBeanTest.assertModel(fieldName, value, expectedResult);
    }

    @Test
    public void testFieldProperties(){
        assertProperties(fieldName, value, expectedResult);
    }

    public static class TestRunner {
        public static void main(String[] args) {
            Result result = JUnitCore.runClasses(ModelBeanPropertiesParametrizedTest.class);
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(result.wasSuccessful());
        }
    }
}

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class FieldBeanPropertiesParametrizedTest {
    private String fieldName;
    private Object value;
    private Object expectedResult;
    private FieldBean fieldBean;
    @Before
    public void initialize() {
        fieldBean = new FieldBean();
    }
    public FieldBeanPropertiesParametrizedTest(String fieldName, Object value, Object expectedResult) {
        this.fieldName = fieldName;
        this.value = value;
        this.expectedResult = expectedResult;
    }

    public static void assertProperties(final String fieldName, final Object value, final Object expectedResult){
        final String serialized =                 "{     \"" + FieldBeanProperties.F_FIELD_NAME + "\": {\n" +
                "\"properties\":{\"" + fieldName + "\": "+ value + "\n}" +
                "      }\n}}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> propertyFieldMap =  (Map<String, Object>)beanMap.get(FieldBeanProperties.F_FIELD_NAME);
        FieldBean bean = new FieldBean(propertyFieldMap);
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][] {
                { FieldBeanProperties.F_DEFAULT, true, true },
                { FieldBeanProperties.F_DEFAULT, false, false },
                { FieldBeanProperties.F_DEFAULT, null, null },
                { FieldBeanProperties.F_FIELD_NAME, "\"NAME\"", "NAME" },
                { FieldBeanProperties.F_FIELD_NAME, null, null },
                { FieldBeanProperties.F_FINAL, true, true },
                { FieldBeanProperties.F_FINAL, false, false },
                { FieldBeanProperties.F_FINAL, null, null },
                { FieldBeanProperties.F_GENERATED, true, true },
                { FieldBeanProperties.F_GENERATED, false, false },
                { FieldBeanProperties.F_GENERATED, null, null },
                { FieldBeanProperties.F_JAVASCRIPT_TYPE, "\"string\"", "string" },
                { FieldBeanProperties.F_JAVASCRIPT_TYPE, null, null },
                { FieldBeanProperties.F_JSON_IGNORE, true, true },
                { FieldBeanProperties.F_JSON_IGNORE, false, false },
                { FieldBeanProperties.F_JSON_IGNORE, null, null },
                { FieldBeanProperties.F_LENGTH, 1, 1 },
                { FieldBeanProperties.F_LENGTH, null, null },
                { FieldBeanProperties.F_MAX, 1, 1 },
                { FieldBeanProperties.F_MAX, null, null },
                { FieldBeanProperties.F_MIN, 1, 1 },
                { FieldBeanProperties.F_MIN, null, null },
                { FieldBeanProperties.F_NOT_NULL, true, true },
                { FieldBeanProperties.F_NOT_NULL, false, false },
                { FieldBeanProperties.F_NOT_NULL, null, null },
                { FieldBeanProperties.F_OVERRIDE, true, true },
                { FieldBeanProperties.F_OVERRIDE, false, false },
                { FieldBeanProperties.F_OVERRIDE, null, null },
                { FieldBeanProperties.F_PROPERTY, true, true },
                { FieldBeanProperties.F_PROPERTY, false, false },
                { FieldBeanProperties.F_PROPERTY, null, null },
                { FieldBeanProperties.F_STATIC_NAME, true, true },
                { FieldBeanProperties.F_STATIC_NAME, false, false },
                { FieldBeanProperties.F_STATIC_NAME, null, null },
                { FieldBeanProperties.F_SUPER, true, true },
                { FieldBeanProperties.F_SUPER, false, false },
                { FieldBeanProperties.F_SUPER, null, null },
                { FieldBeanProperties.F_TRANSIENT, true, true },
                { FieldBeanProperties.F_TRANSIENT, false, false },
                { FieldBeanProperties.F_TRANSIENT, null, null },
                { FieldBeanProperties.F_UNIQUE, true , true},
                { FieldBeanProperties.F_UNIQUE, false, false },
                { FieldBeanProperties.F_UNIQUE, null, null }
        });
    }

    @Test
    public void testFieldProperties(){
        assertProperties(fieldName, value, expectedResult);
    }

    public static class TestRunner {
        public static void main(String[] args) {
            Result result = JUnitCore.runClasses(FieldBeanPropertiesParametrizedTest.class);

            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }

            System.out.println(result.wasSuccessful());
        }
    }
}

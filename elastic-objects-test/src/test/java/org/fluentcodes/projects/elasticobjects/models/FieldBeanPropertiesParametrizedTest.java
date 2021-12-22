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

import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.*;
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
        final String serialized =                 "{     \"" + F_FIELD_NAME + "\": {\n" +
                "\"properties\":{\"" + fieldName + "\": "+ value + "\n}" +
                "      }\n}}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> propertyFieldMap =  (Map<String, Object>)beanMap.get(F_FIELD_NAME);
        FieldBean bean = new FieldBean(propertyFieldMap);
        assertEquals("Problem for " + fieldName, expectedResult, bean.getProperties().get(fieldName));
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][] {
                { F_DEFAULT, true, true },
                { F_DEFAULT, false, false },
                { F_DEFAULT, null, null },
                { F_FIELD_NAME, "\"NAME\"", "NAME" },
                { F_FIELD_NAME, null, null },
                { F_FINAL, true, true },
                { F_FINAL, false, false },
                { F_FINAL, null, false },
                { F_GENERATED, true, true },
                { F_GENERATED, false, false },
                { F_GENERATED, null, false },
                { F_JAVASCRIPT_TYPE, "\"string\"", "string" },
                { F_JAVASCRIPT_TYPE, null, null },
                { F_JSON_IGNORE, true, true },
                { F_JSON_IGNORE, false, false },
                { F_JSON_IGNORE, null, null },
                { F_LENGTH, 1, 1 },
                { F_LENGTH, null, null },
                { F_MAX, 1, 1 },
                { F_MAX, null, null },
                { F_MIN, 1, 1 },
                { F_MIN, null, null },
                { F_NOT_NULL, true, true },
                { F_NOT_NULL, false, false },
                { F_NOT_NULL, null, false },
                { F_OVERRIDE, true, true },
                { F_OVERRIDE, false, false },
                { F_OVERRIDE, null, false },
                { F_PROPERTY, true, true },
                { F_PROPERTY, false, false },
                { F_PROPERTY, null, null },
                { F_STATIC_NAME, true, true },
                { F_STATIC_NAME, false, false },
                { F_STATIC_NAME, null, null },
                { F_SUPER, true, true },
                { F_SUPER, false, false },
                { F_SUPER, null, false },
                { F_TRANSIENT, true, true },
                { F_TRANSIENT, false, false },
                { F_TRANSIENT, null, false },
                { F_UNIQUE, true , true},
                { F_UNIQUE, false, false },
                { F_UNIQUE, null, false }
        });
    }
    @Test
    public void testFieldProperty(){
            FieldBeanTest.assertField(fieldName, value, expectedResult);
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

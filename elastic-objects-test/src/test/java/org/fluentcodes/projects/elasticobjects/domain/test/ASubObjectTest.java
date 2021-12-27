package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FIELD_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ASubObjectTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ASubObject.class;
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
    public void createByElement() {
        final String serialized = "{  \"ASubObject\": {\n" +
                "    \"configModelKey\": \"ModelConfigObject\",\n" +
                "    \"module\": \"elastic-objects-test\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": {\n" +
                "      \"id\": {\n" +
                "        \"final\": false,\n" +
                "        \"staticName\": false\n" +
                "      },\n" +
                "      \"naturalId\": {\n" +
                "        \"final\": false\n" +
                "      },\n" +
                "      \"myASubObject\": {\n" +
                "        \"final\": false\n" +
                "      },\n" +
                "      \"name\": {\n" +
                "        \"final\": false\n" +
                "      },\n" +
                "      \"myString\": {\n" +
                "        \"final\": false\n" +
                "      }\n" +
                "    },\n" +
                "    \"expose\": \"WEB\",\n" +
                "    \"description\": \"A sub object as an example.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.domain.test\",\n" +
                "    \"modelKey\": \"ASubObject\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1605826800000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(ASubObject.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(ASubObject.class.getSimpleName(), bean.getModelKey());
        assertEquals(ASubObject.class.getSimpleName(), bean.getNaturalId());
        assertEquals(F_ID, bean.getFieldBean(F_ID).getFieldKey());
        assertEquals(F_ID, bean.getFieldBean(F_ID).getNaturalId());
    }
}

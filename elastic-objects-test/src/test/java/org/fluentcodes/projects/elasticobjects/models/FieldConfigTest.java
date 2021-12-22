package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Werner on 17.11.2021.
 */
public class FieldConfigTest implements IConfigurationTests {

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
}

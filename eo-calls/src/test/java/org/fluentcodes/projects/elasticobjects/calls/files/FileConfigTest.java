package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Werner on 12.10.2016.
 */
public class FileConfigTest implements IConfigurationTests {
    public static final String FILE_TEST_TXT = "FileTest.txt";

    @Override
    public Class<?> getModelConfigClass() {
        return FileConfig.class;
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
    public void compareConfigurations() {
        assertLoadedConfigurationsEqualsPersisted();
    }

    @Test
    public void testScope__findFileConfig_FileTestTxt__found() {
        FileConfig config = (FileConfig) ProviderConfigMaps.CONFIG_MAPS.find(FileConfig.class, FILE_TEST_TXT);
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getDescription()).isNotNull();
    }

    @Test
    public void createByElement() {
        final String serialized = "{   \"FileConfig\": {\n" +
                "    \"module\": \"eo-calls\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": {\n" +
                "      \"cached\": {\n" +
                "        \"final\": true\n" +
                "      },\n" +
                "      \"fileName\": {\n" +
                "        \"final\": true\n" +
                "      },\n" +
                "      \"filePath\": {\n" +
                "        \"final\": true\n" +
                "      },\n" +
                "      \"hostConfigKey\": {\n" +
                "        \"final\": true\n" +
                "      },\n" +
                "      \"FileConfig.properties\": {\n" +
                "        \"final\": true\n" +
                "      }\n" +
                "    },\n" +
                "    \"interfaces\": \"FileInterface\",\n" +
                "    \"superKey\": \"PermissionConfig\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Immutable EO file configuration allow read or write access to a specific file. \",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.calls.files\",\n" +
                "    \"modelKey\": \"FileConfig\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1539727200000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(FileConfig.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(FileConfig.class.getSimpleName(), bean.getModelKey());
        assertEquals(FileConfig.class.getSimpleName(), bean.getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }

}


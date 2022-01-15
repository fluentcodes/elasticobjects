package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
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
        FileConfig config = (FileConfig) ObjectProvider.CONFIG_MAPS.find(FileConfig.class, FILE_TEST_TXT);
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getDescription()).isNotNull();
    }
}


package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitems.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
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
    public void find_FileTestTxt() {
        FileConfig config = (FileConfig) ObjectProvider.CONFIG_MAPS.find(FileConfig.class, FILE_TEST_TXT);
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getDescription()).isNotNull();
    }

}


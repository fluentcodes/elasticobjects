package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_KEY_UNKNOW;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;

public class ModelConfigTest implements IConfigurationTests {
    private static final Logger LOG = LogManager.getLogger(ModelConfigTest.class);

    @Override
    public Class<?> getModelConfigClass() {
        return ModelConfig.class;
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

    @Test(expected = EoException.class)
    public void findModel_Unknown__exception() {
        CONFIG_MAPS.findModel(SAMPLE_KEY_UNKNOW);
    }

    @Test
    public void checkDependentModels() {
        // Check if basic Models are available
        ModelConfig model = CONFIG_MAPS.findModel(AnObject.class.getSimpleName());
        Assert.assertEquals(AnObject.class.getSimpleName(), model.getModelKey());
        model = CONFIG_MAPS.findModel(ASubObject.class);
        Assert.assertEquals(ASubObject.class.getSimpleName(), model.getModelKey());
    }
}

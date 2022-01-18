package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_KEY_UNKNOW;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void createConfigBean_naturalId() {
        ModelBean bean = ObjectProvider.createModelBean(F_NATURAL_ID, "test");
        assertNotNull(bean.getNaturalId());
        ModelConfig config = new ModelConfigObject(bean, ObjectProvider.CONFIG_MAPS);
        EoRoot cloneMap = EoRoot.ofClass(CONFIG_MAPS, Map.class);
        cloneMap.setSerializationType(JSONSerializationType.STANDARD);
        cloneMap.map(config);
    }
}

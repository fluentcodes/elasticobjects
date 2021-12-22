package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_EXPOSE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Werner on 13.4.2017.
 */
public class ConfigConfigTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return ConfigConfig.class;
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
    public void checkFinal() {
        ModelConfig config = ProviderConfigMaps.findModel(ConfigConfig.class);
        FieldConfig fieldConfig = config.getField(F_EXPOSE);
        assertTrue(fieldConfig.getFinal());
        Boolean property = fieldConfig.getProperty();
        assertFalse(property);
        XpectStringJunit4.assertStatic(fieldConfig.toString());
    }

}

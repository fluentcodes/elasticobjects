package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigTests;
import org.junit.Test;

/**
 * Created by Werner on 21.11.2021.
 */
public class HostConfigTest implements IConfigurationTests {

    @Override
    public Class<?> getModelConfigClass() {
        return HostConfig.class;
    }

    @Test
    public void createThrowsEoException() {
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
    public void elasticobjects__compare__xpected() {
        assertConfigBeanEqualsPersisted("elasticobjects");
    }

    @Test
    public void dummyftp__compare__xpected() {
        assertConfigBeanEqualsPersisted("dummyftp");
    }

    @Test
    public void dummyftpurl__compare__xpected() {
        assertConfigBeanEqualsPersisted("dummyftpurl");
    }

}

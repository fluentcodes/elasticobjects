package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.testitems.IConfigurationTests;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbSqlConfigTest implements IConfigurationTests {
    @Override
    public Class<?> getModelConfigClass() {
        return DbSqlConfig.class;
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

}


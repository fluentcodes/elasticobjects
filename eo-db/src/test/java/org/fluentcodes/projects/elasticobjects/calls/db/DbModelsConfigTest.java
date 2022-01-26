package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Werner on 30.10.2020.
 */
public class DbModelsConfigTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return DbModelsConfig.class;
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
    public void h2mem() {
        DbModelsConfig config = (DbModelsConfig)ObjectProvider.CONFIG_MAPS.
                find(DbModelsConfig.class, "h2:mem");
        assertNotNull(config);
    }

}


package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.files.CsvConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Werner on 11.10.2016.
 */
public class CsvConfigTest implements IModelConfigNoCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return CsvConfig.class;
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
    public void checkTargetCsv() {
        CsvConfig config = (CsvConfig) ProviderConfigMaps.CONFIG_MAPS
                .find(FileConfig.class, "target.csv");
        Assert.assertNotNull(config);
        Assert.assertTrue(config.hasRowHead());
        Assert.assertFalse(config.hasRowStart());
        Assert.assertFalse(config.hasRowEnd());
        Assert.assertFalse(config.hasLength());
        Assert.assertFalse(config.hasFilter());
        Assert.assertFalse(config.hasColKeys());
        Assert.assertEquals(new Integer(0), config.getRowHead());
        Assert.assertNull(config.getRowStart());
        Assert.assertNull(config.getRowEnd());
        Assert.assertNull(config.getLength());
        Assert.assertNull(config.getFilter());
        Assert.assertEquals(new ArrayList<String>(), config.getColKeys());
    }

}

package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Werner on 11.10.2016.
 */
class FileConfigCsvTest{

    @Test
    public void checkTargetCsv() {
        FileConfig config = (FileConfig)ProviderConfigMaps.CONFIG_MAPS
                .find(FileConfig.class, "target.csv");
        Assert.assertNotNull(config);
        Assert.assertTrue(config.getProperties().hasRowHead());
        Assert.assertFalse(config.getProperties().hasRowStart());
        Assert.assertFalse(config.getProperties().hasRowEnd());
        Assert.assertFalse(config.getProperties().hasLength());
        Assert.assertFalse(config.getProperties().hasFilter());
        Assert.assertFalse(config.getProperties().hasColKeys());
        Assert.assertEquals(new Integer(0), config.getProperties().getRowHead());
        Assert.assertNull(config.getProperties().getRowStart());
        Assert.assertNull(config.getProperties().getRowEnd());
        Assert.assertNull(config.getProperties().getLength());
        Assert.assertNull(config.getProperties().getFilter());
        Assert.assertEquals(new ArrayList<String>(), config.getProperties().getColKeys());
    }
}

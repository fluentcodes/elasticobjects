package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Werner on 6.10.2018.
 */
public class NoPath {
    private static final Logger LOG = LogManager.getLogger(NoPath.class);

    @Test
    public void empty()  {
        
        EOConfigsCache configsCache = ProviderRootTest.EO_CONFIGS;
        EO adapter = ProviderRootTest.createEo();
        Assert.assertEquals(Map.class, adapter.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, adapter.get().getClass());
        Assert.assertTrue(adapter.keysEo().size() == 0);
    }

}

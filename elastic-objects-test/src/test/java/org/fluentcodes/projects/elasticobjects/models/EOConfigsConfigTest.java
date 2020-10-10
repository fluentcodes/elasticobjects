package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * Created by werner.diwischek on 09.12.17.
 */
public class EOConfigsConfigTest {
    private static final Logger LOG = LogManager.getLogger(EOConfigsConfigTest.class);

    @Test
    public void checkConfigsCache()  {
        ModelConfigInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(ModelConfigObject.class);
        model.resolve();
        //new XpectEo<>(TRootTestProvider.EO_CONFIGS).compareAsString(model);
    }

    @Test
    public void testModelNotExisting_Exception()  {
        try {
            ModelConfigInterface model = ProviderRootTestScope.EO_CONFIGS.findModel("Nonsense");
            Assert.fail("Should throw EoException since Nonsense is not in the cache");
        }
        catch(EoException e) {
            LOG.info(e.getMessage());
        }
    }


    @Test
    public void checkConfigsCacheWithST()  {
        ModelConfigInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(ASubObject.class);
        model.resolve();
        //new XpectEo<>(TRootTestProvider.EO_CONFIGS).compareAsString(model);
    }

    // Just a first check for the dev scope working.
    @Test
    public void checkConfigsCacheDev()  {
        EOConfigsCache configsCache = new EOConfigsCache(Scope.DEV);
        ModelConfigInterface model = configsCache.findModel(Map.class);
        model.resolve();
        EO adapter = ProviderRootTestScope.createEo(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get());
        Assert.assertEquals(M_STRING, adapter.getModelClass().getSimpleName());
    }


}
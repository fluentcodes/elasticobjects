package org.fluentcodes.projects.elasticobjects.calls.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.models.*;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.10.2016.
 */
public class FileConfigTest {
    private static final Logger LOG = LogManager.getLogger(FileConfigTest.class);

    @Test
    public void givenModelClass_whenCreate_thenExceptionThrown()  {
        ConfigModelChecks.createThrowException(FileConfig.class);
    }

    @Test
    public void givenModel_whenCompare_thenEqual()  {
        ConfigModelChecks.compare(FileConfig.class);
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(FileConfig.class);
    }

    @Test
    public void whenResolveConfigEntries_thenNoError()  {
        ConfigChecks.resolveConfigEntries(FileConfig.class);
    }

    @Test
    public void whenCompareConfigurations_thenXpected()  {
        ConfigChecks.compareConfigurations(FileConfig.class);
    }


    @Test
    public void testfindSourceTxt()  {
        FileConfig config = ProviderRootTestScope.EO_CONFIGS.findFile(FILE_SOURCE_TXT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void testCreateConfigMapDirect()  {
        EOConfigMap map = new EOConfigMapImmutable(ProviderRootTestScope.EO_CONFIGS, FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(FILE_SOURCE_TXT));
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(FILE_RESULT_STRING));
    }
}


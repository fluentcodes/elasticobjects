package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.BEO_STATIC.JSON_MODELS_XLSX_MODEL;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_JSON_MAIN;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

/**
 * Created by Werner on 04.11.2016.
 */
public class JsonConfigTest {
    private static final Logger LOG = LogManager.getLogger(JsonConfigTest.class);

    @Test
    public void findConfigInCache()  {
        final FileConfig config = TestObjectProvider.EO_CONFIGS_CACHE.findJson(JSON_MODELS_XLSX_MODEL);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        final Map<String, Config> map = TestConfig.readClassPathConfig(JsonConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(JSON_MODELS_XLSX_MODEL));
    }

    @Test
    public void readMapMain()  {
        final Map map = TestConfig.readMapFromFile(CONFIG_JSON_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(JSON_MODELS_XLSX_MODEL));
    }

    @Test
    public void readConfigMain()  {
        final Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_JSON_MAIN, JsonConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(JSON_MODELS_XLSX_MODEL));
    }
}
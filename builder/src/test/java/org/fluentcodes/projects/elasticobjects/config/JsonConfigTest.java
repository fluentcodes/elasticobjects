package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_JSON_MAIN;

/**
 * Created by Werner on 04.11.2016.
 */
public class JsonConfigTest {
    private static final Logger LOG = LogManager.getLogger(JsonConfigTest.class);

    @Test
    public void readConfigsMain()  {
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_JSON_MAIN, JsonConfig.class);
    }

    @Test
    public void readConfigMainAsMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_JSON_MAIN);
    }


}
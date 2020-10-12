package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.TreeSet;

/**
 * Created by Werner on 11.10.2016.
 */
public class ModelConfigDevTest {
    @Test
    public void check() {
        EOConfigsCache cache = ProviderRootDevScope.EO_CONFIGS;
        TreeSet<String> keys = new TreeSet<>(cache.getConfigKeys(ModelConfig.class));
        for (String key: keys) {
            ModelConfig model = cache.findModel(key);
            Assertions.assertThat(model).isNotNull();
            model.resolve();
        }
    }
}

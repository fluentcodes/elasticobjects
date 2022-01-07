package org.fluentcodes.projects.elasticobjects.io;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.CONFIG_MAPS_DEV;

public class IOClasspathEoFlatMapTest {
    @Test
    public void DEV_ModelConfigMapMap() {
        IOClasspathEOFlatMap<Map> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS_DEV, "ModelConfig.json", Map.class);
        Map<String, Map> modelMap = io.read();
        Assertions.assertThat(modelMap).isNotNull();
        Assertions.assertThat(modelMap.get("ModelBean")).isNotNull();
    }
}

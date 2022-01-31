package org.fluentcodes.projects.elasticobjects.io;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev.CONFIG_MAPS_DEV;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IOClasspathEoFlatListTest {
    @Test
    public void DEV_ModelConfigListMap() {
        IOClasspathEOFlatList<Map> io = new IOClasspathEOFlatList<>(CONFIG_MAPS_DEV, "ModelConfig.json", Map.class);
        List<Map> modelList = io.read();
        assertNotNull(modelList);
        assertEquals(41, modelList.size());
    }
}

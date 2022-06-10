package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ModelConfigMapDevTest {

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(ModelConfigMap.class.getSimpleName());
        assertEquals("ModelConfig", bean.getSuperKey());
        assertEquals(0, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(ModelConfigMap.class.getSimpleName());
        assertEquals(false, config.getProperties().getCreate());
        assertEquals(0, config.getFields().size());
    }

    @Test
    public void findModel() {
        final ModelConfigMap mapModel = (ModelConfigMap) ObjectProviderDev.CONFIG_MAPS_DEV.findModel(Map.class);
        Assert.assertEquals(Map.class, mapModel.getModelClass());
        final Map map = (Map) mapModel.create();
        Assert.assertEquals(LinkedHashMap.class, map.getClass());
    }
}

package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigMapTest {

    @Test
    public void readMainJackson() {
        final ModelConfigMap mapModel = (ModelConfigMap) ProviderConfigMapsDev.CONFIG_MAPS_DEV.findModel(Map.class);
        Assert.assertEquals(Map.class, mapModel.getModelClass());
        final Map map = (Map) mapModel.create();
        Assert.assertEquals(LinkedHashMap.class, map.getClass());
    }

    @Test
    public void assertMap() {
        ModelConfig model = ProviderConfigMaps.CONFIG_MAPS.findModel(Map.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.MAP, model.getShapeType());
        Assert.assertTrue(model.isMap());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertFalse(model.isObject());
    }


}

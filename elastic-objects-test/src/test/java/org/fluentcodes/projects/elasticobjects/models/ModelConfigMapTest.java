package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ModelConfigMapTest {

    @Test
    public void findModel() {
        ModelConfig model = ObjectProvider.CONFIG_MAPS.findModel(Map.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.MAP, model.getShapeType());
        Assert.assertTrue(model.isMap());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertFalse(model.isObject());
    }


}

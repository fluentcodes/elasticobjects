package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigScalarTest {

    @Test(expected = EoException.class)
    public void findModel() {
        ModelConfig model = ObjectProvider.CONFIG_MAPS.findModel(String.class);
        Assert.assertEquals(String.class, model.getModelClass());
        Assert.assertEquals(ShapeTypes.STRING, model.getShapeType());
        Assert.assertFalse(model.isObject());
        Assert.assertTrue(model.isScalar());
        Assert.assertEquals(String.class.getSimpleName(), model.getNaturalId());
        Assert.assertEquals(String.class.getSimpleName(), model.getModelKey());
        model.create();
    }

}

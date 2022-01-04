package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigScalarTest {

    @Test
    public void assertString()  {
        ModelConfig model = ProviderConfigMaps.CONFIG_MAPS.findModel(String.class);
        Assert.assertEquals(String.class, model.getModelClass());
        Assert.assertEquals(ShapeTypes.STRING, model.getShapeType());
        Assert.assertFalse(model.isObject());
        Assert.assertTrue(model.isScalar());
        Assert.assertEquals(String.class.getSimpleName(), model.getNaturalId());
        Assert.assertEquals(String.class.getSimpleName(), model.getModelKey());
        Assertions.assertThat(model.create()).isNull();
    }

}

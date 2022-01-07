package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Assert;

/**
 * Created by Werner on 18.11.2021.
 */
public interface IModelConfigCreateTests extends IModelConfigTests {

    void create_noEoException();

    default void assertCreateNoException() {
        Object object = getModelConfig().create();
        Assert.assertNotNull(object);
    }

    default Object assertSetGet(final String fieldKey, final Object value) {
        Object object = getModelConfig().create();
        ModelConfig model = ObjectProvider.CONFIG_MAPS.findModel(getModelConfigClass());
        model.set(fieldKey, object, value);
        Assert.assertEquals(value, model.get(fieldKey, object));
        return object;
    }
}

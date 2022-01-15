package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ModelConfigScalarDevTest {

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(ModelConfigScalar.class.getSimpleName());
        assertEquals("ModelConfig", bean.getSuperKey());
        assertEquals(0, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(ModelConfigScalar.class.getSimpleName());
        assertEquals(false, config.getProperties().getCreate());
        assertEquals(0, config.getFields().size());
    }
}

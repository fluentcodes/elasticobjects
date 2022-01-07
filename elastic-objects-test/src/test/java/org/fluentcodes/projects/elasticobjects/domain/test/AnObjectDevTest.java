package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnObjectDevTest {

    @Test
    public void readBeanAnObject() {
        ModelBean bean = ObjectProviderDev.readModelBean(AnObject.class.getSimpleName());
        assertEquals(AnObject.class.getSimpleName(), bean.getModelKey());
        assertEquals(16, bean.getFieldKeys().size());
    }

    @Test
    public void readConfigAnObject() {
        ModelConfig config = ObjectProviderDev.readModelConfig(AnObject.class.getSimpleName());
        assertEquals(AnObject.class.getSimpleName(), config.getModelKey());
        assertEquals(16, config.getFieldKeys().size());
    }
}

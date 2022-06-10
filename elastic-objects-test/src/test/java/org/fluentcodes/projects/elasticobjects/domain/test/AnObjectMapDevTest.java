package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnObjectMapDevTest {
    static final String AN_OBJECT_MAP = "AnObjectMap";
    @Test
    public void readAnObject() {
        ModelBean bean = ObjectProviderDev.readModelBean(AN_OBJECT_MAP);
        assertEquals(AN_OBJECT_MAP, bean.getModelKey());
        assertEquals(2, bean.getFieldKeys().size());
    }

    @Test
    public void readConfigAnObject() {
        ModelConfig config = ObjectProviderDev.readModelConfigMap(AN_OBJECT_MAP);
        assertEquals(AN_OBJECT_MAP, config.getModelKey());
        assertEquals(2, config.getFieldKeys().size());
    }

}

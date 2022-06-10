package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PermissionsForBeanDevTest {
    @Test
    public void readHostBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(PermissionsForBean.class.getSimpleName());
        assertNull( bean.getSuperKey());
        assertEquals(6, bean.getFieldKeys().size());
    }

    @Test
    public void readHostConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(PermissionsForBean.class.getSimpleName());
        assertNull(config.getSuperKey());
        assertEquals(6, config.getFields().size());
    }
}

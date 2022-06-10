package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class HostConfigDevTest {
    @Test
    public void readHostBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(HostConfig.class.getSimpleName());
        assertEquals(PermissionConfig.class.getSimpleName(), bean.getSuperKey());
        assertEquals(2, bean.getFieldKeys().size());
        assertNull(bean.getField(F_PERMISSIONS));
        assertNotNull(bean.getField(F_PROPERTIES));
    }

    @Test
    public void readHostConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(HostConfig.class.getSimpleName());
        assertEquals(PermissionConfig.class.getSimpleName(), config.getSuperKey());
        assertEquals(2, config.getFields().size());
    }
}

package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionConfigDevTest {
    @Test
    public void readHostBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(PermissionConfig.class.getSimpleName());
        assertEquals(Config.class.getSimpleName(), bean.getSuperKey());
        assertEquals(1, bean.getFieldKeys().size());
        assertNotNull(bean.getField(F_PERMISSIONS));
    }

    @Test
    public void readHostConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(PermissionConfig.class.getSimpleName());
        assertEquals(Config.class.getSimpleName(), config.getSuperKey());
        assertEquals(1, config.getFields().size());
    }
}

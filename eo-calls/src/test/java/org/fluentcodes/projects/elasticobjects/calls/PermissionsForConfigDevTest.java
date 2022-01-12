package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PermissionsForConfigDevTest {
    @Test
    public void readHostBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(PermissionsForConfig.class.getSimpleName());
        assertNull( bean.getSuperKey());
        assertEquals(6, bean.getFieldKeys().size());
    }

    @Test
    public void readHostConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(PermissionsForConfig.class.getSimpleName());
        assertNull(config.getSuperKey());
        assertEquals(6, config.getFields().size());
    }
}

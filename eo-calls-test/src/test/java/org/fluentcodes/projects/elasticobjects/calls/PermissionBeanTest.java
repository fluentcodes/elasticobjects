package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionBeanTest {

    @Test
    public void findModel() {
        ModelConfig config = CONFIG_MAPS.findModel(PermissionBean.class.getSimpleName());
        PermissionBean bean = (PermissionBean) config.create();
        assertNotNull(config.getField(F_PERMISSIONS));
        config.set(F_PERMISSIONS, bean, new PermissionsForBean());
        assertNotNull(bean.getPermissions());
    }
}

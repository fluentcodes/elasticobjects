package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
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

package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertNotNull;

public class PermissionConfigTest {
    @Test
    public void findModel() {
        ModelConfig config = CONFIG_MAPS.findModel(PermissionConfig.class.getSimpleName());
        //Map permission = (Map) config.create();
        assertNotNull(config.getField(F_PERMISSIONS));
    }
}

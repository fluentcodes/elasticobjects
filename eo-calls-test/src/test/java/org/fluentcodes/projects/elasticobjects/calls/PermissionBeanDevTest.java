package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import java.util.Arrays;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionBeanDevTest {
    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(PermissionBean.class.getSimpleName());
        assertEquals("ConfigBean", bean.getSuperKey());
        assertEquals(1, bean.getFieldKeys().size());
        assertNotNull(bean.getField(F_PERMISSIONS));
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(PermissionBean.class.getSimpleName());
        assertEquals("ConfigBean", config.getSuperKey());
        assertEquals(1, config.getFields().size());
    }

    @Test
    public void readModelBeanResolve() {
        ModelBean bean = ObjectProviderDev.readModelBean(PermissionBean.class.getSimpleName());
        assertNotNull(bean.getField(F_PERMISSIONS));

        bean.mergeFieldBeanMap(ObjectProviderDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ObjectProviderDev.assertCreateModelBeanMap(), true);

        ModelConfig modelConfig = ObjectProviderDev.createModelConfig(bean);
        modelConfig.resolve(ObjectProviderDev.createModelConfigMap());
    }

    @Test
    public void new_createRoles() {
        PermissionBean bean = new PermissionBean();
        bean.getPermissions().setCreateRoles(Arrays.asList("1", "2"));
        assertEquals(Arrays.asList("1", "2"), bean.getPermissions().getCreateRoles());
    }
}

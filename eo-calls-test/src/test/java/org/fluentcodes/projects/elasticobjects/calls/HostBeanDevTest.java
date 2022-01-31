package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class HostBeanDevTest {
    @Test
    public void readHostBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(HostBean.class.getSimpleName());
        assertEquals(PermissionBean.class.getSimpleName(), bean.getSuperKey());
        assertEquals(2, bean.getFieldKeys().size());
        assertNull(bean.getField(F_PERMISSIONS));
        assertNotNull(bean.getField(F_PROPERTIES));
    }

    @Test
    public void readHostConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(HostBean.class.getSimpleName());
        assertEquals(PermissionBean.class.getSimpleName(), config.getSuperKey());
        assertEquals(2, config.getFields().size());
    }

    @Test
    public void resolveHostBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(HostBean.class.getSimpleName());
        bean.mergeFieldBeanMap(ObjectProviderDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ObjectProviderDev.assertCreateModelBeanMap(), true);

        ModelConfig modelConfig = ObjectProviderDev.createModelConfig(bean);
        modelConfig.resolve(ObjectProviderDev.createModelConfigMap());
        assertNotNull(bean.getField(F_PERMISSIONS));
    }
}

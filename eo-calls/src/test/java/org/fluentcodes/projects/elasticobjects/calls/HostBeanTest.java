package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.HostBean.F_HOST_NAME;
import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createKeyValueJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HostBeanTest implements IModelConfigCreateTests {

    static HostBean createHostBean(final String key, final Object value) {
        EoRoot root = ObjectProvider.createEoWithClasses(HostBean.class);
        root.map(createKeyValueJson(key, value));
        return (HostBean)root.get();
    }

    @Override
    public Class<?> getModelConfigClass() {
        return HostBean.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void createHostBean_modelKey() {
        HostBean bean = createHostBean(F_HOST_NAME, "local");
        assertEquals("local", bean.getHostName());
    }

    @Test
    public void findModel() {
        ModelConfig config = CONFIG_MAPS.findModel(HostBean.class.getSimpleName());
        assertNotNull(config.getField(F_PROPERTIES));
        assertNotNull(config.getField(F_PERMISSIONS));
        HostBean bean = (HostBean) config.create();

    }

}

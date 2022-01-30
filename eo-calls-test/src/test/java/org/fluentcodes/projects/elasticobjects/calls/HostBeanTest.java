package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.HostBean.F_HOST_NAME;
import static org.fluentcodes.projects.elasticobjects.calls.HostBeanProperties.*;
import static org.fluentcodes.projects.elasticobjects.calls.PermissionBean.F_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.F_CLASS_PATH;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HostBeanTest implements IModelConfigCreateTests {

    static HostBean createHostBeanProperty(final String key, final Object value) {
        return (HostBean) ObjectProvider.createObject(HostBean.class, value, F_PROPERTIES, key);
    }

    static HostBean createHostBeanField(final String key, final Object value) {
        return (HostBean) ObjectProvider.createObject(HostBean.class, value, key);
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
    public void createHostBeanField_hostName() {
        HostBean bean = createHostBeanField(F_HOST_NAME, "local");
        assertEquals("local", bean.getHostName());
    }

    @Test
    public void createHostBeanProperty_classPath() {
        HostBean bean = createHostBeanProperty(F_CLASS_PATH, "src/test/resource");
        assertEquals("src/test/resource", bean.getProperties().getClassPath());
    }

    @Test
    public void createHostBeanProperty_dialect() {
        HostBean bean = createHostBeanProperty(F_DIALECT, "oracle");
        assertEquals("oracle", bean.getProperties().getDialect());
    }

    @Test
    public void createHostBeanProperty_driver() {
        HostBean bean = createHostBeanProperty(F_DRIVER, "oracle");
        assertEquals("oracle", bean.getProperties().getDriver());
    }

    @Test
    public void createHostBeanProperty_extension() {
        HostBean bean = createHostBeanProperty(F_EXTENSION, "file");
        assertEquals("file", bean.getProperties().getExtension());
    }

    @Test
    public void createHostBeanProperty_jndi() {
        HostBean bean = createHostBeanProperty(F_JNDI, "jndi");
        assertEquals("jndi", bean.getProperties().getJndi());
    }

    @Test
    public void createHostBeanProperty_password() {
        HostBean bean = createHostBeanProperty(F_PASSWORD, "password");
        assertEquals("password", bean.getProperties().getPassword());
    }

    @Test
    public void createHostBeanProperty_port() {
        HostBean bean = createHostBeanProperty(F_PORT, 99);
        assertEquals(Integer.valueOf(99), bean.getProperties().getPort());
    }

    @Test
    public void createHostBeanProperty_protocol() {
        HostBean bean = createHostBeanProperty(F_PROTOCOL, "protocol");
        assertEquals("protocol", bean.getProperties().getProtocol());
    }

    @Test
    public void createHostBeanProperty_schema() {
        HostBean bean = createHostBeanProperty(F_SCHEMA, "schema");
        assertEquals("schema", bean.getProperties().getSchema());
    }

    @Test
    public void createHostBeanProperty_url() {
        HostBean bean = createHostBeanProperty(F_URL, "url");
        assertEquals("url", bean.getProperties().getUrl());
    }

    @Test
    public void createHostBeanProperty_user() {
        HostBean bean = createHostBeanProperty(F_USER, "user");
        assertEquals("user", bean.getProperties().getUser());
    }


    @Test
    public void findModel() {
        ModelConfig config = CONFIG_MAPS.findModel(HostBean.class.getSimpleName());
        assertNotNull(config.getField(F_PROPERTIES));
        assertNotNull(config.getField(F_PERMISSIONS));
        HostBean bean = (HostBean) config.create();

    }

}

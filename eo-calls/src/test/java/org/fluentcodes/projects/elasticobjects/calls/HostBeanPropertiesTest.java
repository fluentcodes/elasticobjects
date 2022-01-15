package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.HostBeanProperties.*;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.F_CLASS_PATH;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HostBeanPropertiesTest {

    static HostBeanProperties createHostBeanProperty(final String key, final Object value) {
        return (HostBeanProperties) ObjectProvider.createObject(HostBeanProperties.class, value, key);
    }

    @Test
    public void findModel() {
        ModelConfig config = CONFIG_MAPS.findModel(HostBeanProperties.class.getSimpleName());
        HostBeanProperties bean = (HostBeanProperties) config.create();
        assertNotNull(bean);
    }

    @Test
    public void createHostBeanProperty_classPath() {
        HostBeanProperties bean = createHostBeanProperty(F_CLASS_PATH, "src/test/resource");
        assertEquals("src/test/resource", bean.getClassPath());
    }

    @Test
    public void createHostBeanProperty_dialect() {
        HostBeanProperties bean = createHostBeanProperty(F_DIALECT, "oracle");
        assertEquals("oracle", bean.getDialect());
    }

    @Test
    public void createHostBeanProperty_driver() {
        HostBeanProperties bean = createHostBeanProperty(F_DRIVER, "oracle");
        assertEquals("oracle", bean.getDriver());
    }

    @Test
    public void createHostBeanProperty_extension() {
        HostBeanProperties bean = createHostBeanProperty(F_EXTENSION, "file");
        assertEquals("file", bean.getExtension());
    }

    @Test
    public void createHostBeanProperty_jndi() {
        HostBeanProperties bean = createHostBeanProperty(F_JNDI, "jndi");
        assertEquals("jndi", bean.getJndi());
    }

    @Test
    public void createHostBeanProperty_password() {
        HostBeanProperties bean = createHostBeanProperty(F_PASSWORD, "password");
        assertEquals("password", bean.getPassword());
    }

    @Test
    public void createHostBeanProperty_port() {
        HostBeanProperties bean = createHostBeanProperty(F_PORT, 99);
        assertEquals(Integer.valueOf(99), bean.getPort());
    }

    @Test
    public void createHostBeanProperty_protocol() {
        HostBeanProperties bean = createHostBeanProperty(F_PROTOCOL, "protocol");
        assertEquals("protocol", bean.getProtocol());
    }

    @Test
    public void createHostBeanProperty_schema() {
        HostBeanProperties bean = createHostBeanProperty(F_SCHEMA, "schema");
        assertEquals("schema", bean.getSchema());
    }

    @Test
    public void createHostBeanProperty_url() {
        HostBeanProperties bean = createHostBeanProperty(F_URL, "url");
        assertEquals("url", bean.getUrl());
    }

    @Test
    public void createHostBeanProperty_user() {
        HostBeanProperties bean = createHostBeanProperty(F_USER, "user");
        assertEquals("user", bean.getUser());
    }

}

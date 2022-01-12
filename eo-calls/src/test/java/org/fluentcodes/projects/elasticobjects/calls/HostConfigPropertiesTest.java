package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.HostBeanProperties.*;
import static org.fluentcodes.projects.elasticobjects.calls.HostBeanPropertiesTest.createHostBeanProperty;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.F_CLASS_PATH;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HostConfigPropertiesTest {

    static HostConfigProperties createHostConfigProperty(final String key, final Object value) {
        return new HostConfigProperties(createHostBeanProperty(key, value));
    }

    @Test
    public void findModel() {
        ModelConfig config = CONFIG_MAPS.findModel(HostConfigProperties.class.getSimpleName());
        Map bean = (Map) config.create();
        assertNotNull(bean);
    }

    @Test
    public void createHostConfigProperty_classPath() {
        HostConfigProperties config = createHostConfigProperty(F_CLASS_PATH, "src/test/resource");
        assertEquals("src/test/resource", config.getClassPath());
    }

    @Test
    public void createHostConfigProperty_dialect() {
        HostConfigProperties config = createHostConfigProperty(F_DIALECT, "oracle");
        assertEquals("oracle", config.getDialect());
    }

    @Test
    public void createHostConfigProperty_driver() {
        HostConfigProperties config = createHostConfigProperty(F_DRIVER, "oracle");
        assertEquals("oracle", config.getDriver());
    }

    @Test
    public void createHostConfigProperty_extension() {
        HostConfigProperties config = createHostConfigProperty(F_EXTENSION, "file");
        assertEquals("file", config.getExtension());
    }

    @Test
    public void createHostConfigProperty_jndi() {
        HostConfigProperties config = createHostConfigProperty(F_JNDI, "jndi");
        assertEquals("jndi", config.getJndi());
    }

    @Test
    public void createHostConfigProperty_password() {
        HostConfigProperties config = createHostConfigProperty(F_PASSWORD, "password");
        assertEquals("password", config.getPassword());
    }

    @Test
    public void createHostConfigProperty_port() {
        HostConfigProperties config = createHostConfigProperty(F_PORT, 99);
        assertEquals(Integer.valueOf(99), config.getPort());
    }

    @Test
    public void createHostConfigProperty_protocol() {
        HostConfigProperties config = createHostConfigProperty(F_PROTOCOL, "protocol");
        assertEquals("protocol", config.getProtocol());
    }

    @Test
    public void createHostConfigProperty_schema() {
        HostConfigProperties config = createHostConfigProperty(F_SCHEMA, "schema");
        assertEquals("schema", config.getSchema());
    }

    @Test
    public void createHostConfigProperty_url() {
        HostConfigProperties config = createHostConfigProperty(F_URL, "url");
        assertEquals("url", config.getUrl());
    }

    @Test
    public void createHostConfigProperty_user() {
        HostConfigProperties config = createHostConfigProperty(F_USER, "user");
        assertEquals("user", config.getUser());
    }

}

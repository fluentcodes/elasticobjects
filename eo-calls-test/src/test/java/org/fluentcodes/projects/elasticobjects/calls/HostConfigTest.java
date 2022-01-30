package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitems.IConfigurationTests;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.HostBean.F_HOST_NAME;
import static org.fluentcodes.projects.elasticobjects.calls.HostBeanProperties.*;
import static org.fluentcodes.projects.elasticobjects.calls.HostBeanTest.createHostBeanField;
import static org.fluentcodes.projects.elasticobjects.calls.HostBeanTest.createHostBeanProperty;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.F_CLASS_PATH;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;

public class HostConfigTest implements IConfigurationTests {

    static HostConfig createHostConfigProperty(final String key, final Object value) {
        return new HostConfig(createHostBeanProperty(key, value), CONFIG_MAPS);
    }

    static HostConfig createHostConfigField(final String key, final Object value) {
        return new HostConfig(createHostBeanField(key, value), CONFIG_MAPS);
    }

    @Override
    public Class<?> getModelConfigClass() {
        return HostConfig.class;
    }

    @Test
    public void createThrowsEoException() {
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
    public void compareConfigurations() {
        assertLoadedConfigurationsEqualsPersisted();
    }

    @Test
    public void elasticobjects__compare__xpected() {
        assertConfigBeanEqualsPersisted("elasticobjects");
    }

    @Test
    public void dummyftp__compare__xpected() {
        assertConfigBeanEqualsPersisted("dummyftp");
    }

    @Test
    public void dummyftpurl__compare__xpected() {
        assertConfigBeanEqualsPersisted("dummyftpurl");
    }

    @Test
    public void createHostConfigField_hostName() {
        HostConfig config = createHostConfigField(F_HOST_NAME, "local");
        assertEquals("local", config.getHostName());
    }

    @Test
    public void createHostConfigProperty_classPath() {
        HostConfig config = createHostConfigProperty(F_CLASS_PATH, "src/test/resource");
        assertEquals("src/test/resource", config.getProperties().getClassPath());
    }

    @Test
    public void createHostConfigProperty_dialect() {
        HostConfig config = createHostConfigProperty(F_DIALECT, "oracle");
        assertEquals("oracle", config.getProperties().getDialect());
    }

    @Test
    public void createHostConfigProperty_driver() {
        HostConfig config = createHostConfigProperty(F_DRIVER, "oracle");
        assertEquals("oracle", config.getProperties().getDriver());
    }

    @Test
    public void createHostConfigProperty_extension() {
        HostConfig config = createHostConfigProperty(F_EXTENSION, "file");
        assertEquals("file", config.getProperties().getExtension());
    }

    @Test
    public void createHostConfigProperty_jndi() {
        HostConfig config = createHostConfigProperty(F_JNDI, "jndi");
        assertEquals("jndi", config.getProperties().getJndi());
    }

    @Test
    public void createHostConfigProperty_password() {
        HostConfig config = createHostConfigProperty(F_PASSWORD, "password");
        assertEquals("password", config.getProperties().getPassword());
    }

    @Test
    public void createHostConfigProperty_port() {
        HostConfig config = createHostConfigProperty(F_PORT, 99);
        assertEquals(Integer.valueOf(99), config.getProperties().getPort());
    }

    @Test
    public void createHostConfigProperty_protocol() {
        HostConfig config = createHostConfigProperty(F_PROTOCOL, "protocol");
        assertEquals("protocol", config.getProperties().getProtocol());
    }

    @Test
    public void createHostConfigProperty_schema() {
        HostConfig config = createHostConfigProperty(F_SCHEMA, "schema");
        assertEquals("schema", config.getProperties().getSchema());
    }

    @Test
    public void createHostConfigProperty_url() {
        HostConfig config = createHostConfigProperty(F_URL, "url");
        assertEquals("url", config.getProperties().getUrl());
    }

    @Test
    public void createHostConfigProperty_user() {
        HostConfig config = createHostConfigProperty(F_USER, "user");
        assertEquals("user", config.getProperties().getUser());
    }

}

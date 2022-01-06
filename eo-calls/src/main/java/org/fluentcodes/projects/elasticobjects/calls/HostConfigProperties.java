package org.fluentcodes.projects.elasticobjects.calls;

/**
 * Basic host definition for file or db cache.
 *
 * @author null
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 12:17:41 CET 2021
 */
public class HostConfigProperties implements HostPropertiesInterface {
    private final String classPath;

    private final String dialect;
    private final String driver;
    private final String extension;
    private final String jndi;

    private final String password;
    private final Integer port;
    private final String protocol;
    private final String schema;
    private final String url;
    private final String user;



    public HostConfigProperties(HostBeanProperties bean) {
        this.classPath = bean.getClassPath();
        this.dialect = bean.getDialect();
        this.driver = bean.getDriver();
        this.extension = bean.getExtension();
        this.jndi = bean.getJndi();

        this.password = bean.getPassword();
        this.port = bean.getPort();
        this.protocol = bean.getProtocol();
        this.schema = bean.getSchema();
        this.url = bean.getUrl();
        this.user = bean.getUser();

    }

    @Override
    public String getClassPath() {
        return classPath;
    }

    @Override
    public String getDialect() {
        return dialect;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public String getJndi() {
        return jndi;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

}

package org.fluentcodes.projects.elasticobjects.calls;

/**
 * Basic host definition for file or db cache.
 *
 * @author null
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 12:17:41 CET 2021
 */
public class HostConfigProperties implements HostPropertiesInterface {

    private final String password;
    private final Integer port;
    private final String protocol;
    private final String url;
    private final String user;
    private final String classPath;

    public HostConfigProperties(HostBeanProperties bean) {
        this.password = bean.getPassword();
        this.port = bean.getPort();
        this.protocol = bean.getProtocol();
        this.url = bean.getUrl();
        this.user = bean.getUser();
        this.classPath = bean.getClassPath();
    }

    @Override
    public String getClassPath() {
        return classPath;
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
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }
}

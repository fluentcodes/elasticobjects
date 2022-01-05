package org.fluentcodes.projects.elasticobjects.calls;

/*.{javaHeader}|*/

import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerInteger;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.Map;

/**
 * Basic host definition for file or db cache.
 *
 * @author null
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 12:17:41 CET 2021
 */
public class HostBeanProperties implements HostPropertiesInterface {
    public static final String PASSWORD = "password";
    public static final String PORT = "port";
    public static final String PROTOCOL = "protocol";
    public static final String URL = "url";
    public static final String USER = "user";

    private String password;
    private Integer port;
    private String protocol;
    private String url;
    private String user;
    private String classPath;
    public HostBeanProperties() {
    }

    public HostBeanProperties(HostConfigProperties config) {
        this.password = config.getPassword();
        this.port = config.getPort();
        this.protocol = config.getProtocol();
        this.url = config.getUrl();
        this.user = config.getUser();
        this.classPath = config.getClassPath();
    }

    @Override
    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

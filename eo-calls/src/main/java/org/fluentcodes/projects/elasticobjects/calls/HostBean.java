package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerInteger;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.Map;
/**
 * 
 * A bean container class for host values to create {@link HostConfig}
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:24:13 CET 2021
 */
public class HostBean extends PermissionBean implements HostInterface{

    private static final String F_HOST_NAME = "hostName";
    private String hostName;
    private HostBeanProperties properties;
    public HostBean() {
        super();
        properties = new HostBeanProperties();
        defaultConfigModelKey();
    }

    public HostBean(final HostConfig config) {
        super(config);
        this.properties = new HostBeanProperties(config.getProperties());
        setHostName(getHostName());
    }

    public HostBeanProperties getProperties() {
        return properties;
    }

    public void setProperties(HostBeanProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(HostConfig.class.getSimpleName());
    }

    @Override
    public String toString() {
        return getNaturalId() + " -> " + properties.getUrl();
    }
}

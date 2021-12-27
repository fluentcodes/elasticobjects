package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerInteger;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.Map;
/*.{javaHeader}|*/
/**
 * 
 * A bean container class for Field values 
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:24:13 CET 2021
 */
public class HostBean extends PermissionBean implements HostInterface{
/*.{}.*/

    public HostBean() {
        super();
        defaultConfigModelKey();
    }

    public HostBean(final Map<String, Object> map) {
        super(map);
        defaultConfigModelKey();
    }

    @Override
    public String getUrlCache() {
        return "";
    }

    
    /*.{javaAccessors}|*/
    @Override
    public String getHostName(){
        return (String) getProperties().get(HOST_NAME);
    }

    @Override
    public boolean hasHostName() {
        return getProperties().containsKey(HOST_NAME) && getProperties().get(HOST_NAME) != null;
    }

    public HostBean setHostName(String value) {
        getProperties().put(HOST_NAME, value);
        return this;
    }

    private void mergeHostName(final Object value) {
        if (value == null) return;
        if (hasHostName()) return;
        setHostName(new ShapeTypeSerializerString().asObject(value));
    }

    @Override
    public String getPassword() {
        return (String) getProperties().get(PASSWORD);
    }

    @Override
    public boolean hasPassword() {
        return getProperties().containsKey(PASSWORD) && getProperties().get(PASSWORD) != null;
    }

    public HostBean setPassword(String value) {
        getProperties().put(PASSWORD, value);
        return this;
    }
    private void mergePassword(final Object value) {
        if (value == null) return;
        if (hasPassword()) return;
        setPassword(new ShapeTypeSerializerString().asObject(value));
    }

    @Override
    public Integer getPort() {
        return (Integer) getProperties().get(PORT);
    }

    @Override
    public boolean hasPort() {
        return getProperties().containsKey(PORT) && getProperties().get(PORT) != null;
    }

    public HostBean setPort(Integer value) {
        getProperties().put(PORT, value);
        return this;
    }
    private void mergePort(final Object value) {
        if (value == null) return;
        if (hasPort()) return;
        setPort(new ShapeTypeSerializerInteger().asObject(value));
    }

    @Override
    public String getProtocol() {
        return (String) getProperties().get(PROTOCOL);
    }

    @Override
    public boolean hasProtocol() {
        return getProperties().containsKey(PROTOCOL) && getProperties().get(PROTOCOL) != null;
    }

    public HostBean setProtocol(String value) {
        getProperties().put(PROTOCOL, value);
        return this;
    }
    private void mergeProtocol(final Object value) {
        if (value == null) return;
        if (hasProtocol()) return;
        setProtocol(new ShapeTypeSerializerString().asObject(value));
    }

    @Override
    public String getUrl() {
        return (String) getProperties().get(URL);
    }

    @Override
    public boolean hasUrl() {
        return getProperties().containsKey(URL) && getProperties().get(URL) != null;
    }


    public HostBean setUrl(String value) {
        getProperties().put(URL, value);
        return this;
    }
    private void mergeUrl(final Object value) {
        if (value == null) return;
        if (hasUrl()) return;
        setUrl(new ShapeTypeSerializerString().asObject(value));
    }

    @Override
    public String getUser() {
        return (String) getProperties().get(USER);
    }

    @Override
    public boolean hasUser() {
        return getProperties().containsKey(USER) && getProperties().get(USER) != null;
    }

    public HostBean setUser(String value) {
        getProperties().put(USER, value);
        return this;
    }
    private void mergeUser(final Object value) {
        if (value == null) return;
        if (hasUser()) return;
        setUser(new ShapeTypeSerializerString().asObject(value));
    }


    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(HostConfig.class.getSimpleName());
    }

    @Override
    public String toString() {
        return getNaturalId() + " -> " + getUrl();
    }
}

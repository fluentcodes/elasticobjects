package org.fluentcodes.projects.elasticobjects.calls;

/**
 * Methods for {@link HostConfigProperties} and {@link HostBeanProperties}.
 *
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 4 12:15:25 CET 2022
 */
public interface HostPropertiesInterface {

    String getClassPath();

    default boolean hasClassPath() {
        return getClassPath() != null && !getClassPath().isEmpty();
    }

    String getDialect();

    default boolean hasDialect() {
        return getDialect() != null && !getDialect().isEmpty();
    }

    String getDriver();

    default boolean hasDriver() {
        return getDriver() != null && !getDriver().isEmpty();
    }

    String getExtension();

    default boolean hasExtension() {
        return getExtension() != null && !getExtension().isEmpty();
    }

    String getJndi();

    default boolean hasJndi() {
        return getJndi() != null && !getJndi().isEmpty();
    }

    String getPassword();

    default boolean hasPassword() {
        return getPassword() != null && !getPassword().isEmpty();
    }

    Integer getPort();

    default boolean hasPort() {
        return getPort() != null;
    }

    String getProtocol();

    default boolean hasProtocol() {
        return getProtocol() != null && !getProtocol().isEmpty();
    }

    String getSchema();

    default boolean hasSchema() {
        return getSchema() != null && !getSchema().isEmpty();
    }

    String getUrl();

    default boolean hasUrl() {
        return getUrl() != null && !getUrl().isEmpty();
    }

    String getUser();

    default boolean hasUser() {
        return getUser() != null && !getUser().isEmpty();
    }
}

package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
/*.{javaHeader}|*/

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
        return getClassPath() != null && ! getClassPath().isEmpty();
    }

    String getPassword();

    default boolean hasPassword() {
        return getPassword() != null && ! getPassword().isEmpty();
    }

    Integer getPort();

    default boolean hasPort() {
        return getPort() != null;
    }

    String getProtocol();

    default boolean hasProtocol() {
        return getProtocol() != null && !getProtocol().isEmpty();
    }

    String getUrl();

    default boolean hasUrl() {
        return getUrl() != null && !getUrl().isEmpty();
    }

    String getUser();

    default boolean hasUser() {
        return getUser() != null && !getUser().isEmpty();
    }
    /*.{}.*/

}

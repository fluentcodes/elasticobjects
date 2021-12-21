package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
/*.{javaHeader}|*/

/**
 * Methods to get the properties schema from properties map.
 *
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 12:15:25 CET 2021
 */
public interface HostInterface extends ConfigInterface {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    String HOST_NAME = "hostName";
    String PASSWORD = "password";
    String PORT = "port";
    String PROTOCOL = "protocol";
    String URL = "url";
    String USER = "user";

    /*.{}.*/
    String getUrlCache();

    /*.{javaAccessors}|*/
    String getHostName();

    default boolean hasHostName() {
        return getHostName() != null && !getHostName().isEmpty();
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

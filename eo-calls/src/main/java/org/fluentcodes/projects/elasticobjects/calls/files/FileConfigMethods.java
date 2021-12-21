package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;

import java.net.URL;
/*.{javaHeader}|*/

/**
 * Access methods for field properties map.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 14:46:34 CET 2021
 */
public interface FileConfigMethods {
    /*.{}.*/
    String getCachedContent();

    void setCachedContent(String cachedContent);

    String getUrlPath(HostConfig hostConfig);

    URL findUrl(HostConfig hostConfig);

    URL getUrl(HostConfig hostConfig);

    URL createUrl(HostConfig hostConfig);

}

package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
/**
 * Methods for {@link HostConfig} and {@link HostBean}.
 *
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 12:15:25 CET 2021
 */
public interface HostInterface {
    String getHostName();

    default boolean hasHostName() {
        return getHostName() != null && !getHostName().isEmpty();
    }


}

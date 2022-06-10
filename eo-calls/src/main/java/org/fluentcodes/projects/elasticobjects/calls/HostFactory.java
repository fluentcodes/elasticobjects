package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/**
 * Created by Werner on 1.11.2021.
 */

public class HostFactory extends ConfigFactory< HostBean, HostConfig> {
    public HostFactory(final ConfigMaps configMaps) {
        super(configMaps, HostBean.class, HostConfig.class);
    }
}

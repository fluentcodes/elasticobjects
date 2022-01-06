package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/**
 * Created by Werner on 31.10.2021.
 */

public class FileFactory extends ConfigFactory< FileBean, FileConfig> {
    public FileFactory(final ConfigMaps configMaps) {
        super(configMaps, FileBean.class, FileConfig.class);
    }
}

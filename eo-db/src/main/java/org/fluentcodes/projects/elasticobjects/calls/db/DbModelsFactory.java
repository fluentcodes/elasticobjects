package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/**
 */

public class DbModelsFactory extends ConfigFactory<DbModelsBean, DbModelsConfig> {
    public DbModelsFactory(final ConfigMaps configMaps) {
        super(configMaps, DbModelsBean.class, DbModelsConfig.class);
    }
}

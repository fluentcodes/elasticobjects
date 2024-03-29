package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/**
 */

public class DbModelFactory extends ConfigFactory< DbSqlBean, DbSqlConfig> {
    public DbModelFactory(final ConfigMaps configMaps) {
        super(configMaps, DbModelBean.class, DbModelConfig.class);
    }
}

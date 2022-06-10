package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/**
 * Created by Werner on 21.1.2021.
 */

public class DbSqlFactory extends ConfigFactory< DbSqlBean, DbSqlConfig> {
    public DbSqlFactory(final ConfigMaps configMaps) {
        super(configMaps, DbSqlBean.class, DbSqlConfig.class);
    }
}

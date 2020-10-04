package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbSqlConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(DbSqlConfig.class);
    }

    // TODO check within 0.5.0-SNAPSHO for mvn test fails.
    @Ignore
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbSqlConfig.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(DbSqlConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(DbSqlConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(DbSqlConfig.class);
    }
}


package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 11.12.2020.
 */
public class DbSqlBean extends PermissionBean implements DbSqlInterface {
    public static final String SQL_LIST = "sqlList";
    public static final String DEFAULT_HOST_CONFIG_KEY = "defaultHostConfigKey";
    private List<String> sqlList;

    public DbSqlBean() {
        super();
        defaultConfigModelKey();
    }

    public DbSqlBean(final String naturalId, final Map<String, Object> map) {
        super(naturalId, map);
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(DbSqlConfig.class.getSimpleName());
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }


    @Override
    public String toString() {
        return getNaturalId() + " -> ";
    }

    private void mergeSqlList(final Object value) {
        if (value == null) {
            return;
        }
        if (hasSqlList()) {
            return;
        }
        if (value instanceof List) {
            setSqlList((List)value);
        }
    }
}

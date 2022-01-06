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
    private String modelKey;
    private String dbConfigKey;
    private String classPath;
    private List<String> sqlList;

    public DbSqlBean() {
        super();
        defaultConfigModelKey();
    }

    public DbSqlBean(final DbSqlConfig config) {
        super(config);
        this.dbConfigKey = config.getDbConfigKey();
        this.modelKey = config.getModelKey();
        this.classPath = config.getClassPath();
        this.sqlList = config.getSqlList();
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(DbSqlConfig.class.getSimpleName());
    }

    @Override
    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    @Override
    public String getDbConfigKey() {
        return dbConfigKey;
    }

    public void setDbConfigKey(String dbConfigKey) {
        this.dbConfigKey = dbConfigKey;
    }

    @Override
    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @Override
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

}

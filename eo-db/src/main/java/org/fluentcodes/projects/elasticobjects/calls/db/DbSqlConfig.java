package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.util.List;


/**
 * Created by Werner on 05.04.2022.
 */
public class DbSqlConfig extends PermissionConfig implements DbSqlInterface {
    private final String modelKey;
    private final String dbConfigKey;
    private final String classPath;
    private final List<String> sqlList;

    public DbSqlConfig(final ConfigBean configBean, final ConfigMaps configMaps) {
        this((DbSqlBean)configBean, configMaps);
    }

    public DbSqlConfig(final DbSqlBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        this.dbConfigKey = bean.getDbConfigKey();
        this.modelKey = bean.getModelKey();
        this.classPath = bean.getClassPath();
        sqlList = bean.getSqlList();
    }

    @Override
    public String getModelKey() {
        return modelKey;
    }

    @Override
    public String getDbConfigKey() {
        return dbConfigKey;
    }

    @Override
    public String getClassPath() {
        return classPath;
    }

    @Override
    public List<String> getSqlList() {
        return sqlList;
    }
}

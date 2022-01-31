package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;

import java.util.List;


/**
 * Created by Werner on 05.01.2022.
 */
public class DbModelsBean extends PermissionBean implements DbModelsInterface {
    private List<String> dbModelKeys;
    private String dbConfigKey;

    public DbModelsBean() {}

    public DbModelsBean(final DbModelsConfig config) {
        super(config);
        this.dbModelKeys = config.getDbModelKeys();
        this.dbConfigKey = config.getDbConfigKey();
    }
    @Override
    public String getConfigModelKey() {
        return hasConfigModelKey()?
                super.getConfigModelKey():
                DbModelsConfig.class.getSimpleName();
    }

    @Override
    public List<String> getDbModelKeys() {
        return dbModelKeys;
    }

    public void setDbModelKeys(List<String> dbModelKeys) {
        this.dbModelKeys = dbModelKeys;
    }

    @Override
    public String getDbConfigKey() {
        return dbConfigKey;
    }

    public void setDbConfigKey(String dbConfigKey) {
        this.dbConfigKey = dbConfigKey;
    }
}

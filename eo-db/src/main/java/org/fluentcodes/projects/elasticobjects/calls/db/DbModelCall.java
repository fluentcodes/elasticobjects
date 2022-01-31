package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;

/**
 * Abstract call class for model based {@link DbModelsConfig} database operations.
 */
public abstract class DbModelCall extends CallImpl {
    private String configKey;

    protected DbModelCall() {
        super();
    }

    protected DbModelCall(final String configKey) {
        this.configKey = configKey;
    }
    protected DbModelCall(final String configKey, final String targetPath) {
        this.configKey = configKey;
        setTargetPath(targetPath);
    }

    protected DbModelsConfig init(final PermissionType permissionType, final EOInterfaceScalar eo) {
        DbModelsConfig config = (DbModelsConfig) eo.getConfigMaps().find(DbModelsConfig.class, configKey);
        config.hasPermissions(permissionType, eo.getRoles());
        config.getDbConfig().hasPermissions(permissionType, eo.getRoles());
        return config;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
}

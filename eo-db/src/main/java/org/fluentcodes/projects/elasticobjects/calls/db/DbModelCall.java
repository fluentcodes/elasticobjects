package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;

/**
 * Abstract call class for model based {@link DbModelConfig} database operations.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 06:19:26 CET 2020
 */
public abstract class DbModelCall extends CallImpl {
    private String configKey;

    protected DbModelCall() {
        super();
    }

    protected DbModelCall(final String configKey) {
        this.configKey = configKey;
    }

    protected DbModelConfig init(final PermissionType permissionType, final EOInterfaceScalar eo) {
        DbModelConfig config = (DbModelConfig) eo.getConfigMaps().find(DbModelConfig.class, configKey);
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

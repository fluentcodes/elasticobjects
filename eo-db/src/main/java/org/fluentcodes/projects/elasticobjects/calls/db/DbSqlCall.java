package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.sql.Connection;

/*.{javaHeader}|*/

/**
 * Abstract class providing an sql config before executing.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 06:55:21 CET 2020
 */
public abstract class DbSqlCall extends HostCall {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String SQL_KEY = "sqlKey";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private String sqlKey;
    /*.{}.*/
    private DbSqlConfig sqlConfig;

    public DbSqlCall() {
        super();
    }

    public DbSqlCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    public DbSqlCall(final String hostConfigKey, final String sqlKey) {
        super(hostConfigKey);
        this.sqlKey = sqlKey;
    }

    public void setConfigKey(final String configKey) {
        this.sqlKey = configKey;
    }

    protected DbSqlConfig init(final PermissionType permissionType, final EOInterfaceScalar eo) {
        if (!hasSqlKey()) {
            throw new EoException("Empty key");
        }
        sqlConfig = (DbSqlConfig) eo.getConfigMaps().find(DbSqlConfig.class, sqlKey);
        sqlConfig.hasPermissions(permissionType, eo.getRoles());
        super.initHostConfig(permissionType, eo);
        return sqlConfig;
    }

    public DbConfig getDbConfig() {
        return (DbConfig) getHostConfig();
    }

    public Connection getConnection() {
        return getDbConfig().getConnection();
    }

    public DbSqlConfig getSqlConfig() {
        return sqlConfig;
    }

    /*.{javaAccessors}|*/

    /**
     * The key to select a configuration from DbSqlConfig within DbSqlCall.
     */

    public DbSqlCall setSqlKey(String sqlKey) {
        this.sqlKey = sqlKey;
        return this;
    }

    public String getSqlKey() {
        return this.sqlKey;
    }

    public boolean hasSqlKey() {
        return sqlKey != null && !sqlKey.isEmpty();
    }
    /*.{}.*/

}

package org.fluentcodes.projects.elasticobjects.calls.db;

import java.sql.Connection;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes a list of sql statements within DbSqlConfig.
 */
public class DbSqlExecuteCall extends CallImpl implements ConfigWriteCommand {
    public static final Logger LOGGER = LoggerFactory.getLogger(DbSqlExecuteCall.class);
    String dbConfigKey;
    DbConfig dbConfig;
    String dbSqlConfigKey;
    DbSqlConfig dbSqlConfig;

    public DbSqlExecuteCall() {
        super();
    }

    public DbSqlExecuteCall(final String dbConfigKey) {
        this();
        this.dbConfigKey = dbConfigKey;
    }

    public DbSqlExecuteCall(final String dbConfigKey, final String dbSqlConfigKey) {
        this(dbConfigKey);
        this.dbSqlConfigKey = dbSqlConfigKey;
    }

    @Override
    public String execute(final EOInterfaceScalar eo) {
        if (eo == null) {
            throw new EoException("Null or empty EO. But checkConfig needs values to be readed from the db!");
        }
        if(!hasDbSqlConfigKey()) {
            throw new EoException("No dbSqlConfigKey defined");
        }
        if(!hasDbConfigKey()) {
            throw new EoException("No dbConfigKey defined");
        }
        dbConfig = (DbConfig) eo.getConfigMaps().find(HostConfig.class, dbConfigKey);
        dbConfig.hasPermissions(PermissionType.READ, eo.getRoles());
        dbSqlConfig = (DbSqlConfig) eo.getConfigMaps().find(DbSqlConfig.class, dbSqlConfigKey);
        dbSqlConfig.hasPermissions(PermissionType.READ, eo.getRoles());

        return executeSql(eo);
    }

    private String executeSql(final EOInterfaceScalar eo) {
        List<String> sqlList = dbSqlConfig.getSqlList();
        Boolean executed = false;
        for (String sql : sqlList) {
            LOGGER.info("Execute: " + sql);
            Statement statement = null;
            try {
                eo.info("Executed " + sql);
                Connection connection = dbConfig.getConnection();
                statement = connection.createStatement();
                executed = statement.execute(sql) && executed;

            } catch (SQLException e) {
                //PreparedStatementValues.closeStatement(statement);
                DbConfig.closeStatement(statement);
                throw new EoException("Problem execute " + e.getMessage());
            } finally {
                DbConfig.closeStatement(statement);
            }
        }
        eo.info("Excecuted " + sqlList.size() + " statements without error.");
        return executed.toString();
    }

    @Override
    public void setConfigKey(String configKey) {
        this.dbConfigKey = configKey;
    }

    @Override
    public void setByParameter(String values) {
        ConfigWriteCommand.super.setByParameter(values);
    }

    public String getDbSqlConfigKey() {
        return dbSqlConfigKey;
    }

    public DbSqlExecuteCall setDbSqlConfigKey(String dbSqlConfigKey) {
        this.dbSqlConfigKey = dbSqlConfigKey;
        return this;
    }

    public boolean hasDbSqlConfigKey() {
        return dbSqlConfigKey != null && !dbSqlConfigKey.isEmpty();
    }

    public String getDbConfigKey() {
        return dbConfigKey;
    }

    public DbSqlExecuteCall setDbConfigKey(String dbConfigKey) {
        this.dbConfigKey = dbConfigKey;
        return this;
    }

    public boolean hasDbConfigKey() {
        return dbConfigKey != null && !dbConfigKey.isEmpty();
    }
}

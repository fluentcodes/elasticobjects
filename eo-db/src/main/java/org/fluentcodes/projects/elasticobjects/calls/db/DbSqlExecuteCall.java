package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/*.{javaHeader}|*/

/**
 * Executes a list of sql statements within DbSqlConfig.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 07:16:55 CET 2020
 */
public class DbSqlExecuteCall extends DbSqlCall implements ConfigWriteCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /*.{}.*/
    public DbSqlExecuteCall() {
        super();
    }

    public DbSqlExecuteCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    public DbSqlExecuteCall(final String hostConfigKey, final String sqlConfigKey) {
        super(hostConfigKey, sqlConfigKey);
    }


    public Boolean execute(final EOInterfaceScalar eo) {
        return executeSql(eo);
    }

    public boolean executeSql(final EOInterfaceScalar eo) {

        if (eo == null) {
            throw new EoException("Null or empty EO. But checkConfig needs values to be readed from the db!");
        }
        DbSqlConfig config = init(PermissionType.WRITE, eo);
        boolean executed = true;
        List<String> sqlList = getSqlConfig().getSqlList();
        for (String sql : sqlList) {
            Statement statement = null;
            try {
                statement = getConnection().createStatement();
                executed = statement.execute(sql) && executed;
            } catch (SQLException e) {
                //PreparedStatementValues.closeStatement(statement);
                DbConfig.closeStatement(statement);
                throw new EoException("Problem execute " + e.getMessage());
            } finally {
                DbConfig.closeStatement(statement);
            }
        }
        return executed;
    }
    /*.{javaAccessors}|*/
    /*.{}.*/

}

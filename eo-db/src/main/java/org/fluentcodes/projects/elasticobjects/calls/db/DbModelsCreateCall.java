package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;

/**
 * Remove an entry from database by creating a delete sql from entry in sourcePath.
 */
public class DbModelsCreateCall extends DbModelCall implements ConfigWriteCommand {
    private Boolean execute = false;
    public DbModelsCreateCall() {
        super();
    }

    public DbModelsCreateCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    public Boolean getExecute() {
        return execute;
    }

    public void setExecute(Boolean execute) {
        this.execute = execute;
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        return create(eo);
    }

    public Object create(final EOInterfaceScalar eo) {
        DbModelsConfig config = init(PermissionType.CREATE, eo);
        StringBuilder feedback = new StringBuilder("CREATE DATABASE: \n");
        // drop statements
        for (Class<?> modelClass: config.getDbModelClassSet()) {
            DbModelConfig dbModelConfig = config.getDbModelConfig(modelClass);
            StatementPreparedValues statement = new StatementPreparedValues(
                StatementPreparedValues.SqlType.CREATE,
                    dbModelConfig.getDropStatement()
            );
            if (execute) {
                int executeResponse = statement.execute(config.getDbConfig().getConnection());
                feedback.append(executeResponse);
            }
            feedback.append(dbModelConfig.getDropStatement());
            feedback.append(": ");

            feedback.append("\n");

        }
        // start statements
        for (Class<?> modelClass: config.getDbModelClassSet()) {
            DbModelConfig dbModelConfig = config.getDbModelConfig(modelClass);
            StatementPreparedValues statement = new StatementPreparedValues(
                    StatementPreparedValues.SqlType.CREATE,
                    dbModelConfig.getCreateStatement()
            );
            feedback.append(dbModelConfig.getCreateStatement());
            feedback.append(": ");
            if (execute) {
                int executeResponse = statement.execute(config.getDbConfig().getConnection());
                feedback.append(executeResponse);
            }
            feedback.append("\n");
            for (String unique: dbModelConfig.getUniqueList()) {
                StatementPreparedValues uniqueStatement = new StatementPreparedValues(
                        StatementPreparedValues.SqlType.CREATE,
                        unique
                );
                if (execute) {
                    int uniqueResponse = uniqueStatement.execute(config.getDbConfig().getConnection());

                    feedback.append(unique);
                    feedback.append(": ");
                    feedback.append(uniqueResponse);
                    feedback.append("\n");
                }
                else {
                    feedback.append(unique + "\n");
                }
            }
        }
        for (Class<?> modelClass: config.getDbModelClassSet()) {
            DbModelConfig dbModelConfig = config.getDbModelConfig(modelClass);
            for (String constraint: dbModelConfig.getForeignKeys()) {
                StatementPreparedValues statement = new StatementPreparedValues(
                        StatementPreparedValues.SqlType.CREATE,
                        constraint
                );
                if (execute) {
                    int executeResponse = statement.execute(config.getDbConfig().getConnection());
                    feedback.append("Constraint ");
                    feedback.append(constraint);
                    feedback.append(": ");
                    feedback.append(executeResponse);
                    feedback.append("\n");
                }
                else {
                    feedback.append("Constraint ");
                    feedback.append(constraint + "\n");
                }
            }
        }
        if (hasTargetPath()) {
            eo.set(feedback.toString(), getTargetPath());
        }
        return feedback.toString();
    }
}

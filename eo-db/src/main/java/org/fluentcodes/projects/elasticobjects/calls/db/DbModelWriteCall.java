package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Write an entry in database by creating a insert or update sql from entry in sourcePath.
 * The object must be an instance of {@link DbModelsConfig}.
 */
public class DbModelWriteCall extends DbModelCall implements ConfigWriteCommand {
    public DbModelWriteCall() {
        super();
    }

    public DbModelWriteCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    public DbModelWriteCall(final String hostConfigKey, final String targetPath) {
        super(hostConfigKey, targetPath);
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        return write(eo);
    }

    public int write(final EOInterfaceScalar eo) {
        if (!(eo instanceof EoChild)) {
            throw new EoException("eo not instance of EoChild");
        }

        DbModelsConfig config = init(PermissionType.WRITE, eo);
        if (!config.hasDbModelConfig(eo.getModelClass())) {
            throw new EoException("No db equivalent found for '" + eo.getModelClass() + "'!");
        }
        int updateCount = 0;
        DbModelConfig dbModelConfig = config.getDbModelConfig(eo.getModelClass());
        ModelConfig modelConfig = dbModelConfig.getModelConfig();

        StatementFind findStatement = dbModelConfig.createFindStatement((EoChild) eo);
        Object dbObject = findStatement.readOneOrEmpty(config.getDbConfig().getConnection(), config.getConfigMaps());

        for (String key: modelConfig.getFieldKeys()) {
            if (!eo.hasEo(key)) {
                continue;
            }
            if (eo.isScalar()) {
                continue;
            }
            FieldConfig fieldConfig = eo.getModel().getField(key);
            if (!fieldConfig.getProperties().hasIdKey()) {
                continue;
            }
            new DbModelWriteCall(getConfigKey()).execute( eo.getEo(key));
        }

        if (dbObject != null) {
            StatementPreparedValues statement = dbModelConfig.createUpdateStatement((EoChild) eo);
            updateCount = statement
                    .execute(config.getDbConfig().getConnection());
        } else {
            StatementPreparedValues statement = dbModelConfig.createInsertStatement((EoChild) eo);
            updateCount = statement
                    .execute(config.getDbConfig().getConnection());
        }
        dbObject = findStatement.readOneOrEmpty(config.getDbConfig().getConnection(), config.getConfigMaps());
        if (dbObject == null) {
            throw new EoException("Could not find persisted entry in db! " + findStatement);
        }
        eo.map(dbObject);
        return updateCount;
    }
}

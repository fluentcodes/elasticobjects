package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;

/**
 * Resolve ids to objects
 */
public class DbModelResolveCall extends DbModelCall implements ConfigWriteCommand {
    public DbModelResolveCall() {
        super();
    }

    public DbModelResolveCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    public DbModelResolveCall(final String hostConfigKey, final String targetPath) {
        super(hostConfigKey, targetPath);
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        return resolve(eo);
    }

    public String resolve(final EOInterfaceScalar eo) {
        if (!(eo instanceof EoChild)) {
            throw new EoException("eo not instance of EoChild");
        }

        DbModelsConfig config = init(PermissionType.READ, eo);
        if (!config.hasDbModelConfig(eo.getModelClass())) {
            throw new EoException("No db equivalent found for '" + eo.getModelClass() + "'!");
        }
        int updateCount = 0;
        DbModelConfig dbModelConfig = config.getDbModelConfig(eo.getModelClass());
        ModelConfig modelConfig = dbModelConfig.getModelConfig();
        StatementFind findStatement = dbModelConfig.createFindStatement((EoChild) eo);
        Object result = findStatement
                .readFirst(
                        config.getDbConfig().getConnection(),
                        eo.getConfigMaps());
        if (result == null) {
            throw new EoException("Could not find persisted entry in db!");
        }
        for (String key: modelConfig.getFieldKeys()) {
            FieldConfig fieldConfig = eo.getModel().getField(key);
            if (fieldConfig.getProperties().hasIdKey()) {
                String idKey = fieldConfig.getProperties().getIdKey();
                if (!eo.hasEo(idKey)) {
                    continue;
                }
                Long id = (Long) eo.get(idKey);
                Models fieldModels = fieldConfig.getModels();
                Object value = fieldModels.create();
                EOInterfaceScalar eoResolved = eo.set(value, key);
                eoResolved.set(id, F_ID);
                DbModelConfig fieldModelConfig = config.getDbModelConfig(fieldModels.getModelClass());
                StatementFind findFieldStatement = fieldModelConfig.createFindStatement((EoChild) eoResolved);
                Object subResult = findFieldStatement.readFirst(config.getDbConfig().getConnection(), eoResolved.getConfigMaps());
                if (subResult != null) {
                    eoResolved.map(subResult);
                }
            }
        }
        return "";
    }
}

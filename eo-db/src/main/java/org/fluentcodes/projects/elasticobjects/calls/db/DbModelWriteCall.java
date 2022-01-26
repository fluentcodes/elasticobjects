package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;

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
        if (!(eo instanceof EoChild)) {
            throw new EoException("eo not instance of EoChild");
        }
        return write((EoChild)eo);
    }

    public int write(final EoChild eo) {
        DbModelsConfig config = init(PermissionType.WRITE, eo);
        if (!config.hasDbModelConfig(eo.getModelClass())) {
            throw new EoException("No db equivalent found for '" + eo.getModelClass() + "'!");
        }
        int updateCount = 0;
        DbModelConfig dbModelConfig = config.getDbModelConfig(eo.getModelClass());
        ModelConfig modelConfig = dbModelConfig.getModelConfig();

        boolean parentSet = false;

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
            if (!parentSet) {
                Object dbObject = dbModelConfig.write(config.getDbConfig().getConnection(), eo);
                eo.map(dbObject);
                parentSet = true;
            }

            new DbModelWriteCall(getConfigKey()).execute(eo.getEo(key));

            final String childIdKey = fieldConfig.getProperties().getIdKey();
            Object childIdValue = eo.get(key, F_ID);
            eo.set(childIdValue, childIdKey);
        }
        Object dbObject = dbModelConfig.write(config.getDbConfig().getConnection(), eo);
        eo.map(dbObject);
        return updateCount;
    }
}

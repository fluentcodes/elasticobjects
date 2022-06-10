package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoChildValue;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
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

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        if (!(eo instanceof EoChild)) {
            throw new EoException("eo not instance of EoChild");
        }
        if (eo.isList()||eo.isMap()) {
            return writeList((EoChild) eo);
        }
        else {
            return write((EoChild) eo);
        }
    }

    public int writeList(final EoChild eo) {
        int counter = 0;
        for (String key: eo.keys()) {
             counter += write((EoChild) eo.getEo(key));
        }
        return counter;
    }

    public int write(final EoChild eo) {
        DbModelsConfig config = init(PermissionType.WRITE, eo);
        if (!config.hasDbModelConfig(eo.getModelClass())) {
            throw new EoException("No db equivalent found for '" + eo.getModelClass() + "'!");
        }
        if (hasCondition()) {
            Or or = new Or(getCondition());
            if (!or.filter(eo)) {
                return 0;
            }
        }
        int updateCount = 0;
        DbModelConfig dbModelConfig = config.getDbModelConfig(eo.getModelClass());
        ModelConfig modelConfig = dbModelConfig.getModelConfig();

        boolean parentSet = false;
        // Check for sub objects to be persisted recursively
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

            EOInterfaceScalar eoEntry = eo.getEo(key);

            new DbModelWriteCall(getConfigKey()).execute(eoEntry);

            final String childIdKey = fieldConfig.getProperties().getIdKey();
            Object childIdValue = eo.get(key, F_ID);
            eo.set(childIdValue, childIdKey);
        }
        Object dbObject = dbModelConfig.write(config.getDbConfig().getConnection(), eo);
        eo.map(dbObject);
        return updateCount;
    }
}

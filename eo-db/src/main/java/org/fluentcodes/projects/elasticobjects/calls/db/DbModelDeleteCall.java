package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/**
 * Remove an entry from database by creating a delete sql from entry in sourcePath.
 */
public class DbModelDeleteCall extends DbModelCall implements ConfigWriteCommand {

    public DbModelDeleteCall() {
        super();
    }

    public DbModelDeleteCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    public DbModelDeleteCall(final String hostConfigKey, final String targetPath) {
        super(hostConfigKey, targetPath);
    }
    @Override
    public Object execute(final EOInterfaceScalar eo) {
        return remove(eo);
    }

    public Object remove(final EOInterfaceScalar eo) {
        if (!(eo instanceof EoChild)) {
            throw new EoException("Could not query scalar value");
        }
        DbModelsConfig config = init(PermissionType.WRITE, eo);

            StatementFind findStatement = config.getDbModelConfig(eo.getModelClass())
                    .createFindStatement((EoChild) eo);
            Object result = findStatement
                    .readFirst(
                            config.getDbConfig().getConnection(),
                            eo.getConfigMaps());
        if (hasTargetPath()) {
            eo.set(result, getTargetPath());
        }
        StatementPreparedValues statement = config.getDbModelConfig(eo.getModelClass())
                .createDeleteStatement((EoChild)eo);
        return statement
                .execute(config.getDbConfig().getConnection());
    }
}

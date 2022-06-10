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

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        return remove(eo);
    }

    public Object remove(final EOInterfaceScalar eo) {
        if (!(eo instanceof EoChild)) {
            throw new EoException("Could not query scalar value");
        }
        DbModelsConfig config = init(PermissionType.DELETE, eo);
        DbModelConfig dbModelConfig = config.getDbModelConfig(eo.getModelClass());

        Object result = dbModelConfig.delete(config.getDbConfig().getConnection(), (EoChild) eo);

        if (hasTargetPath()) {
            eo.set(result, getTargetPath());
        }
        return "";
    }
}

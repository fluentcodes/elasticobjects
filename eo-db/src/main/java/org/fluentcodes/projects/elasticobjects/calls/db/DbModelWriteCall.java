package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.InsertStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.UpdateStatement;

import java.util.List;

/**
 * Write an entry in database by creating a insert or update sql from entry in sourcePath.
 * The object must be an instance of {@link DbModelConfig}.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 06:45:11 CET 2020
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
        return save(eo);
    }

    public int save(final EOInterfaceScalar eo) {
        DbModelConfig modelConfig = init(PermissionType.WRITE, eo);
        int updateCount = 0;
        if (FindStatement.ofId(eo).execute(modelConfig.getDbConfig().getConnection()) == 1) {
            updateCount = UpdateStatement
                    .of(eo)
                    .execute(modelConfig.getDbConfig().getConnection());
        } else {
            updateCount = InsertStatement
                    .of(eo)
                    .execute(modelConfig.getDbConfig().getConnection());
        }
        if (hasTargetPath()) {
            List result = FindStatement.of(eo)
                    .readFirst(
                            modelConfig.getDbConfig().getConnection(),
                            eo.getConfigMaps());
            eo.set(result, getTargetPath());
        }
        return updateCount;
    }
}

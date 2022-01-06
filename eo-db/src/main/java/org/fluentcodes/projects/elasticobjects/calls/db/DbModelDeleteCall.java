package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.DeleteStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/*.{javaHeader}|*/

/**
 * Remove an entry from database by creating a delete sql from entry in sourcePath.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 06:24:57 CET 2020
 */
public class DbModelDeleteCall extends DbModelCall implements ConfigWriteCommand {

    public DbModelDeleteCall() {
        super();
    }

    public DbModelDeleteCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    @Override
    public Object execute(final IEOScalar eo) {
        return remove(eo);
    }

    public Object remove(final IEOScalar eo) {
        DbModelConfig modelConfig = init(PermissionType.WRITE, eo);
        if (hasTargetPath()) {
            List<String> result = FindStatement.of(eo)
                    .readFirst(
                            modelConfig.getDbConfig().getConnection(),
                            eo.getConfigMaps());
            eo.set(result, getTargetPath());
        }

        return DeleteStatement.of(eo)
                .execute(modelConfig.getDbConfig().getConnection());
    }
}

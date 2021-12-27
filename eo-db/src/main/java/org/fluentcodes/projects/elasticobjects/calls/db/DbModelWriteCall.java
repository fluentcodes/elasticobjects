package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.InsertStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.UpdateStatement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/*.{javaHeader}|*/

/**
 * Write an entry in database by creating a insert or update sql from entry in sourcePath.
 * The object must be an instance of {@link ModelConfigDbObject}.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 06:45:11 CET 2020
 */
public class DbModelWriteCall extends DbModelCall implements ConfigWriteCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /*.{}.*/
    public DbModelWriteCall() {
        super();
    }

    public DbModelWriteCall(final String hostConfigKey) {
        super(hostConfigKey);
    }

    @Override
    public Object execute(final IEOScalar eo) {
        return save(eo);
    }

    public int save(final IEOScalar eo) {
        ModelConfigDbObject modelConfig = init(PermissionType.WRITE, eo);
        if (!modelConfig.isObject()) {
            throw new EoException("No model is provided in path '" + eo.getPathAsString() + "");
        }
        int updateCount = 0;
        if (FindStatement.ofId(eo).execute(getDbConfig().getConnection()) == 1) {
            updateCount = UpdateStatement
                    .of(eo)
                    .execute(getDbConfig().getConnection());
        } else {
            updateCount = InsertStatement
                    .of(eo)
                    .execute(getDbConfig().getConnection());
        }
        if (hasTargetPath()) {
            List result = FindStatement.of(eo)
                    .readFirst(
                            getDbConfig().getConnection(),
                            eo.getConfigMaps());
            eo.set(result, getTargetPath());
        }
        return updateCount;
    }

    /*.{javaAccessors}|*/
    /*.{}.*/
}

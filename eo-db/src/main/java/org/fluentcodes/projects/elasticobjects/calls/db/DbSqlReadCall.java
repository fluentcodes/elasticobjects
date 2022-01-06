package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBeanInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/**
 * Map results of a sql select to the targetPath.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 07:20:13 CET 2022
 */
public class DbSqlReadCall extends DbSqlCall implements ListParamsBeanInterface, ConfigReadCommand {
    private ListParamsBean listParams;

    public DbSqlReadCall() {
        super();
        listParams = new ListParamsBean();
    }

    public DbSqlReadCall(final String hostConfigKey) {
        super(hostConfigKey);
        listParams = new ListParamsBean();
    }

    public DbSqlReadCall(final String hostConfigKey, final String sqlConfigKey) {
        super(hostConfigKey, sqlConfigKey);
        listParams = new ListParamsBean();
    }

    @Override
    public Object execute(final IEOScalar eo) {
        return mapEo(eo, readRaw(eo));
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null || values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length > 5) {
            throw new EoException("Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 3 but has size " + array.length + ": '" + values + "'.");
        }
        if (array.length > 0) {
            setHostConfigKey(array[0]);
        }
        if (array.length > 1) {
            setSqlKey(array[1]);
        }
        if (array.length > 2) {
            setTargetPath(array[2]);
        }
        if (array.length > 3) {
            setCondition(array[3]);
        }
        if (array.length > 4) {
            setKeepCall(KeepCalls.valueOf(array[4]));
        }
    }

    public List readRaw(final IEOScalar eo) {
        DbSqlConfig config = init(PermissionType.READ, eo);
        listParams.initDb();
        return new FindStatement(config.getSql(), eo)
                .read(
                        getConnection(),
                        eo.getConfigMaps(),
                        listParams);
    }

    /*.{javaAccessors}|*/

    /**
     * Parameters of type {@link ListParamsBean} for list type read call operations like {@link CsvSimpleReadCall}.
     */
    public DbSqlReadCall setListParams(ListParamsBean listParams) {
        this.listParams = listParams;
        return this;
    }

    @Override
    public ListParamsBean getListParams() {
        return this.listParams;
    }

    public boolean hasListParams() {
        return listParams != null;
    }
    /*.{}.*/

}

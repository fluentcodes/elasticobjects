package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBeanInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;

import java.util.List;

/*.{javaHeader}|*/

/**
 * Read an entry in database by creating a select sql from entry in sourcePath. The object must be an instance of {@link ModelConfigDbObject}.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 06:39:50 CET 2020
 */
public class DbModelReadCall extends DbModelCall implements ListParamsBeanInterface, ConfigReadCommand {
    private ListParamsBean listParams;

    public DbModelReadCall() {
        super();
        listParams = new ListParamsBean();
    }

    public DbModelReadCall(final String dbModelKey) {
        super(dbModelKey);
        listParams = new ListParamsBean();
    }


    @Override
    public Object execute(final IEOScalar eo) {
        return mapEo(eo, readRaw(eo));
    }

    public List readRaw(final IEOScalar eo) {
        DbModelConfig dbModelConfig = init(PermissionType.READ, eo);
        listParams.initDb();
        return FindStatement.of(eo)
                .read(
                        dbModelConfig.getDbConfig().getConnection(),
                        eo.getConfigMaps(),
                        getListParams());
    }

    /**
     * Parameters of type {@link ListParamsBean} for list type read call operations like {@link CsvSimpleReadCall}.
     */

    public DbModelReadCall setListParams(ListParamsBean listParams) {
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
}

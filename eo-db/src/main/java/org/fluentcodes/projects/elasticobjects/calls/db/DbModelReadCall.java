package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBeanInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;
import java.util.Map;

/**
 * Read an entries in database by creating a select sql from entry in sourcePath. The object must be an instance of {@link ModelConfigDbObject}.
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

    DbModelReadCall(final String hostConfigKey, final String targetPath) {
        super(hostConfigKey, targetPath);
        listParams = new ListParamsBean();
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        if (!(eo instanceof EoChild)) {
            throw new EoException("Could not query scalar value");
        }
        return readRaw((EoChild)eo);
    }

    public Object readRaw(final EoChild eo) {
        DbModelsConfig config = init(PermissionType.READ, eo);
        listParams.initDb();
        StatementFind findStatement = config.getDbModelConfig(eo.getModelClass()).createQueryStatement(eo);
        List<Map<String, Object>> resultList = findStatement
                .read(
                        config.getDbConfig().getConnection(),
                        eo.getConfigMaps(),
                        getListParams());
        return mapEo(eo, resultList, eo.getModelClass());
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

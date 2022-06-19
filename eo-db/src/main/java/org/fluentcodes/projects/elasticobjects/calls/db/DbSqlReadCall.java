package org.fluentcodes.projects.elasticobjects.calls.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Map results of a sql select to the targetPath.
 */
public class DbSqlReadCall extends ListCall implements ConfigReadCommand {
    public static final String CONDITIONS = "conditions";
    public static final String CONDITION_LIST = "conditionList";
    String dbConfigKey;
    DbConfig dbConfig;
    String dbSqlConfigKey;
    DbSqlConfig dbSqlConfig;
    List<Object> queryFilterList;
    Map<String, Object> queryFilterMap;
    String filterString;

    public DbSqlReadCall() {
        super();
    }

    public DbSqlReadCall(final String dbConfigKey) {
        this();
        this.dbConfigKey = dbConfigKey;
    }

    public DbSqlReadCall(final String hostConfigKey, final String sqlConfigKey) {
        this(hostConfigKey);
        dbSqlConfigKey = sqlConfigKey;
    }

    public List<Object> getQueryFilterList() {
        return queryFilterList;
    }

    public boolean hasQueryFilter() {
        return queryFilterList !=null && !queryFilterList.isEmpty();
    }

    public void setQueryFilterList(List<Object> queryFilterList) {
        this.queryFilterList = queryFilterList;
    }
    public void addQueryFilter(Object filter) {
        if (queryFilterList == null) {
            queryFilterList = new ArrayList<>();
        }
        this.queryFilterList.add(filter);
    }

    public Map<String, Object> getQueryFilterMap() {
        return queryFilterMap;
    }

    public DbSqlReadCall setQueryFilterMap(Map<String, Object> queryFilterMap) {
        this.queryFilterMap = queryFilterMap;
        return this;
    }

    public void addQueryFilterMap(String key, Object filter) {
        if (queryFilterMap == null) {
            queryFilterMap = new LinkedHashMap<>();
        }
        this.queryFilterMap.put(key, filter);
    }

    public boolean hasQueryFilterMap() {
        return this.queryFilterMap !=null && !this.queryFilterMap.isEmpty();
    }

    public String getFilterString() {
        return filterString;
    }

    public DbSqlReadCall setFilterString(String filterString) {
        this.filterString = filterString;
        return this;
    }

    public boolean hasFilterString() {
        return this.filterString != null && !this.filterString.isEmpty();
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        if(!hasDbSqlConfigKey()) {
            throw new EoException("No dbSqlConfigKey defined");
        }
        if(!hasDbConfigKey()) {
            throw new EoException("No dbConfigKey defined");
        }

        dbConfig = (DbConfig) eo.getConfigMaps().find(HostConfig.class, dbConfigKey);
        dbConfig.hasPermissions(PermissionType.READ, eo.getRoles());

        dbSqlConfig = (DbSqlConfig) eo.getConfigMaps().find(DbSqlConfig.class, dbSqlConfigKey);
        dbSqlConfig.hasPermissions(PermissionType.READ, eo.getRoles());
        if (!getListParams().hasRowStart()) {
            getListParams().setRowStart(0);
        }
        if (!getListParams().hasRowEnd()) {
            getListParams().setRowEnd(100);
        }
        if (!getListParams().hasRowHead()) {
            getListParams().setRowHead(0);
        }
        //getListParams().initDb();
        List<Map<String, Object>> rawResult = readRaw(eo);
        mapToTarget(eo, rawResult);
        return "";
    }

    private List<Map<String, Object>> readRaw(final EOInterfaceScalar eo) {
        //getListParams().initDb();
        StatementFind statementFind = null;
        if (eo.hasEo(CONDITIONS)) {
            statementFind = new StatementFind(dbSqlConfig.getSql() + " where ", new Or((String) eo.get(CONDITIONS)));
        } else if (eo.hasEo(CONDITION_LIST)) {
            statementFind = new StatementFind(dbSqlConfig.getSql(), (List) eo.get(CONDITION_LIST));
        }
        else {
            if (hasQueryFilter()) {
                statementFind = new StatementFind(dbSqlConfig.getSql(), queryFilterList);
            }
            if (hasQueryFilterMap()) {
                if (statementFind!=null) {
                    statementFind.addFilterMap(queryFilterMap);
                }
                else {
                    statementFind = new StatementFind(dbSqlConfig.getSql(), queryFilterMap);
                }
            }
            if (hasFilterString()) {
                if (statementFind!=null) {
                    statementFind.addFilterString(filterString);
                }
                else {
                    statementFind = new StatementFind(dbSqlConfig.getSql(), filterString);
                }
            }
            if (statementFind == null) {
                statementFind = new StatementFind(dbSqlConfig.getSql(), eo);
            }
        }
        return statementFind.read(
            dbConfig.getConnection(),
            eo.getConfigMaps(),
            getListParams());
    }

    @Override
    public void setConfigKey(String configKey) {
        this.dbSqlConfigKey = configKey;
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null || values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length > 5) {
            throw new EoException(
                "Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 3 but has size " +
                    array.length + ": '" + values + "'.");
        }
        if (array.length > 0) {
            setHostConfigKey(array[0]);
        }
        if (array.length > 1) {
            setDbSqlConfigKey(array[1]);
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


    public String getDbSqlConfigKey() {
        return dbSqlConfigKey;
    }

    public DbSqlReadCall setDbSqlConfigKey(String dbSqlConfigKey) {
        this.dbSqlConfigKey = dbSqlConfigKey;
        return this;
    }

    public boolean hasDbSqlConfigKey() {
        return dbSqlConfigKey != null && !dbSqlConfigKey.isEmpty();
    }

    public String getDbConfigKey() {
        return dbConfigKey;
    }

    public DbSqlReadCall setDbConfigKey(String dbConfigKey) {
        this.dbConfigKey = dbConfigKey;
        return this;
    }

    public boolean hasDbConfigKey() {
        return dbConfigKey != null && !dbConfigKey.isEmpty();
    }
}

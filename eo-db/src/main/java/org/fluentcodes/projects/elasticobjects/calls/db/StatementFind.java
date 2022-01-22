package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.sql.Types.*;

public class StatementFind extends StatementPreparedValues {
    public StatementFind() {
        super(SqlType.FIND);
    }

    public StatementFind(final String statement, EOInterfaceScalar eo) {
        super(SqlType.FIND);
        append(statement);
        if (countParams() == 0) {
            return;
        }
        if (eo.isScalar()) {
            add(eo.get());
        } else {
            for (Object value : ((EoChild) eo).getKeyValues().values()) {
                add(value);
            }
        }
        checkHealth();
    }

    public Object readOneOrEmpty(Connection connection, ConfigMaps configsCache) {
        ListParamsBean listParams = new ListParamsBean()
                .setRowHead(0)
                .setRowStart(0);
        List<Map<String, Object>> list = read(connection, configsCache, listParams);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size()>1) {
            throw new EoException("Found " + list.size() + " entries where one is allowed for '" + getStatement() + "'");
        }
        return list.get(0);
    }

    public Object readFirst(Connection connection, ConfigMaps configsCache) {
        ListParamsBean listParams = new ListParamsBean()
                .setRowHead(0)
                .setRowStart(0);
        List<Map<String, Object>> list = read(connection, configsCache, listParams);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size()>1) {
        }
        return list.get(0);
    }

    public List<Map<String, Object>> read(Connection connection, ConfigMaps configsCache, ListParamsBean listParams) {
        if (connection == null) {
            throw new EoInternalException("Null connection");
        }
        checkHealth();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getStatement());
            resolve(preparedStatement);
            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement.executeQuery();
                /*if (!resultSet.isBeforeFirst()) {
                    return new ArrayList<>();
                }*/
                return readRaw(resultSet, configsCache, listParams);
            } catch (Exception e) {
                DbConfig.closeAll(preparedStatement, resultSet);
                throw new EoException("Exception get resultSet for sql " + getStatement() + ": " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new EoException(e);
        }
    }

    private List<Map<String, Object>> readRaw(final ResultSet resultSet, ConfigMaps configsCache, ListParamsBean listParams) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (listParams.hasRowHead()) {
            try {
                listParams.setColKeys(initMetaData(resultSet));
            } catch (SQLException e) {
                throw new EoException(e);
            }
        }
        int i = 1;
        try {
            while (resultSet.next()) {
                List<Object> rowEntry = createRowList(resultSet);
                if (listParams.hasRowEnd() && i > listParams.getRowEnd()) {
                    return result;
                }
                if (listParams.hasRowStart() && i >= listParams.getRowStart()) {
                    listParams.addRowEntry(configsCache, result, rowEntry);
                }
                i++;
            }
        } catch (Exception e) {
            throw new EoException("Exception create a list from line counter " + i + ": " + e.getMessage());
        }
        return result;
    }

    protected List<String> initMetaData(ResultSet resultSet) throws SQLException {
        List<String> metaDataNames = new ArrayList<>();
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            metaDataNames.add(resultSet.getMetaData().getColumnName(i));
        }
        return metaDataNames;
    }

    protected static List createRowList(final ResultSet resultSet) throws SQLException {
        List row = new ArrayList();
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            int type = resultSet.getMetaData().getColumnType(i);
            switch (resultSet.getMetaData().getColumnType(i)) {
                case VARCHAR:
                    row.add(resultSet.getString(i));
                    break;
                case LONGNVARCHAR:
                    row.add(resultSet.getString(i));
                case LONGVARCHAR:
                    row.add(resultSet.getString(i));
                    break;
                case INTEGER:
                    row.add(resultSet.getInt(i));
                    break;
                case DOUBLE:
                    row.add(resultSet.getDouble(i));
                    break;
                case FLOAT:
                    row.add(resultSet.getFloat(i));
                    break;
                case BOOLEAN:
                    row.add(resultSet.getBoolean(i));
                    break;
                case DATE:
                    row.add(resultSet.getDate(i));
                    break;
                case BIGINT:
                    row.add(resultSet.getLong(i));
                    break;
                case LONGVARBINARY:
                    row.add(resultSet.getByte(i));
                    break;
                default:
                    throw new EoException("Could not find " + i + " type " + resultSet.getMetaData().getColumnType(i));
            }
        }
        return row;
    }


}

package org.fluentcodes.projects.elasticobjects.calls.db;

import static java.sql.Types.BIGINT;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.DATE;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.LONGNVARCHAR;
import static java.sql.Types.LONGVARBINARY;
import static java.sql.Types.LONGVARCHAR;
import static java.sql.Types.REAL;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.VARCHAR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

public class StatementFind extends StatementPreparedValues {
    public StatementFind() {
        super(SqlType.FIND);
    }

    public StatementFind(final String statement, List<Object> conditionList) {
        super(SqlType.FIND);
        append(statement);
        for (Object value : conditionList) {
            addValue(value);
        }
    }

    public StatementFind(final String statement, Map<String, Object> filterMap) {
        super(SqlType.FIND);
        append(statement);
        addFilterMap(filterMap);
    }

    public StatementFind(final String statement, String filterString) {
        super(SqlType.FIND);
        append(statement);
        addFilterString(filterString);
    }

    public void addFilterMap(Map<String, Object> conditionList) {
        for (Map.Entry<String, Object> entry: conditionList.entrySet()) {
            getStatementAsIs().append(" and " + entry.getKey() + " = ?");
            addValue(entry.getValue());
        }
    }

    public void addFilterString(String filterString) {
        Or or = new Or(filterString);
        List<Object> values = or.addSql(getStatementAsIs());
        for (Object value:values) {
            addValue(value);
        }
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

    protected static List createRowList(final ResultSet resultSet) throws SQLException {
        List row = new ArrayList();
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            int type = resultSet.getMetaData().getColumnType(i);
            int columnType = resultSet.getMetaData().getColumnType(i);
            switch (columnType) {
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
                case REAL:
                    row.add(resultSet.getFloat(i));
                    break;
                case BOOLEAN:
                    row.add(resultSet.getBoolean(i));
                    break;
                case DATE:
                    row.add(resultSet.getDate(i));
                    break;
                case TIMESTAMP:
                    //https://stackoverflow.com/questions/8992282/convert-localdate-to-localdatetime-or-java-sql-timestamp
                    if (resultSet.getTimestamp(i) != null) {
                        row.add(resultSet.getTimestamp(i).toLocalDateTime());
                    } else {
                        row.add(null);
                    }
                    break;
                case BIGINT:
                    row.add(resultSet.getLong(i));
                    break;
                case LONGVARBINARY:
                    row.add(resultSet.getByte(i));
                    break;
                default:
                    throw new EoException(
                        "Problem resolving resultset with type defined for field " + i + " with jdbx type " +
                            resultSet.getMetaData().getColumnTypeName(i));
            }
        }
        return row;
    }

    public Map<String, Object> readOneOrEmpty(Connection connection, ConfigMaps configsCache) {
        ListParamsBean listParams = new ListParamsBean()
            .setRowHead(0)
            .setRowStart(0);
        List<Map<String, Object>> list = read(connection, configsCache, listParams);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new EoException(
                "Found " + list.size() + " entries where one is allowed for '" + getStatement() + "'");
        }
        return list.get(0);
    }

    public Map<String, Object> readFirst(Connection connection, ConfigMaps configsCache) {
        ListParamsBean listParams = new ListParamsBean()
            .setRowHead(0)
            .setRowStart(0);
        List<Map<String, Object>> list = read(connection, configsCache, listParams);
        if (list.isEmpty()) {
            throw new EoException("Empty result set for " + this);
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
                return readRaw(resultSet, configsCache, listParams);
            } catch (Exception e) {
                DbConfig.closeAll(preparedStatement, resultSet);
                throw new EoException("Exception get resultSet for sql " + getStatement() + ": " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new EoException(e);
        }
    }

    public boolean exists(Connection connection) {
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
                return resultSet.next();
            } catch (Exception e) {
                DbConfig.closeAll(preparedStatement, resultSet);
                throw new EoException("Exception get resultSet for sql " + getStatement() + ": " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new EoException(e);
        }
    }

    private List<Map<String, Object>> readRaw(final ResultSet resultSet, ConfigMaps configsCache,
                                              ListParamsBean listParams) {
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


}

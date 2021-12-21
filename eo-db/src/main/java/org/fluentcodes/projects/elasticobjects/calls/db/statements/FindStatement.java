package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindStatement extends PreparedStatementValues {

    public FindStatement(String model, Map<String, Object> values) {
        super(SqlType.FIND);
        StringBuilder builderValues = new StringBuilder("");
        for (String key : values.keySet()) {
            add(values.get(key));
            builderValues.append(key + " = ? AND ");
        }
        append("SELECT * FROM ");
        append(model);
        append(" WHERE ");
        append(builderValues.toString().replaceAll(" AND $", " "));
    }

    public FindStatement(final String preparedStatement, IEOScalar eo) {
        super(SqlType.FIND);
        append(preparedStatement);
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

    public static FindStatement of(IEOScalar source) {
        if (source == null) {
            throw new EoException("Null eo for delete");
        }
        ModelInterface model = source.getModel();
        if (!model.isObject()) {
            throw new EoException("Model '" + model.getModelKey() + "' is not a object");
        }
        return new FindStatement(model.getModelKey(), ((EoChild) source).getKeyValues());
    }

    public static FindStatement ofId(IEOScalar source) {
        if (source == null) {
            throw new EoException("Null eo for delete");
        }
        ModelInterface model = source.getModel();
        if (!model.isObject()) {
            throw new EoException("Model '" + model.getModelKey() + "' is not a object");
        }
        Map<String, Object> values = new HashMap<>();
        Map inValues = ((EoChild) source).getKeyValues();
        if (inValues.containsKey("id")) {
            values.put("id", inValues.get("id"));
        } else if (inValues.containsKey("naturalId")) {
            values.put("naturalId", values.get("naturalId"));
        } else {
            throw new EoException("Could not find ids... ");
        }
        return new FindStatement(model.getModelKey(), values);
    }

    public static FindStatement of(final String modelKey, final Long id) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("id", id);
        return new FindStatement(modelKey, values);
    }

    public static FindStatement of(final String modelKey, final String naturalId) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("naturalId", naturalId);
        return new FindStatement(modelKey, values);
    }

    public List readFirst(Connection connection, ConfigMaps configsCache) {
        ListParamsBean listParams = new ListParamsBean()
                .setRowHead(0)
                .setRowStart(0)
                .setRowEnd(1);
        return read(connection, configsCache, listParams);
    }

    public List read(Connection connection, ConfigMaps configsCache, ListParamsBean listParams) {
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

    private List readRaw(final ResultSet resultSet, ConfigMaps configsCache, ListParamsBean listParams) {
        List result = new ArrayList<>();
        if (listParams.hasRowHead()) {
            try {
                listParams.setColKeys(initMetaData(resultSet));
            } catch (SQLException e) {
                throw new EoException(e);
            }
        }
        List rowEntry = null;
        int i = 1;
        try {
            while ((rowEntry = createRow(resultSet)) != null) {
                if (i > listParams.getRowEnd()) {
                    return result;
                }
                if (i >= listParams.getRowStart()) {
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
        for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
            metaDataNames.add(resultSet.getMetaData().getColumnName(i));
        }
        return metaDataNames;
    }

    protected static List createRow(final ResultSet resultSet) throws SQLException {
        resultSet.next();
        if (resultSet.isAfterLast()) {
            return null;
        }
        List row = new ArrayList();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            switch (metaData.getColumnTypeName(i)) {
                case "VARCHAR":
                    row.add(resultSet.getString(i));
                    break;
                case "INTEGER":
                    row.add(resultSet.getInt(i));
                    break;
                case "BOOLEAN":
                    row.add(resultSet.getBoolean(i));
                    break;
                case "DOUBLE":
                    row.add(resultSet.getDouble(i));
                    break;
                case "DATE":
                    row.add(resultSet.getDate(i));
                    break;
                case "BIGINT":
                    row.add(resultSet.getLong(i));
                    break;
            }
        }
        return row;
    }


}

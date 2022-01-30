package org.fluentcodes.projects.elasticobjects.calls.db;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StatementPreparedValues {
    private SqlType sqlType = SqlType.NONE;
    private final List<Object> values;
    private final StringBuilder statement;


    public StatementPreparedValues(final SqlType sqlType) {
        this(sqlType, "");
    }

    public StatementPreparedValues(final SqlType sqlType, final String content) {
        this.sqlType = sqlType;
        values = new ArrayList<>();
        statement = new StringBuilder(content);
    }

    protected void add(Object object) {
        values.add(object);
    }

    protected void resolve(final PreparedStatement preparedStatement) {
        int counter = 1;
        try {
            for (Object value : values) {
                if (value instanceof String) {
                    preparedStatement.setString(counter, (String) value);
                } else if (value instanceof Integer) {
                    preparedStatement.setInt(counter, (Integer) value);
                } else if (value instanceof Long) {
                    preparedStatement.setLong(counter, (Long) value);
                } else if (value instanceof Double) {
                    preparedStatement.setDouble(counter, (Double) value);
                } else if (value instanceof Float) {
                    preparedStatement.setFloat(counter, (Float) value);
                } else if (value instanceof Boolean) {
                    preparedStatement.setBoolean(counter, (Boolean) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(counter, new java.sql.Date(((Date) value).getTime()));
                } else {
                    throw new EoInternalException("No validated " + counter + " - " + value);
                }
                counter++;
            }
        } catch (SQLException e) {
            throw new EoException(counter + ": " + e.getMessage());
        }
    }

    public String getStatement() {
        return statement.toString();
    }

    public void append(String add) {
        statement.append(add);
    }

    protected int countParams() {
        return statement.toString().replaceAll("[^\\?]", "").length();
    }

    protected void checkHealth() {
        if (countParams() != values.size()) {
            throw new EoException("Problem that prepared statement has " + countParams() + " variables but only " + values.size() + " values are provided!");
        }
    }

    public boolean hasValues() {
        return values != null && !values.isEmpty();
    }

    public void addValue(final Object value) {
        values.add(value);
    }

    public void addValues(final List<Object> values) {
        this.values.addAll(values);
    }

    public void addValues(final Object[] values) {
        this.values.addAll(Arrays.asList(values));
    }

    public List<Object> getValues() {
        return new ArrayList(values);
    }

    public int execute(Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement.toString());
            resolve(preparedStatement);
            ResultSet resultSet = null;
            try {
                if (sqlType.equals(SqlType.FIND)) {
                    resultSet = preparedStatement.executeQuery();
                    return resultSet.next() ? 1 : 0;
                } else if (sqlType.equals(SqlType.UPDATE)) {
                    return preparedStatement.executeUpdate();
                } else if (sqlType.equals(SqlType.INSERT)) {
                    return preparedStatement.executeUpdate();
                } else if (sqlType.equals(SqlType.DELETE)) {
                    return preparedStatement.executeUpdate();
                }
                else if (sqlType.equals(SqlType.CREATE)) {
                    return preparedStatement.executeUpdate();
                }
                else {
                    throw new EoException("Nothing to do for  " + sqlType.name());
                }
            } catch (Exception e) {
                DbConfig.closeAll(preparedStatement, resultSet);
                throw new EoException("Exception get resultSet for sql " + statement.toString() + ": " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new EoException(e);
        }

    }

    public enum SqlType {
        UPDATE, INSERT, FIND, DELETE, CREATE, NONE;
    }

    @Override
    public String toString() {
        return "(" + sqlType.name() + ")" + statement + " -> " + values;
    }

}

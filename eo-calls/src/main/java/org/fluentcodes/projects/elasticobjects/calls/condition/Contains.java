package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 27.09.20.
 */
public class Contains extends ConditionImpl {
    private static final Logger LOG = LogManager.getLogger(Contains.class);

    public Contains(String key, Object object) {
        super(key, object);
    }

    public String getOperator() {
        return Condition.CONTAINS;
    }

    @Override
    public void createQuery(StringBuilder sql, List<Object> values) {
        sql.append( getKey() + " like ? ");
        values.add(getValue());
    }

    @Override
    public Object addSql(StringBuilder statement) {
        statement.append("" + getKey() + " in (?) ");
        return getValue();
    }

    @Override
    public String createQuery(Map<String, Object> keyValues) {
        StringBuilder builder = new StringBuilder();
        String idKey = getKey() + "_" + keyValues.size();
        keyValues.put(idKey, getValue());
        builder.append("" + getKey() + "like :" + idKey + " ");
        return builder.toString();
    }

    @Override
    public boolean filter(List row) {
        if (row == null) {
            LOG.warn("Null row should not occure!");
            return true;
        }
        try {
            Integer i = Integer.parseInt(getKey());
            if (i < row.size()) {
                String value = row.get(i).toString();
                String objectString = getValue().toString();
                return value.contains(objectString);
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean filter(EOInterfaceScalar adapter) {
        if (adapter == null) {
            LOG.warn("Null adapter should not occure!");
            return true;
        }
        try {
            Object value = adapter.get(getKey());
            String valueString = new ShapeTypeSerializerString().asObject(value);
            String objectString = getValue().toString();
            if (valueString == null) {
                if (objectString == null) {
                    return true;
                }
                return false;
            }
            return valueString.matches(objectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

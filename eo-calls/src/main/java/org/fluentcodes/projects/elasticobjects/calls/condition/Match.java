package org.fluentcodes.projects.elasticobjects.calls.condition;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class Match implements Condition {
    private static final Logger LOG = LogManager.getLogger(Match.class);
    private final Object object;
    private final String key;

    public Match(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getValue() {
        return object;
    }

    @Override
    public boolean compare(Object object) {
        if (object == null) {
            return this.object == null;
        }
        //TODO
        return true;
    }

    @Override
    public void createQuery(StringBuilder sql, List<Object> values) {
        sql.append( key + " like ?");
        values.add(object);
    }

    @Override
    public String createQuery(Map<String, Object> keyValues) {
        StringBuilder builder = new StringBuilder();
        String idKey = key + "_" + keyValues.size();
        keyValues.put(idKey, getValue());
        builder.append("" + key + "like :" + idKey + " ");
        return builder.toString();
    }

    @Override
    public boolean filter(List row) {
        if (row == null) {
            LOG.warn("Null row should not occure!");
            return true;
        }
        try {
            Integer i = Integer.parseInt(key);
            if (i < row.size()) {
                String value = row.get(i).toString();
                String objectString = getValue().toString();
                return value.matches(objectString);
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean filter(final EOInterfaceScalar eo) {
        if (eo == null) {
            LOG.warn("Null adapter should not occure!");
            return true;
        }
        try {
            Object value = eo.get(key);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.key);
        builder.append(" like ");
        builder.append(this.object.toString());
        return builder.toString();
    }


}

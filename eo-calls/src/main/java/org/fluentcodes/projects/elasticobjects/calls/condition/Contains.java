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
public class Contains implements Condition {
    private static final Logger LOG = LogManager.getLogger(Contains.class);
    private final Object object;
    private final String key;

    public Contains(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return object;
    }

    public boolean compare(Object object) {
        if (object == null) {
            return this.object == null;
        }
        //TODO
        return true;
    }

    public String createQuery(Map<String, Object> keyValues) {
        StringBuilder builder = new StringBuilder();
        String idKey = key + "_" + keyValues.size();
        keyValues.put(idKey, getValue());
        builder.append("" + key + "like :" + idKey + " ");
        return builder.toString();
    }

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
                return value.contains(objectString);
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    public boolean filter(EOInterfaceScalar adapter) {
        if (adapter == null) {
            LOG.warn("Null adapter should not occure!");
            return true;
        }
        try {
            Object value = adapter.get(key);
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

package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;

import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class NotExists implements Condition {
    private static final Logger LOG = LogManager.getLogger(NotExists.class);
    private final String key;

    public NotExists(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public boolean compare(Object object) {
        //TODO
        return true;
    }

    @Override
    public void createQuery(StringBuilder sql, List<Object> values) {
        sql.append( key + " is null ");
    }

    @Override
    public String createQuery(Map<String, Object> keyValues) {
        //TODO
        return "";
    }

    public String createCondition() {
        StringBuilder builder = new StringBuilder();
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
                return row.get(i) == null;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean filter(EOInterfaceScalar eo) {
        if (eo == null) {
            LOG.warn("Null adapter should not occure!");
            return true;
        }
        try {
            return eo.get(key) == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.key);
        return builder.toString();
    }
}

package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class Ne implements Condition {
    private static final Logger LOG = LogManager.getLogger(Ne.class);
    private final Object object;
    private String key;

    public Ne(String key, Object object) {
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
        builder.append("NOT " + key + "=:" + idKey + " ");
        return builder.toString();
    }

    public String createCondition() {
        StringBuilder builder = new StringBuilder();
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
                Object value = row.get(i);
                return !new ShapeTypeSerializerString().compare(value, this.object);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean filter(EOInterfaceScalar eo) {
        if (eo == null) {
            LOG.warn("Null adapter should not occure!");
            return true;
        }
        try {
            return !new ShapeTypeSerializerString().compare(eo.get(key), new Parser(TemplateMarker.SQUARE, (String) this.object).parse(eo));
        } catch (EoException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.key);
        builder.append(" ne ");
        builder.append(this.object.toString());
        return builder.toString();
    }
}

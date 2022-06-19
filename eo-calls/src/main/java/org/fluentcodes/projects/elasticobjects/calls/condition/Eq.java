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
public class Eq extends ConditionImpl {
    private static final Logger LOG = LogManager.getLogger(Eq.class);

    public Eq(String key, Object object) {
        super(key, object);
    }

    @Override
    public String getOperator() {
        return Condition.EQ;
    }

    @Override
    public Object addSql(StringBuilder statement) {
        statement.append("" + getKey() + " = ? ");
        return getValue();
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
            Integer i = Integer.parseInt(getKey());
            if (i < row.size()) {
                Object value = row.get(i);
                return new ShapeTypeSerializerString().compare(value, getObject());
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean filter(final EOInterfaceScalar eo) {
        if (eo == null) {
            LOG.warn("Null adapter should not occure!");
            return true;
        }
        try {
            return new ShapeTypeSerializerString().compare(eo.get(getKey()), new Parser(TemplateMarker.SQUARE, (String) getObject()).parse(eo));
        } catch (EoException e) {
            return false;
        }
    }
}

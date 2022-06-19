package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;

import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class NotExists extends ConditionImpl {
    private static final Logger LOG = LogManager.getLogger(NotExists.class);

    public NotExists(String key) {
        super(key, null);
    }

    public String getOperator() {
        return Condition.NEX;
    }

    @Override
    public Object addSql(StringBuilder statement) {
        statement.append(getKey() + " is null");
        return null;
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
            Integer i = Integer.parseInt(getKey());
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
            return eo.get(getKey()) == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

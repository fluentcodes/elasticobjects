package org.fluentcodes.projects.elasticobjects.calls.condition;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class Exists extends ConditionImpl {
    private static final Logger LOG = LogManager.getLogger(Exists.class);

    public Exists(String key) {
        super(key, null);
    }

    public String getOperator() {
        return Condition.EX;
    }

    @Override
    public void createQuery(StringBuilder sql, List<Object> values) {
        sql.append( getKey() + " is not null ");
    }

    @Override
    public Object addSql(StringBuilder statement) {
        statement.append(getKey() + " is not null");
        return null;
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
            return false;
        }
        try {
            Integer i = Integer.parseInt(getKey());
            if (i < row.size()) {
                return row.get(i) != null;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean filter(EOInterfaceScalar eo) {
        if (eo == null) {
            throw new EoException("Null adapter should not occure!");
        }
        try {
            return eo.get(getKey()) != null;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }
}

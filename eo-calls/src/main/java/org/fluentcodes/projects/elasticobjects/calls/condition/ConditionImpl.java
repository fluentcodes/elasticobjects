package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstract implementation of different conditions.
 */
public abstract class ConditionImpl implements Condition {
    private static final Logger LOG = LogManager.getLogger(ConditionImpl.class);
    private final Object object;
    private final String key;

    public ConditionImpl(String key, Object object) {
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

    public Object getObject() {
        return object;
    }

    @Override
    public boolean compare(Object object) {
        if (object == null) {
            return this.object == null;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.key);
        builder.append(getOperator());
        builder.append(this.object.toString());
        return builder.toString();
    }
}

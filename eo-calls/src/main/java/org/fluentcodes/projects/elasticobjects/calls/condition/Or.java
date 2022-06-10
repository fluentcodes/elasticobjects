package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class Or {
    public static final String DELIMITER = "||";
    private static final Logger LOG = LogManager.getLogger(Or.class);
    private static final String SPLITTER = "\\|\\|";
    List<And> andList;
    Map<String, Object> keyValues;

    public Or(Or or) {
        this();
        for (And and : or.andList) {
            this.addAnd(and);
        }
    }

    public Or(Condition... conditions) {
        this();
        for (Condition condition : conditions) {
            andList.add(new And(condition));
        }
    }

    public Or(And... and) {
        this();
        this.andList = Arrays.asList(and);
    }

    public Or() {
        andList = new ArrayList<>();
    }

    public Or(String orAsString) {
        this();
        if (orAsString == null || orAsString.isEmpty()) {
            return;
        }
        String[] andArray = orAsString.split(SPLITTER);
        for (String andAsString : andArray) {
            andList.add(new And(andAsString));
        }
    }

    public void addAnd(And and) {
        if (andList == null) {
            andList = new ArrayList<>();
        }
        andList.add(and);
    }

    public void addAndToAll(And and) {
        if (andList == null) {
            andList = new ArrayList<>();
            andList.add(and);
        }
        for (And condition : andList) {
            condition.addConditions(and);
        }
    }

    public Map<String, Object> getKeyValueMap() {
        return this.keyValues;
    }

    public void createQuery(final StringBuilder sql, final List<Object> values) {
        this.keyValues = new HashMap<>();
        int counter = 0;

        for (And and : andList) {
            sql.append(" (");
            and.createQuery(sql, values);
            counter++;
            sql.append(") ");
            if (counter != andList.size()) {
                sql.append(" or ");
            }
        }
    }

    public String createQuery() {
        StringBuilder sql = new StringBuilder();
        this.keyValues = new HashMap<>();
        int counter = 0;
        for (And and : andList) {
            sql.append(and.createQuery(keyValues));
            counter++;
            if (counter != andList.size()) {
                sql.append(" or ");
            }
        }
        return sql.toString();
    }

    public boolean filter(List row) {
        if (row == null) {
            LOG.warn("Null row should not occure!");
            return true;
        }
        boolean result = false;
        for (And and : andList) {
            result = result || and.filter(row);
            if (result) {
                return true;
            }
        }
        return false;
    }

    public boolean filter(final EOInterfaceScalar adapter) {
        if (adapter == null) {
            LOG.warn("Null row should not occure!");
            return true;
        }
        boolean result = false;
        for (And and : andList) {
            result = result || and.filter(adapter);
            if (result) {
                return true;
            }
        }
        return false;
    }

    public List<And> getAndList() {
        return andList;
    }

    public int size() {
        return andList.size();
    }

    public And getAnd(int i) {
        if (i < andList.size()) {
            return andList.get(i);
        }
        return null;
    }

    public boolean isEmpty() {
        if (andList == null) {
            return true;
        }
        return andList.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (And and : andList) {
            builder.append(and.toString());
            counter++;
            if (counter < andList.size()) {
                builder.append(" || ");
            }
        }
        return builder.toString();
    }
}

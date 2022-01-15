package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public interface Condition {
    String EQ = "eq";
    String EQUALS = "equals";
    String MATCH = "match";
    String EX = "ex";
    String NEX = "nex";
    String LIKE = "like";
    String NE = "ne";
    static Pattern ifPattern = Pattern.compile("[\\s]*([^\\s]*?)[\\s]+(eq\\s+|ne\\s+|equals\\s+|ex|nex|le\\s+|ge\\s+|notEquals\\s+|like\\s+|notLike\\s+|match\\s+|contains\\s+|in\\s+)(.*)");

    boolean compare(Object object);

    String createQuery(final Map<String, Object> keyValues);

    String getKey();

    Object getValue();

    boolean filter(final List row);

    boolean filter(final EOInterfaceScalar eo);
}

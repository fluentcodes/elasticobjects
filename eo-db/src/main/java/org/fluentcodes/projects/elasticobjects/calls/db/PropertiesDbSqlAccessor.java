package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.models.Properties;

public interface PropertiesDbSqlAccessor extends Properties {
    final static String DB_KEY = "dbKey";


    default boolean hasDbKey() {
        return getDbKey()!=null && !getDbKey().isEmpty();
    }

    default String getDbKey() {
        return hasProperties() ? (String) getProperties().get(DB_KEY) : null;
    }

    default void setDbKey(final String module) {
        if (hasProperties()) {
            getProperties().put(DB_KEY, module);
        }
    }
}

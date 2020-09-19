package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.models.PropertiesAccessor;

public interface PropertiesDbSqlAccessor extends PropertiesAccessor {
    String DB_KEY = "dbKey";


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
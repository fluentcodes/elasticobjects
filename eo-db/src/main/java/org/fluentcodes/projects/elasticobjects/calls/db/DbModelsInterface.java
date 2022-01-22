package org.fluentcodes.projects.elasticobjects.calls.db;


import java.util.List;

/**
 */
public interface DbModelsInterface {

    List<String> getDbModelKeys();
    default boolean hasDbModelKeys() {
        return getDbModelKeys()!=null && !getDbModelKeys().isEmpty();
    }

    String getDbConfigKey();
    default boolean hasDbConfigKey() {
        return getDbConfigKey()!=null && !getDbConfigKey().isEmpty();
    }
}

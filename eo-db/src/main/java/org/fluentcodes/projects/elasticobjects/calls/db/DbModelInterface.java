package org.fluentcodes.projects.elasticobjects.calls.db;


/**
 * Created by Werner on 06.01.2022.
 */
public interface DbModelInterface {

    String getModelKey();
    default boolean hasModelKey() {
        return getModelKey()!=null && !getModelKey().isEmpty();
    }

    String getDbConfigKey();
    default boolean hasDbConfigKey() {
        return getDbConfigKey()!=null && !getDbConfigKey().isEmpty();
    }
}

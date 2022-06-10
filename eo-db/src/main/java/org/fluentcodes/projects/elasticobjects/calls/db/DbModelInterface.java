package org.fluentcodes.projects.elasticobjects.calls.db;


/**
 */
public interface DbModelInterface {
    String getModelKey();
    default boolean hasModelKey() {
        return getModelKey()!=null && !getModelKey().isEmpty();
    }
}

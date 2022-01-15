package org.fluentcodes.projects.elasticobjects;

/**
 * Offers an adapter for scalar wrapper to access elements via path.
 */

public interface EOInterfaceBase {
    EO getParent();

    default boolean hasParent() {
        return getParent() != null;
    }

    default boolean isRoot() {
        return !hasParent();
    }

    EOInterfaceScalar map(Object source);

    String getFieldKey();

    Path getPath();

    String getPathAsString();

    Object get();

    EOInterfaceScalar getEo(String... path);

    Object get(final String... pathStrings);

    void set(final Object value);

    EOInterfaceScalar set(Object value, String... paths);

    EoRoot getRoot();

    default void setCheckObjectReplication(boolean checkObjectReplication) {
        getRoot().setCheckObjectReplication(checkObjectReplication);
    }

    boolean isChanged();

    void setChanged();

    EO remove();

    default boolean isEmpty() {
        return get() == null;
    }

    default boolean isEoEmpty() {
        return true;
    }

    default boolean hasEo(String path) {
        return false;
    }
}

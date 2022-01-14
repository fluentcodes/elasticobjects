package org.fluentcodes.projects.elasticobjects;

/**
 * Offers an adapter for scalar wrapper to access elements via path.
 */

public interface IEOBase {
    IEOObject getParent();

    default boolean hasParent() {
        return getParent() != null;
    }

    default boolean isRoot() {
        return !hasParent();
    }

    IEOScalar map(Object source);

    String getFieldKey();

    Path getPath();

    String getPathAsString();

    Object get();

    IEOScalar getEo(String... path);

    Object get(final String... pathStrings);

    void set(final Object value);

    IEOScalar set(Object value, String... paths);

    EoRoot getRoot();

    default void setCheckObjectReplication(boolean checkObjectReplication) {
        getRoot().setCheckObjectReplication(checkObjectReplication);
    }

    boolean isChanged();

    void setChanged();

    IEOObject remove();

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

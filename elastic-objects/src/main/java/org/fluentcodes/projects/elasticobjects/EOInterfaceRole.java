package org.fluentcodes.projects.elasticobjects;

import java.util.Arrays;
import java.util.List;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EOInterfaceRole extends EOInterfaceBase {
    default void setRoles(String... roles) {
        setRoles(Arrays.asList(roles));
    }

    default List<String> getRoles() {
        return getRoot().getRoles();
    }

    default void setRoles(List<String> roles) {
        getRoot().setRoles(roles);
    }

    default boolean hasRoles() {
        return getRoles() != null && !getRoles().isEmpty();
    }
}

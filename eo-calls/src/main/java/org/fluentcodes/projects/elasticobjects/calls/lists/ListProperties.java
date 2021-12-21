package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;

import java.util.List;

public interface ListProperties extends ConfigInterface {

    default boolean hasRowHead() {
        return getRowHead() != null;
    }

    Integer getRowHead();

    default boolean hasColKeys() {
        return getColKeys() != null && !getColKeys().isEmpty();
    }

    List<String> getColKeys();

    default boolean hasRowStart() {
        return getRowStart() != null;
    }

    Integer getRowStart();

    default boolean hasRowEnd() {
        return getRowEnd() != null;
    }

    Integer getRowEnd();

    default boolean hasLength() {
        return getLength() != null;
    }

    Integer getLength();

    default boolean hasFilter() {
        return getFilter() != null && !getFilter().isEmpty();
    }

    String getFilter();

}

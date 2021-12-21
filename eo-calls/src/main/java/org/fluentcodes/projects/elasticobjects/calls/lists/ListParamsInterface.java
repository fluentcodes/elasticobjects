package org.fluentcodes.projects.elasticobjects.calls.lists;

import java.util.List;

/**
 * A bean class for containing the following values:
 * <ul>
 * <li>rowStart: .</li>
 * <li>rowEnd: .</li>
 * <li>length: ??</li>
 * </ul>
 * Created by werner.diwischek on 03.12.16.
 */
public interface ListParamsInterface {
    default boolean hasFilter() {
        return getFilter() != null && !getFilter().isEmpty();
    }

    String getFilter();

    Integer getRowHead();

    Integer getRowStart();

    Integer getRowEnd();

    Integer getLength();

    List<String> getColKeys();

    default boolean hasRowHead() {
        return getRowHead() != null && getRowHead() > -1;
    }

    default boolean hasRowStart() {
        return getRowStart() != null && getRowStart() > -1;
    }

    default boolean hasRowEnd() {
        return getRowEnd() != null && getRowEnd() > -1;
    }

    default boolean hasLength() {
        return getLength() != null && getLength() > 0;
    }

    default boolean hasColKeys() {
        return getColKeys() != null && !getColKeys().isEmpty();
    }
}

package org.fluentcodes.projects.elasticobjects.calls.lists;

import java.util.List;

/**
 * A bean class for containing the following values:
 * <ul>
 * <li>rowStart: .</li>
 * <li>rowEnd: .</li>
 * <li>length: ??</li>
 * </ul>
 * Created by werner.diwischek on 21.12.21.
 */

public interface ListParamsConfigInterface extends ListParamsInterface {
    ListParamsConfig getListParams();

    default String getFilter() {
        return getListParams().getFilter();
    }

    default Integer getRowHead() {
        return getListParams().getRowHead();
    }

    default Integer getRowStart() {
        return getListParams().getRowStart();
    }

    default Integer getRowEnd() {
        return getListParams().getRowEnd();
    }

    default Integer getLength() {
        return getListParams().getLength();
    }

    default List <String> getColKeys() {
        return getListParams().getColKeys();
    }
}

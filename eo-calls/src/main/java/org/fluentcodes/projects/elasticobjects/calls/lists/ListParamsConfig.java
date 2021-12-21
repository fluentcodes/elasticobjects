package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerInteger;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.COL_KEYS;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.F_FILTER;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.LENGTH;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.ROW_END;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.ROW_HEAD;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.ROW_START;

/**
 * A bean class for containing the following values:
 * <ul>
 * <li>rowStart: .</li>
 * <li>rowEnd: .</li>
 * <li>length: ??</li>
 * </ul>
 * Created by werner.diwischek on 03.12.16.
 */
public class ListParamsConfig {
    private final Integer rowStart;
    private final Integer rowEnd;
    private final Integer length;
    private final Integer rowHead;
    private final String filter;
    private final List<String> colKeys;

    public ListParamsConfig(Map<String, Object> properties) {
        this.rowHead = new ShapeTypeSerializerInteger().asObject(properties.get(ROW_HEAD));
        this.rowStart = new ShapeTypeSerializerInteger().asObject(properties.get(ROW_START));
        this.length = new ShapeTypeSerializerInteger().asObject(properties.get(LENGTH));
        this.rowEnd = new ShapeTypeSerializerInteger().asObject(properties.get(ROW_END));
        this.filter = new ShapeTypeSerializerString().asObject(properties.get(F_FILTER));
        String colKeys = new ShapeTypeSerializerString().asObject(properties.get(COL_KEYS));
        if (colKeys != null && !colKeys.isEmpty()) {
            this.colKeys = Arrays.asList(colKeys.split(","));
        } else {
            this.colKeys = new ArrayList<>();
        }
    }

    public boolean hasFilter() {
        return getFilter()!=null && ! getFilter().isEmpty();
    }

    public String getFilter() {
        return filter;
    }

    public Integer getRowHead() {
        return rowHead;
    }

    public boolean hasRowHead() {
        return rowHead != null && rowHead > -1;
    }

    public boolean hasRowStart() {
        return rowStart != null && rowStart > -1;
    }

    public Integer getRowStart() {
        return rowStart;
    }

    public boolean hasRowEnd() {
        return rowEnd!=null && rowEnd>-1;
    }

    public Integer getRowEnd() {
        return rowEnd;
    }

    public Integer getLength() {
        return length;
    }

    public boolean hasLength() {
        return this.length != null && this.length > 0;
    }

    public List <String> getColKeys() {
        return colKeys;
    }

    public boolean hasColKeys() {
        return colKeys!=null && !colKeys.isEmpty();
    }
}

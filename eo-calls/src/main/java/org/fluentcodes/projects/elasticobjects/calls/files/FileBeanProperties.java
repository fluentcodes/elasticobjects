package org.fluentcodes.projects.elasticobjects.calls.files;

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
public class FileBeanProperties implements FilePropertiesInterface {
    public static final String ROW_HEAD = "rowHead";
    public static final String ROW_START = "rowStart";
    public static final String LENGTH = "length";
    public static final String ROW_END = "rowEnd";
    public static final String F_FILTER = "filterRaw";
    public static final String COL_KEYS = "colKeys";


    private Integer rowStart;
    private Integer rowEnd;
    private Integer length;
    private Integer rowHead;
    private String filter;
    private List<String> colKeys;
    private String fieldDelimiter;
    private String rowDelimiter;

    public FileBeanProperties() {
    }

    public FileBeanProperties(FileConfigProperties config) {
        this.colKeys = config.getColKeys();
        this.fieldDelimiter = config.getFieldDelimiter();
        this.filter = config.getFilter();
        this.length = config.getLength();
        this.rowDelimiter = config.getRowDelimiter();
        this.rowEnd = config.getRowEnd();
        this.rowStart = config.getRowStart();
    }

    @Override
    public Integer getRowStart() {
        return rowStart;
    }

    public void setRowStart(Integer rowStart) {
        this.rowStart = rowStart;
    }

    @Override
    public Integer getRowEnd() {
        return rowEnd;
    }

    public void setRowEnd(Integer rowEnd) {
        this.rowEnd = rowEnd;
    }

    @Override
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public Integer getRowHead() {
        return rowHead;
    }

    public void setRowHead(Integer rowHead) {
        this.rowHead = rowHead;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public List<String> getColKeys() {
        return colKeys;
    }

    public void setColKeys(List<String> colKeys) {
        this.colKeys = colKeys;
    }

    @Override
    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public void setFieldDelimiter(String fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
    }

    @Override
    public String getRowDelimiter() {
        return rowDelimiter;
    }

    public void setRowDelimiter(String rowDelimiter) {
        this.rowDelimiter = rowDelimiter;
    }
}

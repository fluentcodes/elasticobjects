package org.fluentcodes.projects.elasticobjects.calls.files;

import java.util.List;

/**
 * A config class for optional file properties.
 * <p>
 * Created by werner.diwischek on 05.01.22.
 */
public class FileConfigProperties implements FilePropertiesInterface {
    public static final String F_DEFAULT_ROW_DELIMITER = "\n";
    public static final String F_DEFAULT_FIELD_DELIMITER = ";";

    private final Integer rowStart;
    private final Integer rowEnd;
    private final Integer length;
    private final Integer rowHead;
    private final String filter;
    private final List<String> colKeys;
    private final String fieldDelimiter;
    private final String rowDelimiter;

    public FileConfigProperties(FileBeanProperties bean) {
        this.colKeys = bean.getColKeys();
        this.fieldDelimiter = bean.getFieldDelimiter();
        this.filter = bean.getFilter();
        this.length = bean.getLength();
        this.rowDelimiter = bean.getRowDelimiter();
        this.rowEnd = bean.getRowEnd();
        this.rowHead = bean.getRowHead();
        this.rowStart = bean.getRowStart();
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public Integer getRowHead() {
        return rowHead;
    }

    @Override
    public Integer getRowStart() {
        return rowStart;
    }

    @Override
    public Integer getRowEnd() {
        return rowEnd;
    }

    @Override
    public Integer getLength() {
        return length;
    }

    @Override
    public List<String> getColKeys() {
        return colKeys;
    }

    @Override
    public String getRowDelimiter() {
        return hasRowDelimiter() ?
                rowDelimiter :
                F_DEFAULT_ROW_DELIMITER;
    }

    @Override
    public boolean hasRowDelimiter() {
        return rowDelimiter!=null && !rowDelimiter.isEmpty();
    }

    @Override
    public String getFieldDelimiter() {
        return hasFieldDelimiter() ?
                fieldDelimiter :
                F_DEFAULT_FIELD_DELIMITER;
    }

    @Override
    public boolean hasFieldDelimiter() {
        return fieldDelimiter!=null && !fieldDelimiter.isEmpty();
    }
}

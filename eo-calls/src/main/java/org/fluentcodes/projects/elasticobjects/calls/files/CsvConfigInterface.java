package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;

public interface CsvConfigInterface extends ConfigInterface {
    public static final String F_FIELD_DELIMITER = "fieldDelimiter";
    public static final String F_ROW_DELIMITER = "rowDelimiter";
    public static final String F_DEFAULT_FIELD_DELIMITER = ";";
    public static final String F_DEFAULT_ROW_DELIMITER = "\n";

    default boolean hasFieldDelimiter() {
        return getFieldDelimiter() != null && !getFieldDelimiter().isEmpty();
    }

    String getFieldDelimiter();

    default boolean hasRowDelimiter() {
        return getRowDelimiter() != null && !getRowDelimiter().isEmpty();
    }

    String getRowDelimiter();
}

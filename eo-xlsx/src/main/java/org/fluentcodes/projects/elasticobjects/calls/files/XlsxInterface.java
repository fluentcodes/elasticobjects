package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;

public interface XlsxInterface extends ConfigInterface {
    String SHEET_NAME = "sheetName";

    default boolean hasSheetName() {
        return getSheetName() != null && !getSheetName().isEmpty();
    }

    String getSheetName();
}

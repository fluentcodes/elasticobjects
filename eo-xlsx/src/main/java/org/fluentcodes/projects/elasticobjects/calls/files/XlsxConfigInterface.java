package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;

import java.util.List;

public interface XlsxConfigInterface extends ConfigConfigInterface {
    String SHEET_NAME = "sheetName";

    List readRaw(ListParams params);

    default boolean hasSheetName() {
        return getSheetName()!=null && !getSheetName().isEmpty();
    }

    default String getSheetName() {
        return hasProperties()?(String)getProperties().get(SHEET_NAME):null;
    }

    default void setSheetName(final String value) {
        if (hasProperties()) {
            getProperties().put(value, SHEET_NAME);
        }
    }
}

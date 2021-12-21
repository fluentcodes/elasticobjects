package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;

import java.util.List;

public interface XlsxInterface extends ConfigInterface {
    String SHEET_NAME = "sheetName";

    List<Object> readRaw(ListParamsBean params);

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

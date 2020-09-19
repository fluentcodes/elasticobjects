package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.calls.lists.PropertiesListAccessor;
import org.fluentcodes.projects.elasticobjects.models.PropertiesAccessor;

import java.util.List;

public interface PropertiesXlsxAccessor extends PropertiesListAccessor {
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
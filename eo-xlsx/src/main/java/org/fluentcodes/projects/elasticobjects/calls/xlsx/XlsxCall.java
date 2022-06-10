package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListCall;

/**
 * Read an Excel sheet specified by fileConfigKey referencing to a {@link FileConfig} configuration.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Wed Nov 11 08:02:52 CET 2020
 */
public abstract class XlsxCall extends ListCall {
    private String sheetName;
    public XlsxCall() {
        super();
    }

    public XlsxCall(final String configKey) {
        super(configKey);
    }

    public XlsxCall(final String configKey,final String fileName) {
        super(configKey, fileName);
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public boolean hasSheetName() {
        return sheetName != null && !sheetName.isEmpty();
    }

    public String fetchSheetName() {
        return hasSheetName()?sheetName:"0";
    }
}

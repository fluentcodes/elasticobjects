package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig {
    protected static final String F_FIELD_DELIMITER = "fieldDelimiter";
    protected static final String F_ROW_DELIMITER = "rowDelimiter";
    protected static final String F_DEFAULT_FIELD_DELIMITER = ";";
    protected static final String F_DEFAULT_ROW_DELIMITER = "\n";

    private final String fieldDelimiter;
    private final String rowDelimiter;
    private final ListParamsConfig listParamsConfig;

    public CsvConfig(ConfigBean configBean, final ConfigMaps configMaps) {
        this((FileBean) configBean, configMaps);
    }

    public CsvConfig(FileBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);

        String myFieldDelimiter = new ShapeTypeSerializerString().asObject(bean.getProperties().get(F_FIELD_DELIMITER));
        this.fieldDelimiter = myFieldDelimiter == null || myFieldDelimiter.isEmpty() ?
                F_DEFAULT_FIELD_DELIMITER :
                myFieldDelimiter;

        String myRowDelimiter = new ShapeTypeSerializerString().asObject(bean.getProperties().get(F_ROW_DELIMITER));
        this.rowDelimiter = myRowDelimiter == null || myRowDelimiter.isEmpty() ?
                F_DEFAULT_ROW_DELIMITER :
                myRowDelimiter;

        this.listParamsConfig = new ListParamsConfig(bean.getProperties());
    }

    public String getRowDelimiter() {
        return rowDelimiter;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public ListParamsConfig getListParamsConfig() {
        return listParamsConfig;
    }

    public boolean hasFieldDelimiter() {
        return getFieldDelimiter() != null && !getFieldDelimiter().isEmpty();
    }

    public boolean hasRowDelimiter() {
        return getRowDelimiter() != null && !getRowDelimiter().isEmpty();
    }

}

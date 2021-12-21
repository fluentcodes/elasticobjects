package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.List;

/**
 * Created by Werner on 09.10.2016.
 */
public class CsvConfig extends FileConfig implements CsvConfigInterface, ListParamsConfigInterface {
    private final String fieldDelimiter;
    private final String rowDelimiter;
    private final ListParamsConfig listParams;

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

        this.listParams = new ListParamsConfig(bean.getProperties());
    }

    @Override
    public String getRowDelimiter() {
        return rowDelimiter;
    }

    @Override
    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    @Override
    public ListParamsConfig getListParams() {
        return listParams;
    }

    @Override
    public String getFilter() {
        return getListParams().getFilter();
    }

    @Override
    public Integer getRowHead() {
        return getListParams().getRowHead();
    }

    @Override
    public Integer getRowStart() {
        return getListParams().getRowStart();
    }

    @Override
    public Integer getRowEnd() {
        return getListParams().getRowEnd();
    }

    @Override
    public Integer getLength() {
        return getListParams().getLength();
    }

    @Override
    public List<String> getColKeys() {
        return getListParams().getColKeys();
    }
}

package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvSimpleReadCall extends FileReadCall implements ListInterface {
    private ListParams listParams;
    public CsvSimpleReadCall()  {
        super();
        listParams = new ListParams();
    }
    public CsvSimpleReadCall(final String configKey)  {
        super(configKey);
        listParams = new ListParams();
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    @Override
    public Object execute(EO eo) {
        return mapEo(eo, readRaw(eo));
    }

    public List readRaw(final EO eo) {
        CsvConfig config = (CsvConfig) init(PermissionType.READ, eo);
        getListParams().merge(config.getProperties());
        String content = super.read(eo);
        if (content == null|| content.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rows = content.split(config.getRowDelimiter());
        List result = new ArrayList<>();

        if (getListParams().hasRowHead(rows.length)) {
            String header = rows[getListParams().getRowHead()];
            if (header!=null && !header.isEmpty()) {
                String[] fields = header.split(config.getFieldDelimiter());
                if (!getListParams().hasColKeys()) {
                    getListParams().setColKeys(Arrays.asList(fields));
                }
            }
        }
        for (int i=0; i<rows.length;i++) {
            String row = rows[i];
            if (row == null || row.isEmpty()) {
                continue;
            }
            if (!getListParams().isRowStart(i)) {
                continue;
            }
            if (!getListParams().isRowEnd(i)) {
                return result;
            }
            if (row == null|| row.isEmpty()) {
                continue;
            }
            String[] fields = row.split(config.getFieldDelimiter());
            List rowEntry = Arrays.asList(fields);
            getListParams().addRowEntry(eo.getConfigsCache(), result, rowEntry);
        }
        return result;
    }
}

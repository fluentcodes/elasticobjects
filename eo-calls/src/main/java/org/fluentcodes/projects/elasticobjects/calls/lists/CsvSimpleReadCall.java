package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*.{javaHeader}|*/

/**
 * Defines a primitive csv file read operation.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 11:16:47 CET 2020
 */
public class CsvSimpleReadCall extends FileReadCall implements ListParamsBeanInterface {
    private ListParamsBean listParams;

    public CsvSimpleReadCall() {
        super();
        listParams = new ListParamsBean();
    }

    public CsvSimpleReadCall(final String configKey) {
        super(configKey);
        listParams = new ListParamsBean();
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        return mapEo(eo, readRaw(eo));
    }

    public List readRaw(final EOInterfaceScalar eo) {
        FileConfig config = init(PermissionType.READ, eo);
        listParams.merge(config);
        final String rowDelimiter = config.getProperties().getRowDelimiter();
        final String fieldDelimiter = config.getProperties().getFieldDelimiter();
        String content = super.read(eo);
        if (content == null || content.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rows = content.split(rowDelimiter);
        List result = new ArrayList<>();

        if (getListParams().hasRowHead(rows.length)) {
            String header = rows[getListParams().getRowHead()];
            if (header != null && !header.isEmpty()) {
                String[] fields = header.split(fieldDelimiter);
                if (!getListParams().hasColKeys()) {
                    getListParams().setColKeys(Arrays.asList(fields));
                }
            }
        }
        for (int i = 0; i < rows.length; i++) {
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
            if (row == null || row.isEmpty()) {
                continue;
            }
            String[] fields = row.split(fieldDelimiter);
            List rowEntry = Arrays.asList(fields);
            getListParams().addRowEntry(eo.getConfigMaps(), result, rowEntry);
        }
        return result;
    }
    /*.{javaAccessors}|*/

    /**
     * Parameters of type {@link ListParamsBean} for list type read call operations like {@link CsvSimpleReadCall}.
     */
    public CsvSimpleReadCall setListParams(ListParamsBean listParams) {
        this.listParams = listParams;
        return this;
    }

    @Override
    public ListParamsBean getListParams() {
        return this.listParams;
    }

    public boolean hasListParams() {
        return listParams != null;
    }
    /*.{}.*/
}

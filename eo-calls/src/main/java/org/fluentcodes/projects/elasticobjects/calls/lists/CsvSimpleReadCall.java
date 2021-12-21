package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.CsvConfig;
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
    /*.{}.*/

    /*.{javaStaticNames}|*/
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private ListParamsBean listParams;

    /*.{}.*/
    public CsvSimpleReadCall() {
        super();
        listParams = new ListParamsBean();
    }

    public CsvSimpleReadCall(final String configKey) {
        super(configKey);
        listParams = new ListParamsBean();
    }

    @Override
    public Object execute(final IEOScalar eo) {
        return mapEo(eo, readRaw(eo));
    }

    public List readRaw(final IEOScalar eo) {
        CsvConfig config = (CsvConfig) init(PermissionType.READ, eo);
        getListParams().merge(config.getListParams());
        String content = super.read(eo);
        if (content == null || content.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rows = content.split(config.getRowDelimiter());
        List result = new ArrayList<>();

        if (getListParams().hasRowHead(rows.length)) {
            String header = rows[getListParams().getRowHead()];
            if (header != null && !header.isEmpty()) {
                String[] fields = header.split(config.getFieldDelimiter());
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
            String[] fields = row.split(config.getFieldDelimiter());
            List rowEntry = Arrays.asList(fields);
            getListParams().addRowEntry(eo.getConfigMaps(), result, rowEntry);
        }
        return result;
    }
    /*.{javaAccessors}|*/

    /**
     * Parameters of type {@link ListParamsBean} for list type read call operations like {@link CsvSimpleReadCall}.
     */
    @Override
    public CsvSimpleReadCall setListParams(ListParamsBean listParams) {
        this.listParams = listParams;
        return this;
    }

    @Override
    public ListParamsBean getListParams() {
        return this.listParams;
    }

    @Override
    public boolean hasListParams() {
        return listParams != null;
    }
    /*.{}.*/
}

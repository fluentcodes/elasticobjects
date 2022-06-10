package org.fluentcodes.projects.elasticobjects.calls.lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfigProperties;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerLocalDateTime;

/**
 * Defines a primitive csv file write operation.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 11:26:51 CET 2020
 */
public class CsvSimpleWriteCall extends CallImpl implements ListParamsBeanInterface {
    private ListParamsBean listParams;
    private FileWriteCall fileWriteCall;
    private FileConfig config;
    private String fileConfigKey;
    private String fileName;
    private String rowDelimiter;
    private String fieldDelimiter;
    private List<String> colKeys;

    public CsvSimpleWriteCall() {
        super();
        listParams = new ListParamsBean();
    }

    public CsvSimpleWriteCall(final String fileConfigKey) {
        this();
        this.fileConfigKey = fileConfigKey;
    }
    public CsvSimpleWriteCall(final String fileConfigKey, String fileName) {
        this(fileConfigKey);
        this.fileName = fileName;
    }

    @Override
    public String execute(EOInterfaceScalar eo) {
        if (fileConfigKey == null) {
            fileConfigKey = "TREE";
        }
        if (hasFileName()) {
            fileWriteCall = new DirectoryWriteCall(fileConfigKey, fileName);
        } else {
            fileWriteCall = new FileWriteCall(fileConfigKey);
        }
        config = (FileConfig) eo.getConfigMaps().find(FileConfig.class, getFileConfigKey());
        if (eo.isEmpty()) {
            throw new EoException("No values at path " + eo.getPathAsString());
        }
        getListParams().merge(config);
        if (eo.isList()) {
            init(eo.getEo("0"));
        } else if (eo.isMap()) {
            String key = new ArrayList<String>(((EO) eo).keys()).get(0);
            init(eo.getEo("0"));
        } else {
            throw new EoException("Nor List nor Map at path " + eo.getPathAsString());
        }
        fileWriteCall.setContent(create(eo));
        return fileWriteCall.execute(eo);
    }

    public FileWriteCall getFileWriteCall() {
        return fileWriteCall;
    }

    @Override
    public void setByParameter(String values) {

    }

    public String create(EOInterfaceScalar eo) {
        StringBuilder buffer = new StringBuilder();
        if (listParams.hasRowHead()) {
            for (int i = 0; i < listParams.getRowHead(); i++) {
                buffer.append(rowDelimiter);
            }
            buffer.append(colKeys.stream().collect(Collectors.joining(fieldDelimiter)));
            buffer.append(rowDelimiter);
        }
        for (String key : ((EO) eo).keys()) {
            EOInterfaceScalar row = eo.getEo(key);
            if (row.isEmpty()) {
                continue;
            }
            addRow(buffer, row);
        }
        return buffer.toString();
    }

    private void addRow(StringBuilder buffer, EOInterfaceScalar row) {
        if (row.isEmpty()) {
            //buffer.append(rowDelimiter);
            return;
        }
        for (String key : colKeys) {
            if (!row.hasEo(key)) {
                buffer.append(fieldDelimiter);
                continue;
            }
            EOInterfaceScalar entry = row.getEo(key);
            if (entry.isEmpty()) {
                buffer.append(fieldDelimiter);
                continue;
            }
            if (entry.isScalar()) {
                if (entry.getModelClass() == LocalDateTime.class) {
                    buffer.append(
                        ShapeTypeSerializerLocalDateTime.defaultDateStringFormat((LocalDateTime) entry.get()));
                }
                else {
                    buffer.append(entry.get());
                }
            } else {
                buffer.append(new EOToJSON().toJson(entry));
            }
            buffer.append(fieldDelimiter);
        }
        buffer.append(rowDelimiter);
    }

    /**
     * Parameters of type {@link ListParamsBean} for list type read call operations like {@link CsvSimpleReadCall}.
     */
    public CsvSimpleWriteCall setListParams(ListParamsBean listParams) {
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

    public String getFileConfigKey() {
        return fileConfigKey;
    }

    public CsvSimpleWriteCall setFileConfigKey(String fileConfigKey) {
        this.fileConfigKey = fileConfigKey;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public CsvSimpleWriteCall setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public boolean hasFileName() {
        return fileName != null && !fileName.isEmpty();
    }

    public String getRowDelimiter() {
        return rowDelimiter;
    }

    public void setRowDelimiter(String rowDelimiter) {
        this.rowDelimiter = rowDelimiter;
    }

    public boolean hasRowDelimiter() {
        return rowDelimiter != null && !rowDelimiter.isEmpty();
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public CsvSimpleWriteCall setFieldDelimiter(String fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
        return this;
    }

    public boolean hasFieldDelimiter() {
        return fieldDelimiter != null && !fieldDelimiter.isEmpty();
    }

    public boolean init(EOInterfaceScalar eo) {
        mergeColKeys(eo);
        mergeRowDelimiter();
        mergeFieldDelimiter();
        return true;
    }

    private void mergeFieldDelimiter() {
        if (!hasFieldDelimiter()) {
            if (config.getProperties().hasFieldDelimiter()) {
                fieldDelimiter = config.getProperties().getFieldDelimiter();
            } else {
                fieldDelimiter = FileConfigProperties.F_DEFAULT_FIELD_DELIMITER;
            }
        }
    }

    private void mergeRowDelimiter() {
        if (!hasRowDelimiter()) {
            if (config.getProperties().hasRowDelimiter()) {
                rowDelimiter = config.getProperties().getRowDelimiter();
            } else {
                rowDelimiter = FileConfigProperties.F_DEFAULT_ROW_DELIMITER;
            }
        }
    }

    public List<String> getColKeys() {
        return colKeys;
    }

    public CsvSimpleWriteCall setColKeys(List<String> colKeys) {
        this.colKeys = colKeys;
        return this;
    }

    public boolean hasColKeys() {
        return this.colKeys != null && !this.colKeys.isEmpty();
    }

    private void mergeColKeys(EOInterfaceScalar eo) {
        if (!hasColKeys()) {
            if (config.getProperties().hasColKeys()) {
                colKeys = config.getProperties().getColKeys();
            } else if (eo.isObject()) {
                colKeys = new ArrayList(eo.getModel().getFieldKeys());
            } else {
                colKeys = new ArrayList(((EO) eo).keys());
            }
        }
    }
}

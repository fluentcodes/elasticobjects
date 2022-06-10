package org.fluentcodes.projects.elasticobjects.calls.lists;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Prepare {@link FileConfig} configuration.
 */
public abstract class ListCall extends CallImpl implements ListParamsBeanInterface {
    private ListParamsBean listParams;

    private ModelConfig modelConfig;
    private FileConfig fileConfig;
    private HostConfig hostConfig;

    private String modelConfigKey;
    private String hostConfigKey;
    private String fileConfigKey;
    private String fileName;
    private List<String> colKeys;
    private FileCall fileCall;

    public ListCall() {
        super();
        listParams = new ListParamsBean();
    }

    public ListCall(final String fileConfigKey) {
        this();
        this.fileConfigKey = fileConfigKey;
    }

    public ListCall(final String fileConfigKey, String fileName) {
        this(fileConfigKey);
        this.fileName = fileName;
    }

    public boolean init(EOInterfaceScalar eo) {
        if (modelConfigKey == null) {
            modelConfigKey = "Map";
        }
        if (fileConfigKey == null) {
            fileConfigKey = "TREE";
        }
        if (hostConfigKey == null) {
            hostConfigKey = "localhost";
        }
        modelConfig = eo.getConfigMaps().findModel(modelConfigKey);
        hostConfig = (HostConfig) eo.getConfigMaps().find(HostConfig.class, hostConfigKey);
        fileConfig = (FileConfig) eo.getConfigMaps().find(FileConfig.class, fileConfigKey);
        getListParams().merge(fileConfig);
        return true;
    }

    public URL createURL() {
        return fileConfig.createUrl(hostConfig);
    }

    public String getUrlPath() {
        if (hasFileName()) {
            return fileConfig.getFilePath() + "/" + getFileName();
        }
        return fileConfig.getUrlPath(hostConfig);
    }

    public URL findURL() {
        return fileConfig.findUrl(hostConfig);
    }

    @Override
    public void setByParameter(String values) {

    }

    /**
     * Parameters of type {@link ListParamsBean} for list type read call operations like {@link CsvSimpleReadCall}.
     */
    public ListCall setListParams(ListParamsBean listParams) {
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

    public ModelConfig getModelConfig() {
        return modelConfig;
    }

    public String getModelConfigKey() {
        return modelConfigKey;
    }

    public void setModelConfigKey(String modelConfigKey) {
        this.modelConfigKey = modelConfigKey;
    }

    public String getHostConfigKey() {
        return hostConfigKey;
    }

    public void setHostConfigKey(String hostConfigKey) {
        this.hostConfigKey = hostConfigKey;
    }

    public HostConfig getHostConfig() {
        return hostConfig;
    }

    public String getFileConfigKey() {
        return fileConfigKey;
    }

    public ListCall setFileConfigKey(String fileConfigKey) {
        this.fileConfigKey = fileConfigKey;
        return this;
    }

    public FileConfig getFileConfig() {
        return fileConfig;
    }

    public String getFileName() {
        return fileName;
    }

    public ListCall setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public boolean hasFileName() {
        return fileName != null && !fileName.isEmpty();
    }

    public boolean initListParams(EOInterfaceScalar eo) {
        mergeColKeys(eo);
        return true;
    }

    public List<String> getColKeys() {
        return getListParams().getColKeys();
    }

    public ListCall setColKeys(List<String> colKeys) {
        getListParams().setColKeys(colKeys);
        return this;
    }

    public boolean hasColKeys() {
        return getListParams().hasColKeys();
    }

    public boolean hasRowHead() {
        return listParams.hasRowHead();
    }



    protected void mergeColKeys(EOInterfaceScalar eo) {
        if (!hasColKeys()) {
            if (fileConfig.getProperties().hasColKeys()) {
                getListParams().setColKeys(fileConfig.getProperties().getColKeys());
            } else if (eo.isObject()) {
                getListParams().setColKeys(new ArrayList(eo.getModel().getFieldKeys()));
            } else {
                getListParams().setColKeys(new ArrayList(((EO) eo).keys()));
            }
        }
    }

    public void mapToTarget(EOInterfaceScalar eo, List<Map<String, Object>> result) {
        EOInterfaceScalar targetEo = eo;
        if (hasTargetPath()) {
            targetEo = ((EoChild)eo).createChild(getTargetPath(), null);
        }
        if (result == null || result.isEmpty()) {
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> entry = result.get(i);
            String key = Integer.valueOf(i).toString();
            targetEo.set(entry, key);
        }
    }
}

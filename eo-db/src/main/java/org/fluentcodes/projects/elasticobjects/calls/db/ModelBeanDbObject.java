package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;

/**
 * Created by Werner on 11.12.2020.
 */
public class ModelBeanDbObject extends PermissionBean {
    public static final String SQL_LIST = "sqlList";
    public static final String DEFAULT_HOST_CONFIG_KEY = "defaultHostConfigKey";
    private String hostConfigKey;
    private String modelConfigKey;

    public ModelBeanDbObject() {
        super();
        defaultConfigModelKey();
    }

    public String getHostConfigKey() {
        return hostConfigKey;
    }

    public void setHostConfigKey(String hostConfigKey) {
        this.hostConfigKey = hostConfigKey;
    }

    public String getModelConfigKey() {
        return modelConfigKey;
    }

    public void setModelConfigKey(String modelConfigKey) {
        this.modelConfigKey = modelConfigKey;
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(ModelConfigDbObject.class.getSimpleName());
    }
}

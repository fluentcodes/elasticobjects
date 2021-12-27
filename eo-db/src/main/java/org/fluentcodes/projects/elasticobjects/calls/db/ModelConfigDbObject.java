package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;

/**
 * Created by Werner on 01.11.2020.
 */
public class ModelConfigDbObject extends PermissionConfig {
    public final static String HOST_CONFIG_KEY = "hostConfigKey";
    private final String hostConfigKey;
    private final String modelConfigKey;
    private final HostConfig hostConfig;
    private final ModelConfig modelConfig;

    public ModelConfigDbObject(ConfigBean bean, final ConfigMaps configMaps) {
        this((ModelBeanDbObject) bean, configMaps);
    }

    public ModelConfigDbObject(ModelBeanDbObject bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        this.hostConfigKey = bean.getHostConfigKey();
        this.modelConfigKey = bean.getModelConfigKey();
        hostConfig = (HostConfig)configMaps.find(HostConfig.class, hostConfigKey);
        modelConfig = configMaps.findModel(modelConfigKey);
    }

    public String getHostConfigKey() {
        return hostConfigKey;
    }

    public boolean hasHostConfigKey() {
        return hostConfigKey != null && !hostConfigKey.isEmpty();
    }
}

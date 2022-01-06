package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;


/**
 * Created by Werner on 05.04.2022.
 */
public class DbModelConfig extends PermissionConfig implements DbModelInterface {
    private final String modelKey;
    private final String dbConfigKey;
    private final ModelConfig modelConfig;
    private final DbConfig dbConfig;

    public DbModelConfig(final ConfigBean configBean, final ConfigMaps configMaps) {
        this((DbModelBean)configBean, configMaps);
    }

    public DbModelConfig(final DbModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        this.modelKey = bean.getModelKey();
        this.dbConfigKey = bean.getDbConfigKey();
        modelConfig = configMaps.findModel(modelKey);
        dbConfig = (DbConfig) configMaps.find(HostConfig.class, dbConfigKey);
    }

    @Override
    public String getModelKey() {
        return modelKey;
    }

    @Override
    public String getDbConfigKey() {
        return dbConfigKey;
    }

    public ModelConfig getModelConfig() {
        return modelConfig;
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }
}

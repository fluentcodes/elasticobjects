package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Configuration for model based database sql queries.
 */
public class DbModelsConfig extends PermissionConfig implements DbModelsInterface {
    private final List<String> dbModelKeys;
    private final Map<Class<?>, DbModelConfig> dbModelConfigMap;
    private final String dbConfigKey;
    private final DbConfig dbConfig;

    public DbModelsConfig(final ConfigBean configBean, final ConfigMaps configMaps) {
        this((DbModelsBean)configBean, configMaps);
    }

    public DbModelsConfig(final DbModelsBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        if (bean.hasDbModelKeys()) {
            this.dbModelKeys = bean.getDbModelKeys();
        }
        else {
            this.dbModelKeys = new ArrayList<>(configMaps.getConfigKeys(DbModelConfig.class));
        }
        this.dbConfigKey = bean.getDbConfigKey();
        dbConfig = (DbConfig) configMaps.find(HostConfig.class, dbConfigKey);
        dbModelConfigMap = new HashMap<>();
        for (String key: dbModelKeys) {
            DbModelConfig modelConfig = (DbModelConfig) configMaps.find(DbModelConfig.class, key);
            dbModelConfigMap.put(modelConfig.getModelConfig().getModelClass(), modelConfig);
        }
    }

    public Set<Class<?>> getDbModelClassSet() {
        return dbModelConfigMap.keySet();
    }

    @Override
    public List<String> getDbModelKeys() {
        return dbModelKeys;
    }

    @Override
    public String getDbConfigKey() {
        return dbConfigKey;
    }

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public boolean hasDbModelConfig(final Class configClass) {
        return dbModelConfigMap.containsKey(configClass);
    }

    public DbModelConfig getDbModelConfig(final Class configClass) {
        if (!dbModelConfigMap.containsKey(configClass)) {
            throw new EoException("Could not find configClass '" + configClass.getSimpleName() + "'");
        }
        return dbModelConfigMap.get(configClass);
    }
}

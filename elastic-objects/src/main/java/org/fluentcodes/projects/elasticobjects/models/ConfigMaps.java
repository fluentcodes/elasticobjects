package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfigMap;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfigMap;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * cache map for configurations
 *
 * @author Werner Diwischek
 * @since 13.10.2016.
 */
public class ConfigMaps {
    private final Map<Class, ConfigConfigMapInterface> configMaps;
    private final Scope scope;

    public ConfigMaps() {
        this(Scope.DEV);
    }

    public ConfigMaps(final Scope scope) {
        configMaps = new LinkedHashMap<>();
        this.scope = scope == null ? Scope.DEV : scope;
        //configMaps.put(ModelConfig.class, new EOConfigMapModels(this, scope));
        configMaps.put(ModelConfig.class, new ModelConfigsMap(scope));
        if (scope == Scope.DEV) {
            return;
        }
        configMaps.put(HostConfig.class, new HostConfigMap(scope));
        configMaps.put(FileConfig.class, new FileConfigMap(scope));
    }

    public Scope getScope() {
        return scope;
    }

    public Set<Class> getKeys() {
        return configMaps.keySet();
    }

    public List<String> getKeysAsString() {
        return configMaps.keySet().stream().map(x -> x.getSimpleName()).collect(Collectors.toCollection(ArrayList::new));
    }

    public Object find(final Class configClass, final String configKey)  {
        if (configKey == null || configKey.isEmpty()) throw new EoException("Config key is empty for finder '" + configClass.getSimpleName() + "'!");
        ConfigConfigInterface config = getConfigMap(configClass).find(configKey);
        return config;
    }
    public boolean hasKey(final Class configClass, final String configKey)  {
        if (configKey == null || configKey.isEmpty()) throw new EoException("Config key is empty for finder '" + configClass.getSimpleName() + "'!");
        return  getConfigMap(configClass).hasKey(configKey);
    }

    public boolean isEmpty(final Class configClass) {
        return getConfigMap(configClass).isEmpty();
    }

    public Set<String> getConfigKeys(final String configName)  {
        return getConfigMap(findConfigClass(configName)).getKeys();
    }

    public Set<String> getConfigKeys(Class configClass) {
        return getConfigMap(configClass).getKeys();
    }

    public Set<String> getConfigKeys(Class configClass, Expose expose) {
        return getConfigMap(configClass).getKeys(expose);
    }

    private ConfigConfigMapInterface getConfigMap(Class configClass) {
        if (configClass == null) throw new EoException("Config class is null!");
        if (!this.configMaps.containsKey(configClass)) configMaps.put(configClass, new ConfigConfigMap(scope, configClass));
        return configMaps.get(configClass);
    }

    private ConfigConfigMapInterface getConfigMap(final String configName)  {
        return getConfigMap(findConfigClass(configName));
    }

    private Class findConfigClass(final String configName)  {
        if (configName == null || configName.isEmpty()) throw new EoException("Null search name for configMap entry");
        for (Class configClass: configMaps.keySet()) {
            if (configName.equals(configClass.getSimpleName())) return configClass;
        }
        throw new EoException("Could not find search name '" + configName + "'for configMap entry");
    }

    public ModelConfig findModel(final String modelKey)  {
        return (ModelConfig) find(ModelConfig.class, modelKey);
    }
    public ModelConfig findModel(final Class modelClass)  {
        return findModel(modelClass.getSimpleName());
    }
    public ModelConfig findModel(final Object modelValue)  {
        if (modelValue == null) {
            throw new EoException("null model value");
        }
        return findModel(modelValue.getClass());
    }

    public FileConfig findFile(final String key)  {
        return (FileConfig) find(FileConfig.class, key);
    }

    public boolean hasFile(final String key)  {
        return hasKey(FileConfig.class, key);
    }

    public HostConfig findHost(final String key)  {
        return (HostConfig) find(HostConfig.class, key);
    }



}

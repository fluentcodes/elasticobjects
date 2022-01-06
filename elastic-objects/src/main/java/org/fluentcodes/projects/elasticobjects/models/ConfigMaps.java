package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

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
    private final Map<Class<? extends Config>, Map<String, Config>> configMaps;
    private final Scope scope;
    private boolean modelFinished;

    public ConfigMaps() {
        this(Scope.DEV);
    }

    public ConfigMaps(final Scope scope) {
        configMaps = new LinkedHashMap<>();
        this.scope = scope;
        if (scope == Scope.DEV) {
            this.modelFinished = true;
            configMaps.put(ModelConfig.class, new ModelFactoryBasic(this).createImmutableConfig());
            return;
        }
        configMaps.put(ModelConfig.class, new ModelFactoryAll(this).createImmutableConfig());
        configMaps.put(FieldConfig.class, new FieldFactory(this).createImmutableConfig());
        this.modelFinished = true;
    }

    public Scope getScope() {
        return scope;
    }

    public boolean isModelFinished() {
        return modelFinished;
    }

    public Set<Class<? extends Config>> getKeys() {
        return configMaps.keySet();
    }

    public List<String> getKeysAsString() {
        return configMaps.keySet().stream().map(x -> x.getSimpleName()).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Find a certain configuration within the configuration map for configClass.
     * @param configClass the associated config class
     * @param configKey   the config key to find
     * @return The configuration class
     */
    public Object find(final Class<? extends Config> configClass, final String configKey)  {
        if (configKey == null || configKey.isEmpty()) {
            throw new EoException("Config key is empty within '" + configClass.getSimpleName() + "'!");
        }
        if (!getConfigMap(configClass).containsKey(configKey)) {
            throw new EoException("Could not find config key '" + configKey + "' within '" + configClass.getSimpleName() + "'!");
        }
        return getConfigMap(configClass).get(configKey);
    }

    public boolean hasKey(final Class configClass, final String configKey)  {
        if (configKey == null || configKey.isEmpty()) throw new EoException("Config key is empty for finder '" + configClass.getSimpleName() + "'!");
        return  getConfigMap(configClass).containsKey(configKey);
    }

    public boolean isEmpty(final Class configClass) {
        return getConfigMap(configClass).isEmpty();
    }

    public Set<String> getConfigKeys(final String configName)  {
        return getConfigKeys(findConfigClass(configName));
    }

    public Set<String> getConfigKeys(Class configClass) {
        return getConfigMap(configClass).keySet();
    }

    public Set<String> getConfigKeys(Class configClass, Expose expose) {
        return getConfigMap(configClass).keySet();
    }

    private Map<String, Config> getConfigMap(Class<? extends Config> configClass) {
        if (configClass == null) throw new EoException("Config class is null!");
        if (!this.configMaps.containsKey(configClass)) {
            String factoryClassName = configClass.getName().replace("Config", "Factory") ;
            Class<?> factoryClass = null;
            try {
                factoryClass = Class.forName(factoryClassName);
            } catch (ClassNotFoundException e) {
                throw new EoException(e);
            }
            try {
                ConfigFactory configFactory = (ConfigFactory) factoryClass.getConstructor(ConfigMaps.class).newInstance(this);
                configMaps.put(configClass, configFactory.createImmutableConfig());
            } catch (EoException| EoInternalException e) {
                throw e;
            }
            catch (Exception exceptionOther) {
                throw new EoException("Problem initalizing configurations " + factoryClassName + " " + exceptionOther.getMessage());
            }

        }
        return configMaps.get(configClass);
    }


    private Class<?> findConfigClass(final String configName)  {
        if (configName == null || configName.isEmpty()) throw new EoException("Null search name for configMap entry");
        for (Class<?> configClass: configMaps.keySet()) {
            if (configName.equals(configClass.getSimpleName())) return configClass;
        }
        throw new EoException("Could not find search name '" + configName + "'for configMap entry");
    }

    public ModelConfig findModel(final String modelKey)  {
        return (ModelConfig) find(ModelConfig.class, modelKey);
    }
    public ModelConfig findModel(final Class<?> modelClass)  {
        return (ModelConfig) find(ModelConfig.class, modelClass.getSimpleName());
    }
    public ModelConfig findModel(final Object modelValue)  {
        if (modelValue == null) {
            throw new EoException("null model value");
        }
        return findModel(modelValue.getClass());
    }

    public String toString(Class<? extends Config> configClass) {
        if (!isModelFinished()) {
            return "Not finshed yet.";
        }

        Map<String, Config> configMap = getConfigMap(configClass);

        EoRoot cloneMap = EoRoot.ofClass(this, Map.class);
        cloneMap.setSerializationType(JSONSerializationType.STANDARD);
        cloneMap.map(configMap);
        return new EOToJSON().toJson(cloneMap);
    }

}

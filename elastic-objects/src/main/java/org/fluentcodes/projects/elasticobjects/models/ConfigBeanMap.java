package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IORuntimeException;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ConfigBeanMap<T extends ConfigConfigInterface> implements ConfigConfigMapInterface<T> {
    public static final Logger LOG = LogManager.getLogger(ConfigBeanMap.class);
    private final Map<String, T> configMap;
    private final Scope scope;
    private final Class<? extends ConfigBean> beanClass;
    private final Class<? extends ConfigConfig> defaultConfigClass;

   ConfigBeanMap() {
       this.scope = Scope.DEV;
       this.defaultConfigClass = ModelConfig.class;
       this.beanClass = ModelBean.class;
       this.configMap = new TreeMap<>();
   }

    protected ConfigBeanMap(final Scope scope, final Class<? extends ConfigConfig> configClass)  {
        this(scope, findBean(configClass), configClass);
    }

    static Class<? extends ConfigBean> findBean(final Class<? extends ConfigConfig> configClass) {
        try {
            return (Class<? extends ConfigBean>)Class.forName(configClass.getName().replaceAll("Config$","Bean"));
        } catch (ClassNotFoundException e) {
            throw new EoInternalException("Problem finding bean class from configClass + '" + configClass.getSimpleName() + "':" + e.getMessage());
        }
    }

    protected ConfigBeanMap(final Scope scope, final Class<? extends ConfigBean> beanClass, final Class<? extends ConfigConfig> configClass)  {
       this.beanClass = beanClass;
       this.defaultConfigClass = configClass;

        if (scope == null) {
            this.scope = Scope.DEV;
        }
        else {
            this.scope = scope;
        }
        this.configMap = new TreeMap<>();
        if (this.scope == Scope.DEV) {
            return;
        }
        initMapByJson();
    }

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    void initMapByJson() {
        EO eoRoot = EoRoot.ofClass(new ConfigMaps(), readConfigFiles());
        for (String key: eoRoot.keys()) {
            configMap.put(key, createBean(key, (Map) eoRoot.get(key)));
        }
    }

    public Map<String, ConfigConfigInterface> createConfigMap() {
        Map<String, ConfigConfigInterface> configInterfaceMap = new TreeMap<>();
        for (String key: configMap.keySet()) {
            configInterfaceMap.put(key, ((ConfigBean)configMap.get(key)).createConfig(defaultConfigClass));
        }
        return configInterfaceMap;
    }

    protected T createBean(final String naturalId, final Map valueMap) {
        //throw new EoInternalException("ConfigClass '" + defaultConfigClass.getSimpleName() + "' should overwrite addBean method.");
        try {
            Constructor constructor = beanClass.getConstructor(String.class, Map.class);
            return (T) constructor.newInstance(naturalId, valueMap);
        }
        catch (Exception e) {
            throw new EoInternalException("Bean Class '" + beanClass.getSimpleName() + "' problems initiate constructor via reflection for '" + naturalId + "': " + e.getMessage());
        }
    }

    protected void addBean(final T bean) {
        addBean(bean.getNaturalId(), bean);
    }
    protected void addBean(final String naturalId, final T bean) {
        configMap.put(naturalId, bean);
    }

    public Map<String, T> getBeanMap() {
        return configMap;
    }

    public Scope getScope() {
        return scope;
    }

    public Class<? extends ConfigConfig> getDefaultConfigClass() {
        return defaultConfigClass;
    }

    @Override
    public T find(String key)  {
        if (key == null || key.isEmpty()) {
            throw new EoException("Key for find in '" + defaultConfigClass.getSimpleName() + "' is empty!");
        }
        if (!configMap.containsKey(key)) {
            throw new EoException("Could not find config entry for '" + key + "' in config class '" + defaultConfigClass.getSimpleName() + "'");
        }
        return configMap.get(key);
    }

    public Set<String> getKeys() {
        return configMap.keySet();
    }

    public boolean hasKey(final String key) {
        return configMap.containsKey(key);
    }

    public boolean isEmpty() {
        return configMap.isEmpty();
    }

    protected final String readConfigFiles() {
        return readConfigFiles(defaultConfigClass.getSimpleName() + ".json");
    }

    /**
     * Read all files in the classpath and concatenate them so its a valid json file.
     * @param fileName A file name for JSON configurations.
     * @return the concatenated file content.
     */
    protected final static String readConfigFiles(final String fileName) {
        try {
            List<String> configContentList = new IOString()
                    .setFileName(fileName)
                    .readStringList();
            if (configContentList.isEmpty()) {
                LOG.warn("No configuration file '" + fileName + "' found in the classpath!");
                return "";
            }

            if (configContentList.size() == 1) {
                return configContentList.get(0);
            }
            StringBuilder concatenate = new StringBuilder();
            concatenate.append(configContentList.get(0)
                    .replaceAll("\\}$",","));
            for (int i = 1; i<configContentList.size()-1; i++) {
                concatenate.append(configContentList.get(i)
                        .replaceAll("\\}$",",")
                        .replaceAll("^\\{",""));
            }
            concatenate.append(configContentList.get(configContentList.size()-1)
                    .replaceAll("^\\{",""));
            return concatenate.toString();
        }
        catch (IORuntimeException e) {
            LOG.warn("No configuration file '" + fileName + "' found in the classpath!");
            return "";
        }
    }
}

package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatList;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Werner on 22.10.2021.
 */

public abstract class ConfigFactory<T extends ConfigBean, U extends Config> {
    public static final Logger LOG = LogManager.getLogger(ConfigFactory.class);
    private final Scope scope;
    private final Class<? extends Config> configClass;
    private final Class<? extends ConfigBean> beanClass;
    private final ConfigMaps configMaps;

    protected ConfigFactory(final ConfigMaps configMaps, final Class<? extends ConfigBean> beanClass, Class<? extends Config> configClass) {
        this.configMaps = configMaps;
        this.scope = configMaps.getScope();
        this.configClass = configClass;
        this.beanClass = beanClass;
    }

    protected ConfigMaps getConfigMaps() {
        return configMaps;
    }

    public Map<String, Config> createImmutableConfig() {
        return new UnmodifiableMap<>(createConfigMap());
    }

    /**
     * Default init map.
     *
     * @return the expanded final configurations.
     */
    public List<T> createBeanList() {
        List<T> beanMap = new IOClasspathEOFlatList<T>
                (configMaps, configClass.getSimpleName() + ".json", beanClass)
                .read();
        return beanMap;
    }

    public Map<String, U> createConfigMap() {
        List<T> beanMap = createBeanList();
        Map<String, U> configMap = new TreeMap<>();
        try {
            for (T entry : beanMap) {
                if (!getScope().filter(entry)) {
                    continue;
                }
                U config = (U) entry.createConfig(configMaps);
                configMap.put(entry.getNaturalId(), config);
            }
        }
        catch (EoInternalException| EoException e) {
            throw e;
        }
        catch (Exception e) {
            throw new EoInternalException(e);
        }
        return configMap;
    }

    public Scope getScope() {
        return scope;
    }

    public Map<String, T> transformToBeanMap(List<? extends T> beanList) {
        Map<String, T> beanMap = new TreeMap<>();
        for (T bean: beanList) {
            beanMap.put(bean.getNaturalId(), bean);
        }
        return beanMap;
    }

    public Map<String, T> createBeanMap() {
        return transformToBeanMap(createBeanList());
    }
}

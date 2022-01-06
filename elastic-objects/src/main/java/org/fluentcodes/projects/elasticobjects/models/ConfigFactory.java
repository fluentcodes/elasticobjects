package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatMap;

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
    public Map<String, T> createBeanMap() {
        Map<String, T> beanMap = new IOClasspathEOFlatMap<T>
                (configMaps, configClass.getSimpleName() + ".json", beanClass)
                .read();
        for (Map.Entry<String, T> entry : beanMap.entrySet()) {
            entry.getValue().setNaturalId(entry.getKey());
        }
        return beanMap;
    }

    public Map<String, U> createConfigMap() {
        Map<String, T> beanMap = createBeanMap();
        Map<String, U> configMap = new TreeMap<>();
        try {
            for (Map.Entry<String, T> entry : beanMap.entrySet()) {
                Optional<String> filterScope = getScope().filter(entry.getKey());
                if (!filterScope.isPresent()) {
                    continue;
                }
                U config = (U) entry.getValue().createConfig(configMaps);
                configMap.put(entry.getKey(), config);
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
}

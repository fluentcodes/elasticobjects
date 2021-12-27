package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatMap;

import java.util.Map;

/**
 *
 * Created by Werner on 31.10.2021.
 */

public class ModelFactoryFromConfigurations extends ModelFactory {

    public ModelFactoryFromConfigurations(final ConfigMaps configMaps) {
        super(configMaps);
    }

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    @Override
    public Map<String, ModelBean> createBeanMap() {
        Map<String, ModelBean> beanMap = new ModelFactoryBasic(getConfigMaps()).createBeanMap();
        addModelBeans(beanMap);
        return beanMap;
    }

    protected void addModelBeans(Map<String, ModelBean> beanMap) {
        ConfigMaps devConfigMaps = new ConfigMaps(Scope.DEV);
        Map<String, Map<String, Object>> modelMapValues = new IOClasspathEOFlatMap<Map<String,Object>>
                (devConfigMaps, "ModelConfig.json", Map.class)
                .read();
        Map<String, FieldBean> fieldBeanMap = new FieldFactory(devConfigMaps).createBeanMap();
        for (Map.Entry<String, Map<String,Object>> entry: modelMapValues.entrySet()) {
            ModelBean modelBean = new ModelBean(entry.getValue());
            if (!modelBean.hasModelKey()) {
                LOG.warn("No modelKey defined for {}.", entry.getKey());
                continue;
            }
            modelBean.setNaturalId(entry.getKey());
            beanMap.put(entry.getKey(), modelBean);
        }
        for  (Map.Entry<String, ModelBean> entry: beanMap.entrySet()) {
            ModelBean modelBean = entry.getValue();
            modelBean.mergeFieldBeanMap(fieldBeanMap);
            modelBean.setDefault();
        }
    }
}

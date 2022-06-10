package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatList;

import java.util.List;
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
    public List<ModelBean> createBeanList() {
        List<ModelBean> beanMap = new ModelFactoryBasic(getConfigMaps()).createBeanList();
        addModelBeans(beanMap);
        return beanMap;
    }

    protected void addModelBeans(List<ModelBean> beanMap) {
        ConfigMaps devConfigMaps = new ConfigMaps(Scope.DEV);
        List<Map<String, Object>> mapList = new IOClasspathEOFlatList<Map<String,Object>>
                (devConfigMaps, "ModelConfig.json", Map.class)
                .read();
        FieldFactory fieldFactory = new FieldFactory(devConfigMaps);
        List<FieldBean> fieldBeanList = fieldFactory.createBeanList();

        for (Map<String,Object> entry: mapList) {
            ModelBean modelBean = new ModelBean(entry);
            beanMap.add(modelBean);
        }
        Map<String, FieldBean> fieldBeanMap = fieldFactory.transformToBeanMap(fieldBeanList);
        for  (ModelBean modelBean: beanMap) {
            modelBean.mergeFieldBeanMap(fieldBeanMap);
            modelBean.setDefault();
        }
    }
}

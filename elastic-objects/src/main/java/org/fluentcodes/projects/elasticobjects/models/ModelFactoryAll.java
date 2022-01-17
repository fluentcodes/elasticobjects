package org.fluentcodes.projects.elasticobjects.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelFactoryAll extends ModelFactory {
    ModelFactoryAll(final ConfigMaps configMaps) {
        super(configMaps);
    }

    @Override
    public List<ModelBean> createBeanList() {
        List<ModelBean> beanList = new ArrayList<>();
        new ModelFactoryBasic(getConfigMaps()).addModelBeans(beanList);
        new ModelFactoryFromConfigurations(getConfigMaps())
                .addModelBeans(beanList);
        new ModelFactoryFromModels(getConfigMaps())
                .addModelBeans(beanList);
        return beanList;
    }

}

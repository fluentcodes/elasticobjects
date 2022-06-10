package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatList;
import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.10.2021.
 */

public class FieldFactory extends ConfigFactory<FieldBean, FieldConfigObject> {
    public FieldFactory(final ConfigMaps configMaps) {
        super(configMaps, FieldBean.class, FieldConfigObject.class);
    }

    @Override
    public List<FieldBean> createBeanList() {
        List<Map<String, Object>> mapList = new IOClasspathEOFlatList<Map<String,Object>>
                (getConfigMaps(), "FieldConfig.json", Map.class)
                .read();
        List<FieldBean> fieldBeanList = new ArrayList<>();
        for (Map<String, Object> mapValue: mapList) {
            FieldBean fieldBean = new FieldBean(mapValue);
            fieldBean.setDefault();
            fieldBeanList.add(fieldBean);
        }
        return fieldBeanList;
    }

    static Map<String, FieldBean> createBeanMap(final List<FieldBean> beanList) {
        Map<String, FieldBean> beanMap = new TreeMap<>();
        for (FieldBean bean: beanList) {
            beanMap.put(bean.getNaturalId(), bean);
        }
        return beanMap;
    }

}

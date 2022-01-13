package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.io.IOClasspathEOFlatMap;

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
    public Map<String, FieldBean> createBeanMap() {
        Map<String, Map<String, Object>> mapValues = new IOClasspathEOFlatMap<Map<String,Object>>
                (getConfigMaps(), "FieldConfig.json", Map.class)
                .read();
        Map<String, FieldBean> fieldBeanMap = new TreeMap<>();
        for (Map.Entry<String, Map<String, Object>> entry: mapValues.entrySet()) {
            Map<String, Object> mapValue = entry.getValue();
            FieldBean fieldBean = new FieldBean(mapValue);
            fieldBean.setDefault();
            if (!fieldBean.hasFieldKey()) {
                LOG.warn("No modelKey defined for {}.", entry.getKey());
                continue;
            }
            if (!fieldBean.hasNaturalId()) {
                fieldBean.setNaturalId( entry.getKey());
            }
            fieldBeanMap.put(entry.getKey(), fieldBean);
        }
        return fieldBeanMap;
    }
}

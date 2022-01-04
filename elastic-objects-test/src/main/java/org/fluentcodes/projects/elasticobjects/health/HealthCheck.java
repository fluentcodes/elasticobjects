package org.fluentcodes.projects.elasticobjects.health;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HealthCheck {

    public static List<String> checkUnusedFields() {
        ConfigMaps configMaps = ProviderConfigMaps.CONFIG_MAPS;
        Set<String> modelKeys = configMaps.getConfigKeys(ModelConfig.class);
        Set<String> fieldKeys = new TreeSet<>();
        for (String modelKey : modelKeys) {
            ModelConfig modelConfig = configMaps.findModel(modelKey);
            if (!modelConfig.isObject()) {
                continue;
            }
            for (String fieldKey : modelConfig.getFieldKeys()) {
                fieldKeys.add(modelConfig.getField(fieldKey).getNaturalId());
            }
        }
        int counter = 0;
        Map<String, FieldBean> fieldBeanMap = new FieldFactory(configMaps).createBeanMap();
        final List<String> unusedFields = new ArrayList<>();
        for (String fieldKey : fieldBeanMap.keySet()) {
            if (!fieldKeys.contains(fieldKey)) {
                counter++;
                System.out.println(counter + ". Does not contain " + fieldKey);
                unusedFields.add(fieldKey);
            }
        }
        return unusedFields;
    }
}

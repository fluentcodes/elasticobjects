package org.fluentcodes.projects.elasticobjects.health;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HealthCheck {

    public static List<String> checkUnusedFields() {
        ConfigMaps configMaps = ObjectProvider.CONFIG_MAPS;
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
        List<FieldBean> fieldBeanList = new FieldFactory(configMaps).createBeanList();
        final List<String> unusedFields = new ArrayList<>();
        for (FieldBean fieldBean : fieldBeanList) {
            if (!fieldKeys.contains(fieldBean.getNaturalId())) {
                counter++;
                System.out.println(counter + ". Does not contain " + fieldBean.getNaturalId());
                unusedFields.add(fieldBean.getNaturalId());
            }
        }
        return unusedFields;
    }
}

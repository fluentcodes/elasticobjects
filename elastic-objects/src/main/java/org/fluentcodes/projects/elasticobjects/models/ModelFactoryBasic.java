package org.fluentcodes.projects.elasticobjects.models;

import java.time.LocalDateTime;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableCollection;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableList;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Set elementary objects for parsing json.
 */

public class ModelFactoryBasic extends ModelFactory {

    public ModelFactoryBasic(final ConfigMaps configMaps) {
        super(configMaps);
    }

    @Override
    public List<ModelBean> createBeanList() {
        List<ModelBean> modelMap = new ArrayList<>();
        return addModelBeans(modelMap);
    }

    public List<ModelBean> addModelBeans(List<ModelBean> modelMap) {
        modelMap.add(new ModelBean(LinkedHashMap.class, ShapeTypes.MAP)
                .setCreate(true)
        );
        modelMap.add(new ModelBean(Map.class, ShapeTypes.MAP)
                .setCreate(true)
        );
        modelMap.add(new ModelBean(UnmodifiableMap.class, ShapeTypes.MAP));

        modelMap.add(new ModelBean(UnmodifiableCollection.class, ShapeTypes.LIST));
        modelMap.add(new ModelBean(UnmodifiableList.class, ShapeTypes.LIST));
        modelMap.add(new ModelBean(List.class, ShapeTypes.LIST)
                .setCreate(true)
        );
        modelMap.add(new ModelBean(ArrayList.class, ShapeTypes.LIST)
                .setCreate(true)
        );

        modelMap.add(new ModelBean(Integer.class, ShapeTypes.INTEGER));
        modelMap.add(new ModelBean(Long.class, ShapeTypes.LONG));
        modelMap.add(new ModelBean(String.class, ShapeTypes.STRING));
        modelMap.add(new ModelBean(Float.class, ShapeTypes.FLOAT));
        modelMap.add(new ModelBean(Double.class, ShapeTypes.DOUBLE));
        modelMap.add(new ModelBean(Boolean.class, ShapeTypes.BOOLEAN));
        modelMap.add(new ModelBean(Date.class, ShapeTypes.DATE));
        modelMap.add(new ModelBean(LocalDateTime.class, ShapeTypes.LOCAL_DATE_TIME));
        modelMap.add((ModelBean) new ModelBean(LogLevel.class, ShapeTypes.ENUM)
                .setModule("elastic-objects")
                .setModuleScope("main"));
        modelMap.add((ModelBean)new ModelBean(JSONSerializationType.class, ShapeTypes.ENUM)
                .setModule("elastic-objects")
                .setModuleScope("main")
        );
        return modelMap;
    }
}

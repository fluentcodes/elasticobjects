package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.io.IOClasspathStringList;
import org.fluentcodes.tools.io.IORuntimeException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelFactoryFromModels extends ModelFactory {
    private static final String MODELS_JSON = "Models.json";

    public ModelFactoryFromModels(final ConfigMaps configMaps) {
        super(configMaps);
    }

    /**
     * Default init map.
     *
     * @return the expanded final configurations.
     */
    @Override
    public Map<String, ModelBean> createBeanMap() {
        Map<String, ModelBean> beanMap = new TreeMap<>();
        return addModelBeans(beanMap);
    }

    public Map<String, ModelBean> addModelBeans(Map<String, ModelBean> beanMap) {
        addModelsFromJsonList(beanMap);
        return beanMap;
    }

    protected final void addModelsFromJsonList(final Map<String, ModelBean> beanMap) {
        try {
            List<String> modelsList = new IOClasspathStringList(MODELS_JSON).read();
            if (modelsList.isEmpty()) {
                return;
            }
            for (String modelList : modelsList) {
                String[] modelClasses = modelList.split("\n");
                for (String modelClass : modelClasses) {
                    addModelForClasses(beanMap, modelClass);
                }
            }
        } catch (IORuntimeException e) {
            LOG.info(e.getMessage());
            return;
        }
    }

    private void addModelForClasses(Map<String, ModelBean> beanMap, String modelClass) {
        try {
            addModelForClasses(beanMap, Class.forName(modelClass));
        } catch (Exception e) {
            throw new EoException("Could not find Class " + modelClass + ": " + e.getMessage());
        }
    }

    private void addModelForClasses(Map<String, ModelBean> beanMap, Class<?> modelClass) {
        ModelBeanForClasses modelBean = new ModelBeanForClasses(modelClass, beanMap);
        if (beanMap.containsKey(modelClass.getSimpleName())) {
            LOG.info("Already defined '{}'", modelClass.getSimpleName());
            return;
        }
        for (FieldBean fieldBean : modelBean.getFields().values()) {
            String typeKey = ((FieldBeanForClasses) fieldBean).getTypeKey();
            if (!beanMap.containsKey(typeKey)) {
                addModelForClasses(beanMap, ((FieldBeanForClasses) fieldBean).getTypeClass().getTypeName());
            }
        }
        beanMap.put(modelBean.getNaturalId(), modelBean);
        if (modelBean.hasSuperClass()) {
            addModelForClasses(beanMap, modelBean.getSuperClass());
        }
    }
}

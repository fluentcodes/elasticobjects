package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.io.IOClasspathStringList;
import org.fluentcodes.tools.io.IORuntimeException;

import java.util.ArrayList;
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
    public List<ModelBean> createBeanList() {
        List<ModelBean> beanMap = new ArrayList<>();
        return addModelBeans(beanMap);
    }

    public List<ModelBean> addModelBeans(List<ModelBean> beanMap) {
        addModelsFromJsonList(beanMap);
        return beanMap;
    }

    protected final void addModelsFromJsonList(final List<ModelBean> beanMap) {
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
            LOG.info("No files '{}' found in classpath to scan java classes directly without configurations.", MODELS_JSON);
            return;
        }
    }

    private void addModelForClasses(List<ModelBean> beanMap, String modelClass) {
        try {
            addModelForClasses(beanMap, Class.forName(modelClass));
        } catch (Exception e) {
            throw new EoException("Could not find Class " + modelClass + ": " + e.getMessage());
        }
    }

    private void addModelForClasses(List<ModelBean> beanList, Class<?> modelClass) {
        for (ModelBean modelBean: beanList) {
            if (modelBean.getNaturalId().equals(modelClass.getSimpleName())) {
                return;
            }
        }
        ModelBeanForClasses modelBean = new ModelBeanForClasses(modelClass, beanList);
        for (FieldBean fieldBean : modelBean.getFields().values()) {
            String typeKey = ((FieldBeanForClasses) fieldBean).getTypeKey();
            addModelForClasses(beanList, ((FieldBeanForClasses) fieldBean).getTypeClass().getTypeName());
        }
        beanList.add(modelBean);
        if (modelBean.hasSuperClass()) {
            addModelForClasses(beanList, modelBean.getSuperClass());
        }
    }
}

package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.UnmodifiableCollection;
import org.fluentcodes.projects.elasticobjects.UnmodifiableList;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelBeanMap extends ConfigBeanMap<ModelBean>  {
    private final static String MODELS_JSON = "Models.json";
    public ModelBeanMap()  {
        super();
        init();
    }

    public ModelBeanMap(final Scope scope)  {
        super(scope, ModelConfig.class);
        init();
        if (scope == Scope.DEV) {
            return;
        }
        FieldBeanMap fieldBeanMap = new FieldBeanMap(getScope());
        addModelsJson();
        for (ModelBean modelBean: getBeanMap().values()) {
            modelBean.mergeFieldBeanMap(fieldBeanMap);
        }
        resolveSuper();
    }

    protected void init() {
        addModelBeanMap(LinkedHashMap.class);
        addModelBeanMap(Map.class);
        addModelBeanMap(UnmodifiableMap.class);

        addModelBeanList(UnmodifiableCollection.class);
        addModelBeanList(UnmodifiableList.class);
        addModelBeanList(List.class);
        addModelBeanList(ArrayList.class);

        addModelBeanScalar(Integer.class);
        addModelBeanScalar(Long.class);
        addModelBeanScalar(String.class);
        addModelBeanScalar(Float.class);
        addModelBeanScalar(Double.class);
        addModelBeanScalar(Boolean.class);
        addModelBeanScalar(Date.class);
        addModelBeanScalar(LogLevel.class);
        addModelBeanScalar(JSONSerializationType.class);
    }

    protected final void addModelsJson()  {
        String modelListString = readConfigFiles(MODELS_JSON);
        if (modelListString == null || modelListString.isEmpty()) {
            return;
        }
        String[] modelClasses = modelListString.split("\n");
        for (String modelClass: modelClasses) {
            addModelForClasses(modelClass);
        }
    }

    private  void addModelForClasses(String modelClass) {
        try {
            addModelForClasses(Class.forName(modelClass));
        } catch (Exception e) {
            throw new EoException("Could not find Class " + modelClass + ": " + e.getMessage());
        }
    }

    private void addModelForClasses(Class modelClass) {
        ModelBeanForClasses modelBean = new ModelBeanForClasses(modelClass, getBeanMap());
        if (getBeanMap().containsKey(modelClass.getSimpleName())) {
            LOG.info("Already defined '" + modelClass.getSimpleName() + "'");
            return;
        }
        for (FieldBeanInterface fieldBean: modelBean.getFieldBeans().values()) {
            String typeKey = ((FieldBeanForClasses)fieldBean).getTypeKey();
            if (!getBeanMap().containsKey(typeKey)) {
                addModelForClasses(((FieldBeanForClasses) fieldBean).getTypeClass().getTypeName());
            }
        }
        getBeanMap().put(modelBean.getNaturalId(), modelBean);
        if (modelBean.hasSuperClass()) {
            addModelForClasses(modelBean.getSuperClass());
        }
    }

    void resolveSuper() {
        for (ModelBean modelBean: getBeanMap().values()) {
            modelBean.resolveSuper(getBeanMap(), true);
        }
    }
    
    @Override
    protected ModelBean createBean(final String naturalId, final Map valueMap) {
        return new ModelBean(naturalId, valueMap);
    }

    private void addModelBeanMap(Class modelClass) {
        addBean(modelClass.getSimpleName(), new ModelBean(modelClass, ShapeTypes.MAP));
    }

    private void addModelBeanList(Class modelClass) {
        addBean(modelClass.getSimpleName(), new ModelBean(modelClass, ShapeTypes.LIST));
    }

    private  void addModelBeanScalar(Class modelClass) {
        addBean(modelClass.getSimpleName(), new ModelBean(modelClass, ShapeTypes.SCALAR));
    }
}

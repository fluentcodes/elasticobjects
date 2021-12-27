package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Werner on 09.10.2016.
 */
public abstract class ModelConfig extends ConfigConfig implements ModelConfigMethods {
    public static final String MODEL_KEY = "modelKey";
    public static final String INTERFACES = "interfaces";
    public static final String SUPER_KEY = "superKey";
    public static final String PACKAGE_PATH = "packagePath";

    private static final Logger LOG = LogManager.getLogger(ModelConfig.class);
    private boolean resolved = false;
    private boolean resolvedFields = false;

    private final Boolean abstractValue;
    private final String bean;
    private final Boolean create;
    private final Boolean dbAnnotated;
    private final String defaultImplementation;
    private final Boolean finalValue;
    private final String idKey;
    private final String interfaces;
    private final String modelKey;
    private final String naturalKeys;
    private final Boolean override;
    private final String packagePath;
    private final Boolean property;
    private final ShapeTypes shapeType;
    private final String superKey;
    private final String table;

    private Class modelClass;
    private ModelConfig superModel;
    private ModelConfig defaultImplementationModel;

    //resolved
    private final Map<String, FieldConfig> fieldConfigMap;
    private final Map<String, ModelConfig> interfacesMap;

    public ModelConfig(final ModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        try {
            this.shapeType = bean.getShapeType() == null? ShapeTypes.BEAN : bean.getShapeType();
        } catch (Exception e) {
            throw new EoException(e);
        }
        this.modelKey = bean.getModelKey();
        this.packagePath = bean.getPackagePath();
        this.superKey = bean.getSuperKey();
        this.interfaces = bean.getInterfaces();
        this.defaultImplementation = bean.getDefaultImplementation();
        this.abstractValue = bean.getAbstract();
        this.finalValue = bean.getFinal();
        this.override = bean.getOverride();
        this.dbAnnotated = bean.getDbAnnotated();
        this.create = bean.getCreate();
        this.property = bean.getProperty();
        this.bean = bean.getBean();
        this.table = bean.getTable();
        this.idKey = bean.getIdKey();
        this.naturalKeys = bean.getNaturalKeys();

        this.fieldConfigMap = new TreeMap<>();
        this.interfacesMap = new LinkedHashMap<>();

        if (bean.hasFieldBeans()) {
            for (FieldBean fieldBean : bean.getFieldBeans().values()) {
                fieldConfigMap.put(fieldBean.getFieldKey(), new FieldConfig(this, fieldBean));
            }
        }
        setModelClass();
    }

    @Override
    public ShapeTypes getShapeType() {
        return this.shapeType;
    }

    @Override
    public String getDefaultImplementation() {
        return defaultImplementation;
    }

    @Override
    public Boolean getAbstract() {
        return abstractValue;
    }

    @Override
    public Boolean getFinal() {
        return finalValue;
    }

    @Override
    public Boolean getOverride() {
        return override;
    }

    @Override
    public Boolean getDbAnnotated() {
        return this.dbAnnotated;
    }

    @Override
    public Boolean getCreate() {
        return this.create;
    }

    @Override
    public Boolean getProperty() {
        return this.property;
    }

    @Override
    public String getBean() {
        return bean;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getIdKey() {
        return idKey;
    }

    @Override
    public String getNaturalKeys() {
        return naturalKeys;
    }

    public Models getFieldModels(final String fieldKey) {
        return getField(fieldKey).getModels();
    }

    /**
     * The model name for the actions
     */
    public String getModelKey() {
        return this.modelKey;
    }
    //<call keep="JAVA" configKey="CacheGetter.tpl" }

    public String getPackagePath() {
        return this.packagePath;
    }

    /**
     * A key for the super class of a class.
     */
    public String getSuperKey() {
        return this.superKey;
    }

    /**
     * A comma separeted list of interface keys.
     */
    public String getInterfaces() {
        return this.interfaces;
    }

    public Class getModelClass() {
        if (modelClass == null) {
            return Object.class;
        }
        return modelClass;
    }

    @Override
    public Map<String, FieldConfig> getFieldMap() {
        return fieldConfigMap;
    }

    public Map<String, FieldConfig> getFieldConfigMap() {
        return fieldConfigMap;
    }

    public void setModelClass() {
        if (getModelKey() == null) {
            return;
            //throw new EoException("No modelkey defined. No model class could be derived!");
        }
        String packagePath = getPackagePath();
        if (packagePath == null) {
            return;
            //throw new EoException("No packagePath for " + modelKey + "defined.");
        }
        try {
            this.modelClass = (Class.forName(packagePath + "." + modelKey));
        } catch (Exception e) {
            throw new EoException("Class not resolved with packagePath " + packagePath + " and modelKey " + modelKey);
        }
    }

    private final void setSuperModel(Map<String, ModelInterface> modelConfigMap) {
        if (!hasSuperKey()) {
            return;
        }
        if (!modelConfigMap.containsKey(getSuperKey())) {
            throw new EoException("Could not find modelConfig for super with key '" + superKey + "' for '" + getNaturalId() + "'.");
        }
        this.superModel = (ModelConfig) modelConfigMap.get(superKey);
    }


    private final void setDefaultImplementationModel(Map<String, ModelInterface> modelConfigMap) {
        if (!hasDefaultImplementation()) {
            return;
        }
        if (!modelConfigMap.containsKey(getDefaultImplementation())) {
            throw new EoException("Could not find modelConfig for default implementation with key '" + getDefaultImplementation() + "' for '" + getNaturalId() + "'.");
        }
        this.defaultImplementationModel = (ModelConfig) modelConfigMap.get(getDefaultImplementation());
    }

    public final ModelConfigMethods getDefaultImplementationModel() {
        return defaultImplementationModel;
    }

    private final void setInterfacesMap(Map<String, ModelInterface> cache) {
        if (interfaces == null || interfaces.isEmpty()) {
            return;
        }
        String[] interfaceArray = interfaces.split(",");
        for (String interfaceEntry : interfaceArray) {
            interfacesMap.put(interfaceEntry, (ModelConfig) cache.get(interfaceEntry));
        }
    }

    private void setFieldConfigMap(final Map<String, ModelInterface> modelConfigMap) {
        if (!hasFieldConfigMap()) {
            return;
        }
        for (FieldConfig fieldConfig : fieldConfigMap.values()) {
            fieldConfig.resolve(this, modelConfigMap);
        }
    }

    public Map getKeyValues(final Object object, final PathPattern pathPattern) {
        Set<String> keySet = keys(object);
        Map keyValues = new LinkedHashMap();
        List<String> keys;
        if (pathPattern == null || pathPattern.get().size() == 0) {
            keys = new ArrayList(keySet);
        } else {
            keys = pathPattern.filter(keySet);
        }
        for (String key : keys) {
            try {
                /*if (fieldConfigMap.get(key).isTransient()) {
                    continue;
                }*/
                Object value = get(key, object);
                if (value == null) {
                    continue;
                }
                keyValues.put(key, value);
            } catch (Exception e) {
                LOG.warn("Problem getting value for " + key + ": " + e.getMessage());
            }
        }
        return keyValues;
    }

    public void resolve(Map<String, ModelInterface> modelConfigMap) {
        if (resolved) {
            return;
        }
        setSuperModel(modelConfigMap);
        setDefaultImplementationModel(modelConfigMap);
        setInterfacesMap(modelConfigMap);
        resolved = true;
        if (!hasFieldConfigMap()) {
            return;
        }
        setFieldConfigMap(modelConfigMap);
        resolved = true;
    }

    public boolean equals(ModelConfig modelCache) {
        if (modelKey == null) {
            return false;
        }
        if (modelCache == null) {
            return false;
        }
        return modelKey.equals(modelCache.getModelKey());
    }

    @Override
    public String toString() {
        if (!getConfigMaps().isModelFinished()) {
            return getShapeType() + " " + getNaturalId();
        } else {
            return super.toString();
        }
    }

    public ModelBean createBean() {
        return new ModelBean(this);
    }

    String asString(Object object) {
        return getShapeType().asString(object);
    }

    public boolean compare(Object left, Object right) {
        return getShapeType().compare(left, right);
    }

    String asJson(Object object) {
        return getShapeType().asJson(object);
    }

    Object asObject(Object object) {
        if (getShapeType() == ShapeTypes.ENUM) {
            return getShapeType().asObject(getModelClass(), object);
        }
        return getShapeType().asObject(object);
    }
}

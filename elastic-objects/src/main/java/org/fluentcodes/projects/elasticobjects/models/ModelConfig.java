package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Werner on 09.10.2016.
 */
public abstract class ModelConfig extends Config implements ModelInterface {
    public static final String F_MODEL_KEY = "modelKey";
    public static final String F_INTERFACES = "interfaces";
    public static final String F_SUPER_KEY = "superKey";
    public static final String PACKAGE_PATH = "packagePath";

    private static final Logger LOG = LogManager.getLogger(ModelConfig.class);
    private boolean resolved = false;
    private boolean resolvedFields = false;

    private final ModelConfigProperties properties;
    private final String interfaces;
    private final String modelKey;
    private final String packagePath;
    private final ShapeTypes shapeType;
    private final String superKey;

    private Class modelClass;
    private ModelConfig superModel;
    private ModelConfig defaultImplementationModel;

    //resolved
    private final Map<String, FieldConfig> fields;
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
        this.properties = new ModelConfigProperties(bean.getProperties());
        this.fields = new TreeMap<>();
        this.interfacesMap = new LinkedHashMap<>();

        if (bean.hasFields()) {
            for (Map.Entry<String, FieldBean> entry: bean.getFields().entrySet()) {
                fields.put(entry.getKey(), new FieldConfig(this, entry.getValue()));
            }
        }
        setModelClass();
    }

    public ModelConfigProperties getProperties() {
        return properties;
    }

    @Override
    public ShapeTypes getShapeType() {
        return this.shapeType;
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

    public Map<String, FieldConfig> getFields() {
        return fields;
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

    private final void setSuperModel(Map<String, ModelConfig> modelConfigMap) {
        if (!hasSuperKey()) {
            return;
        }
        if (!modelConfigMap.containsKey(getSuperKey())) {
            throw new EoException("Could not find modelConfig for super with key '" + superKey + "' for '" + getNaturalId() + "'.");
        }
        this.superModel = (ModelConfig) modelConfigMap.get(superKey);
    }

    boolean hasDefaultImplementation() {
        return getProperties().hasDefaultImplementation();
    }

    private final void setDefaultImplementationModel(Map<String, ModelConfig> modelConfigMap) {
        if (!hasDefaultImplementation()) {
            return;
        }
        if (!modelConfigMap.containsKey(getProperties().getDefaultImplementation())) {
            throw new EoException("Could not find modelConfig for default implementation with key '" + getProperties().getDefaultImplementation() + "' for '" + getNaturalId() + "'.");
        }
        this.defaultImplementationModel = (ModelConfig) modelConfigMap.get(getProperties().getDefaultImplementation());
    }

    public final ModelConfig getDefaultImplementationModel() {
        return defaultImplementationModel;
    }

    private final void setInterfacesMap(Map<String, ModelConfig> cache) {
        if (interfaces == null || interfaces.isEmpty()) {
            return;
        }
        String[] interfaceArray = interfaces.split(",");
        for (String interfaceEntry : interfaceArray) {
            interfacesMap.put(interfaceEntry, (ModelConfig) cache.get(interfaceEntry));
        }
    }

    private boolean hasFieldConfigMap() {
        return !fields.isEmpty();
    }

    private void setFields(final Map<String, ModelConfig> modelConfigMap) {
        if (!hasFieldConfigMap()) {
            return;
        }
        for (FieldConfig fieldConfig : fields.values()) {
            fieldConfig.resolve(this, modelConfigMap);
        }
    }

    public Set<String> keys(Object object)  {
        throw new EoException("Could not get field names because no sub fields defined for scalar models! " + object.getClass().getSimpleName());
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

    public Object get(String fieldKey, Object value) {
        throw new EoException("Could not get field value because no sub fields defined for scalar models: " + fieldKey);
    }

    public boolean hasValue(String fieldKey, Object value) {
        throw new EoException("Could not get field value because no sub fields defined for scalar models: " + fieldKey);
    }

    public void resolve(Map<String, ModelConfig> modelConfigMap) {
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
        setFields(modelConfigMap);
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

    public void remove(final String fieldName, final Object object)  {
        throw new EoException("Could not remove sub field because not sub fields defined for scalar models: " + fieldName);
    }

    public Object create() {
        throw new EoException("Could not create empty object for '(" + shapeType + ")" + modelKey + "'");
    }

    public boolean isEmpty(final Object object)  {
        return object == null;
    }

    public boolean set(final String fieldName, final Object object, final Object value)  {
        throw new EoException("Could not set value because no field defined for scalar models: " + fieldName);
    }

    public boolean exists(final String fieldName, final Object object)  {
        throw new EoException("No sub fields defined for scalar models: " + fieldName);
    }

    public int size(final Object object)  {
        throw new EoException("Could not get field names size because no sub fields  defined for scalar models!" + object.getClass().getSimpleName());
    }

    public Set<String> getFieldKeys()  {
        throw new EoException("Could not get field names because no sub fields  defined for scalar models!");
    }

    public FieldConfig getField(final String fieldName)  {
        throw new EoException("Could not get sub field because no field defined for scalar models: " + fieldName);
    }

    public ModelConfig getFieldModelConfig(final String fieldName)  {
        throw new EoException("Could not get sub field because no field defined for scalar models: " + fieldName);
    }

    public boolean hasField(final String fieldKey)  {
        return false;
    }

    public boolean isList() {
        return (this instanceof ModelConfigList);
    }

    public boolean isMap() {
        return (this instanceof ModelConfigMap);
    }

    public boolean isScalar() {
        return (this instanceof ModelConfigScalar);
    }

    public boolean isEnum() {
        return hasShapeType() && getShapeType() == ShapeTypes.ENUM;
    }

    public boolean isNumber() {
        return hasShapeType() && getShapeType().isNumber();
    }

    public boolean isObject() {
        return (this instanceof ModelConfigObject);
    }

    public boolean isCall() {
        return getModelKey().endsWith("Call");
    }

    public boolean isInterface() {
        return getShapeType() == ShapeTypes.INTERFACE;
    }

    public boolean isContainer() {
        return !isScalar();
    }

    public boolean isProperty(final String fieldKey) {
        return hasField(fieldKey) && getField(fieldKey).isProperty();
    }

    public boolean isJsonIgnore(final String fieldKey) {
        return hasField(fieldKey) && getField(fieldKey).getProperties().isJsonIgnore();
    }

    public Boolean isCreate() {
        return properties.isCreate();
    }

}

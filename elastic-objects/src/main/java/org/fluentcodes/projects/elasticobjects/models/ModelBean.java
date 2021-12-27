package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_OVERRIDE;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_PROPERTY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;

public class ModelBean extends ConfigBean implements ModelInterface, Comparable<ModelBean> {
    public static final String FIELD_KEYS = "fieldKeys";
    private boolean resolved;
    private String modelKey;
    private String packagePath;
    private String superKey;
    private String interfaces;
    private Map<String, FieldBean> fieldBeans;
    private ShapeTypes shapeType;
    private Set<ModelBean> modelSet;

    public ModelBean() {
        super();
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
    }

    public ModelBean(final String key) {
        super();
        setNaturalId(key);
        setModelKey(key);
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
    }

    public ModelBean(final Class modelClass, ShapeTypes shapeType) {
        this();
        setNaturalId(modelClass.getSimpleName());
        setModelKey(getNaturalId());
        setPackagePath(modelClass.getPackage().getName());
        setShapeType(shapeType);
        setConfigModelKey(shapeType.getModelConfigKey());
    }


    public ModelBean(final ModelConfig config) {
        super(config);
        setAbstract(config.getAbstract());
        setBean(config.getBean());
        setCreate(config.getCreate());
        setDbAnnotated(config.getDbAnnotated());
        setDefaultImplementation(config.getDefaultImplementation());
        setFinal(config.getFinal());
        setIdKey(config.getIdKey());
        setInterfaces(config.getInterfaces());
        // TODO REMOVE?! setJavascriptType(config.getJavascriptType());
        setModelKey(config.getModelKey());
        setNaturalKeys(config.getNaturalKeys());
        setOverride(config.getOverride());
        setPackagePath(config.getPackagePath());
        setProperty(config.getProperty());
        // TODO check to implement in config: setRolePermissions(config.)
        setShapeType(config.getShapeType());
        setSuperKey(config.getSuperKey());
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
        setFieldMap(config);
    }

    public ModelBean(final Map<String, Object> valueMap) {
        super(valueMap);
        Map<String, Object> properties = valueMap.containsKey(F_PROPERTIES) && valueMap.get(F_PROPERTIES) != null ?
                (Map<String, Object>) valueMap.get(F_PROPERTIES) :
                valueMap;

        setAbstract(
                toBoolean(properties.get(F_ABSTRACT)));
        setBean(
                toString(properties.get(BEAN)));
        setCreate(
                toBoolean(properties.get(F_CREATE)));
        setDbAnnotated(
                toBoolean(properties.get(DB_ANNOTATED)));
        setDefaultImplementation(
                toString(properties.get(DEFAULT_IMPLEMENTATION)));
        setFinal(
                toBoolean(properties.get(F_FINAL)));
        setInterfaces(
                toString(valueMap.get(INTERFACES)));
        setIdKey(
                toString(properties.get(ID_KEY)));
        setJavascriptType(
                toString(properties.get(JAVASCRIPT_TYPE)));
        setModelKey(
                toString(valueMap.get(MODEL_KEY)));
        setNaturalKeys(
                toString(properties.get(NATURAL_KEYS)));
        setOverride(
                toBoolean(properties.get(F_OVERRIDE)));
        setPackagePath(
                toString(valueMap.get(PACKAGE_PATH)));
        setProperty(
                toBoolean(properties.get(F_PROPERTY)));
        setShapeType(
                new ShapeTypeSerializerEnum<ShapeTypes>().asObject(ShapeTypes.class, valueMap.get(SHAPE_TYPE)));
        setSuperKey(
                toString(valueMap.get(SUPER_KEY)));
        setTable(
                toString(properties.get(TABLE)));
        defaultConfigModelKey();
        defaultNaturalId();
        modelSet = new TreeSet<>();

        if (!valueMap.containsKey(FIELD_KEYS) || valueMap.get(FIELD_KEYS) == null) {
            this.fieldBeans = new TreeMap<>();
            return;
        }
        if (valueMap.get(FIELD_KEYS) instanceof String) {
            fieldBeans = createFields((String) valueMap.get(FIELD_KEYS));
        } else if (valueMap.get(FIELD_KEYS) instanceof List) {
            fieldBeans = createFields((List) valueMap.get(FIELD_KEYS));
        } else if (valueMap.get(FIELD_KEYS) instanceof Map) {
            fieldBeans = createFields((Map) valueMap.get(FIELD_KEYS));
        } else {
            this.fieldBeans = new TreeMap<>();
            LOG.warn("FieldBeans is neither Map, String or List but " + valueMap.get(FIELD_KEYS).getClass().getSimpleName());
        }
    }

    protected void setFieldMap(final ModelConfig config) {
        for (Map.Entry<String, FieldConfig> entry : config.getFieldMap().entrySet()) {
            fieldBeans.put(entry.getKey(), new FieldBean(entry.getValue()));
        }
    }

    public void setDefault() {
        mergeAbstract(false);
        mergeCreate(false);
        defaultConfigModelKey();
        mergeDbAnnotated(false);
        mergeFinal(false);
        defaultNaturalId();
        mergeOverride(false);
        mergeProperty(false);
        defaultShapeTypes();
    }

    public void setFieldBeans(Map<String, FieldBean> fieldBeans) {
        this.fieldBeans = fieldBeans;
    }

    protected void addField(final String fieldKey) {
        addField(new FieldBean(fieldKey));
    }

    protected void addField(final FieldConfig fieldConfig) {
        fieldBeans.put(fieldConfig.getNaturalId(), new FieldBean(fieldConfig));
    }

    protected void addField(final String fieldKey, final Map<String, Object> fieldMap) {
        addField(new FieldBean(fieldKey, fieldMap));
    }

    protected void addField(final FieldBean fieldBean) {
        fieldBeans.put(fieldBean.getNaturalId(), fieldBean);
    }

    protected void setFieldMap(final Map fieldConfigMap) {
        for (Object key : fieldConfigMap.keySet()) {
            addField((String) key, (Map<String, Object>) fieldConfigMap.get(key));
        }
    }

    public Map<String, FieldBean> createFields(final Map<String, Object> fields) {
        Map<String, FieldBean> fieldMap = new TreeMap<>();
        if (fields.isEmpty()) {
            return fieldMap;
        }
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            try {
                Map<String, Object> fieldEntryMap = (Map<String, Object>) entry.getValue();
                fieldEntryMap.put(F_FIELD_KEY, entry.getKey().replaceAll(".*\\.", ""));
                fieldEntryMap.put(F_NATURAL_ID, entry.getKey());
                fieldMap.put((String) entry.getKey(), new FieldBean(fieldEntryMap));
            } catch (Exception e) {
                throw new EoException("Problem casting field value " + entry.getKey());
            }
        }
        return fieldMap;
    }

    public Map<String, FieldBean> createFields(final List<String> fields) {
        Map<String, FieldBean> fieldMap = new TreeMap<>();
        if (fields.isEmpty()) {
            return fieldMap;
        }
        for (String key : fields) {
            fieldMap.put(key, new FieldBean(key));
        }
        return fieldMap;
    }

    public Map<String, FieldBean> createFields(final String fields) {
        Map<String, FieldBean> fieldMap = new TreeMap<>();
        if (fields.isEmpty()) {
            return fieldMap;
        }
        String[] fieldKeys = fields.split(",\\s*");
        for (String key : fieldKeys) {
            fieldMap.put(key, new FieldBean(key));
        }
        return fieldMap;
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(ModelConfigObject.class.getSimpleName());
    }

    private void defaultNaturalId() {
        if (hasNaturalId()) {
            return;
        }
        setNaturalId(getModelKey());
    }

    public void merge(final ModelBean modelBean) {
        super.merge(modelBean);
    }

    @Override
    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    @Override
    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    @Override
    public String getSuperKey() {
        return superKey;
    }

    public void setFieldKeys(List<String> fieldKeys) {
    }

    public void setSuperKey(String superKey) {
        this.superKey = superKey;
    }

    @Override
    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    public Set<String> getFieldKeys() {
        return getFieldBeans().keySet();
    }

    public Map<String, FieldBean> getFieldBeans() {
        return fieldBeans;
    }

    public boolean hasFieldBeans() {
        return fieldBeans != null && !fieldBeans.isEmpty();
    }

    public FieldBean getFieldBean(final String fieldKey) {
        return fieldBeans.get(fieldKey);
    }

    @Override
    public Map<String, FieldConfig> getFieldMap() {
        throw new EoException("TODO");
    }

    @Override
    public Boolean getAbstract() {
        return (Boolean) getProperties().get(F_ABSTRACT);
    }

    public ModelBean setAbstract(Boolean dbAnnotated) {
        getProperties().put(F_ABSTRACT, dbAnnotated);
        return this;
    }

    private void mergeAbstract(Boolean value) {
        if (hasAbstract()) {
            return;
        }
        getProperties().put(F_ABSTRACT, value);
    }

    @Override
    public String getBean() {
        return (String) getProperties().get(BEAN);
    }

    public void setBean(String value) {
        getProperties().put(BEAN, value);
    }

    @Override
    public Boolean getCreate() {
        return (Boolean) getProperties().get(F_CREATE);
    }

    public ModelBean setCreate(Boolean create) {
        getProperties().put(F_CREATE, create);
        return this;
    }

    private void mergeCreate(Boolean value) {
        if (hasCreate()) {
            return;
        }
        getProperties().put(F_CREATE, value);
    }

    @Override
    public Boolean getDbAnnotated() {
        return (Boolean) getProperties().get(DB_ANNOTATED);
    }

    public ModelBean setDbAnnotated(Boolean dbAnnotated) {
        getProperties().put(DB_ANNOTATED, dbAnnotated);
        return this;
    }

    private void mergeDbAnnotated(Boolean value) {
        if (hasDbAnnotated()) {
            return;
        }
        getProperties().put(DB_ANNOTATED, value);
    }

    @Override
    public String getDefaultImplementation() {
        return (String) getProperties().get(DEFAULT_IMPLEMENTATION);
    }

    public void setDefaultImplementation(String value) {
        getProperties().put(DEFAULT_IMPLEMENTATION, value);
    }


    @Override
    public Boolean getFinal() {
        return (Boolean) getProperties().get(F_FINAL);
    }

    public void setFinal(Boolean value) {
        getProperties().put(F_FINAL, value);
    }

    private void mergeFinal(Boolean value) {
        if (hasFinal()) {
            return;
        }
        getProperties().put(F_FINAL, value);
    }

    @Override
    public String getIdKey() {
        return hasProperties() ? (String) getProperties().get(ID_KEY) : null;
    }

    public void setIdKey(String value) {
        getProperties().put(ID_KEY, value);
    }

    public void setJavascriptType(String value) {
        getProperties().put(JAVASCRIPT_TYPE, value);
    }

    public String getJavascriptType() {
        return (String) getProperties().get(JAVASCRIPT_TYPE);
    }

    public void setNaturalKeys(String value) {
        getProperties().put(NATURAL_KEYS, value);
    }

    @Override
    public String getNaturalKeys() {
        return (String) getProperties().get(NATURAL_KEYS);
    }

    @Override
    public Boolean getOverride() {
        return (Boolean) getProperties().get(F_OVERRIDE);
    }

    @Override
    public boolean hasOverride() {
        return getProperties().containsKey(F_OVERRIDE) && getProperties().get(F_OVERRIDE) != null;
    }

    public void setOverride(Boolean value) {
        getProperties().put(F_OVERRIDE, value);
    }

    private void mergeOverride(Boolean value) {
        if (hasOverride()) {
            return;
        }
        getProperties().put(F_OVERRIDE, value);
    }

    @Override
    public Boolean getProperty() {
        return (Boolean) getProperties().get(F_PROPERTY);
    }

    public ModelBean setProperty(Boolean value) {
        getProperties().put(F_PROPERTY, value);
        return this;
    }

    private void mergeProperty(Boolean value) {
        if (hasProperty()) {
            return;
        }
        getProperties().put(F_PROPERTY, value);
    }

    public void setShapeType(String shapeType) {
        setShapeType(
                new ShapeTypeSerializerEnum<ShapeTypes>().asObject(ShapeTypes.class, shapeType));
    }

    public void setShapeType(ShapeTypes shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    public ShapeTypes getShapeType() {
        return shapeType;
    }

    public void defaultShapeTypes() {
        if (hasShapeType()) {
            return;
        }
        setShapeType(ShapeTypes.BEAN);
    }

    @Override
    public String getTable() {
        return hasProperties() ? (String) getProperties().get(TABLE) : null;
    }

    public void setTable(String value) {
        getProperties().put(TABLE, value);
    }

    public void mergeFieldBeanMap(Map<String, FieldBean> fieldBeanMap) {
        Map<String, FieldBean> mergedMap = new TreeMap<>();
        for (Map.Entry<String, FieldBean> entry : fieldBeans.entrySet()) {
            if (entry.getValue().isMerged()) {
                continue;
            }
            FieldBean fieldBean = entry.getValue();
            if (!fieldBeanMap.containsKey(entry.getKey())) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            FieldBean fieldBeanFromMap = fieldBeanMap.get(entry.getKey() );

            fieldBean.merge(fieldBeanFromMap);
            fieldBean.setParentModel(this);
            mergedMap.put(fieldBean.getFieldKey(), fieldBean);
        }
        this.fieldBeans = mergedMap;
    }

    public void resolveSuper(Map<String, ModelBean> modelBeans, boolean mergeFields) {
        if (resolved) {
            return;
        }
        if (this.hasSuperKey()) {
            if (!modelBeans.containsKey(superKey) || modelBeans.get(superKey) == null) {
                throw new EoInternalException("No model configuration found for superKey '" + superKey + "'");
            }
            ModelBean superModelBean = modelBeans.get(this.getSuperKey());
            modelSet.add(superModelBean);
            superModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
        }
        if (this.hasInterfaces()) {
            String[] interfaceArray = this.getInterfaces().split(",");
            for (String interfaceKey : interfaceArray) {
                if (!modelBeans.containsKey(interfaceKey) || modelBeans.get(interfaceKey) == null) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'.");
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                modelSet.add(interfaceModelBean);
                interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
            }
        }
        for (FieldBean fieldBean : fieldBeans.values()) {
            fieldBean.setParentModel(this);
            if (!fieldBean.hasModelKeys()) {
                throw new EoInternalException("No Model defined for field '" + fieldBean.getNaturalId() + "'");
            }
            for (String fieldModelKey : fieldBean.getModelKeys().split(",")) {
                if (!modelBeans.containsKey(fieldModelKey) || modelBeans.get(fieldModelKey) == null) {
                    throw new EoInternalException("Could not find model '" + fieldModelKey + "' for '" + fieldBean.getFieldKey() + "'.");
                }
                modelSet.add(modelBeans.get(fieldModelKey));
            }
        }
        resolved = true;
    }

    protected void resolveSuper(Map<String, ModelBean> modelBeans, Map<String, FieldBean> subFieldBeans, boolean mergeFields) {
        if (!resolved) {
            if (this.hasSuperKey()) {
                if (!modelBeans.containsKey(this.getSuperKey())) {
                    throw new EoException("Could not resolve super key '" + getSuperKey() + "' for '" + getNaturalId() + "'.");
                }
                ModelBean superModelBean = modelBeans.get(this.getSuperKey());
                superModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
            }

            if (this.hasInterfaces()) {
                String[] interfaceArray = this.getInterfaces().split(",");
                for (String interfaceKey : interfaceArray) {
                    if (!modelBeans.containsKey(interfaceKey)) {
                        throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'.");
                    }
                    ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                    interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
                }
            }
            resolved = true;
        }

        if (!mergeFields) {
            return;
        }
        for (FieldBean fieldBean : this.fieldBeans.values()) {
            if (subFieldBeans.containsKey(fieldBean.getNaturalId())) {
                subFieldBeans.get(fieldBean.getNaturalId()).setOverride(true);
                continue;
            }
            FieldBean fieldBeanLocal = new FieldBean(fieldBean);
            fieldBeanLocal.setSuper(true);
            subFieldBeans.put(fieldBean.getNaturalId(), fieldBeanLocal);
        }
    }

    @Override
    public String toString() {
        return "(" + getShapeType() + ")" + getKey();
    }

    public String getClassName() {
        return this.packagePath + "." + this.modelKey;
    }

    @Override
    public int compareTo(ModelBean modelBean) {
        return getClassName().compareTo(modelBean.getClassName());
    }

}

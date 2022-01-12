package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_SUPER_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

public class ModelBean extends ConfigBean implements ModelInterface, Comparable<ModelBean> {
    private static final Logger LOG = LogManager.getLogger(ModelBean.class);
    public static final String F_FIELDS = "fields";
    private boolean resolved;
    private String modelKey;
    private String packagePath;
    private String superKey;
    private String interfaces;
    private Map<String, FieldBean> fields;
    private ShapeTypes shapeType;
    private Set<ModelBean> modelSet;
    private ModelBeanProperties properties;

    public ModelBean() {
        super();
        fields = new TreeMap<>();
        modelSet = new TreeSet<>();
        this.properties = new ModelBeanProperties(new HashMap<>());
    }

    public ModelBean(final String key) {
        super();
        setNaturalId(key);
        setModelKey(key);
        fields = new TreeMap<>();
        modelSet = new TreeSet<>();
        this.properties = new ModelBeanProperties(new HashMap<>());
    }

    public ModelBean(final Class modelClass, ShapeTypes shapeType) {
        this();
        setNaturalId(modelClass.getSimpleName());
        setModelKey(getNaturalId());
        setPackagePath(modelClass.getPackage().getName());
        setShapeType(shapeType);
        setConfigModelKey(shapeType.getModelConfigKey());
        this.properties = new ModelBeanProperties(new HashMap<>());
    }


    public ModelBean(final ModelConfig config) {
        super(config);
        setInterfaces(config.getInterfaces());
        setModelKey(config.getModelKey());
        setPackagePath(config.getPackagePath());
        setShapeType(config.getShapeType());
        setSuperKey(config.getSuperKey());
        fields = new TreeMap<>();
        modelSet = new TreeSet<>();
        setFieldMap(config);
        this.properties = new ModelBeanProperties(config.getProperties());
    }

    public ModelBean(final Map<String, Object> valueMap) {
        super(valueMap);
        setInterfaces(
                toString(valueMap.get(F_INTERFACES)));
        setModelKey(
                toString(valueMap.get(F_MODEL_KEY)));
        setPackagePath(
                toString(valueMap.get(PACKAGE_PATH)));
        setShapeType(
                new ShapeTypeSerializerEnum<ShapeTypes>().asObject(ShapeTypes.class, valueMap.get(SHAPE_TYPE)));
        setSuperKey(
                toString(valueMap.get(F_SUPER_KEY)));

        if (valueMap.containsKey(F_PROPERTIES) && valueMap.get(F_PROPERTIES) != null) {
            this.properties = new ModelBeanProperties((Map<String, Object>) valueMap.get(F_PROPERTIES));
        } else {
            this.properties = new ModelBeanProperties(new HashMap<>());
        }

        defaultConfigModelKey();
        defaultNaturalId();


        modelSet = new TreeSet<>();
        if (!valueMap.containsKey(F_FIELDS) || valueMap.get(F_FIELDS) == null) {
            this.fields = new TreeMap<>();
            return;
        }
        if (valueMap.get(F_FIELDS) instanceof String) {
            fields = createFields((String) valueMap.get(F_FIELDS));
        } else if (valueMap.get(F_FIELDS) instanceof List) {
            fields = createFields((List) valueMap.get(F_FIELDS));
        } else if (valueMap.get(F_FIELDS) instanceof Map) {
            fields = createFields((Map) valueMap.get(F_FIELDS));
        } else {
            this.fields = new TreeMap<>();
            LOG.warn("FieldBeans is neither Map, String or List but " + valueMap.get(F_FIELDS).getClass().getSimpleName());
        }
    }

    public ModelBeanProperties getProperties() {
        return properties;
    }

    public void setProperties(ModelBeanProperties properties) {
        this.properties = properties;
    }

    ModelBean setCreate(final Boolean value) {
        getProperties().setCreate(value);
        return this;
    }

    protected void setFieldMap(final ModelConfig config) {
        for (Map.Entry<String, FieldConfig> entry : config.getFields().entrySet()) {
            fields.put(entry.getKey(), new FieldBean(entry.getValue()));
        }
    }

    public void setDefault() {
        defaultConfigModelKey();
        defaultNaturalId();
        defaultShapeTypes();
        properties.setDefault();
    }

    public void setFields(Map<String, FieldBean> fields) {
        this.fields = fields;
    }

    protected void addField(final String fieldKey) {
        addField(new FieldBean(fieldKey));
    }

    protected void addField(final FieldBean fieldBean) {
        fields.put(fieldBean.getNaturalId(), fieldBean);
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
                FieldBean bean = new FieldBean(fieldEntryMap);
                fieldMap.put(bean.getFieldKey(), bean);
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
            FieldBean bean = new FieldBean(key);
            fieldMap.put(bean.getFieldKey(), bean);
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
            FieldBean bean = new FieldBean(key);
            fieldMap.put(bean.getFieldKey(), bean);
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
        return getFields().keySet();
    }

    public Map<String, FieldBean> getFields() {
        return fields;
    }

    public boolean hasFields() {
        return fields != null && !fields.isEmpty();
    }

    public FieldBean getField(final String fieldKey) {
        return fields.get(fieldKey);
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

    public void mergeFieldBeanMap(Map<String, FieldBean> fieldBeanMap) {
        Map<String, FieldBean> mergedMap = new TreeMap<>();
        for (Map.Entry<String, FieldBean> entry : fields.entrySet()) {
            if (entry.getValue().isMerged()) {
                continue;
            }
            FieldBean fieldBean = entry.getValue();
            if (!fieldBeanMap.containsKey(fieldBean.getNaturalId())) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            FieldBean fieldBeanFromMap = fieldBeanMap.get(fieldBean.getNaturalId());

            fieldBean.merge(fieldBeanFromMap);
            fieldBean.setParentModel(this);
            mergedMap.put(fieldBean.getFieldKey(), fieldBean);
        }
        this.fields = mergedMap;
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
            superModelBean.resolveSuper(modelBeans, this.fields, mergeFields);
        }
        if (this.hasInterfaces()) {
            String[] interfaceArray = this.getInterfaces().split(",");
            for (String interfaceKey : interfaceArray) {
                if (!modelBeans.containsKey(interfaceKey) || modelBeans.get(interfaceKey) == null) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'.");
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                modelSet.add(interfaceModelBean);
                interfaceModelBean.resolveSuper(modelBeans, this.fields, mergeFields);
            }
        }
        for (FieldBean fieldBean : fields.values()) {
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
                superModelBean.resolveSuper(modelBeans, this.fields, mergeFields);
            }

            if (this.hasInterfaces()) {
                String[] interfaceArray = this.getInterfaces().split(",");
                for (String interfaceKey : interfaceArray) {
                    if (!modelBeans.containsKey(interfaceKey)) {
                        throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'.");
                    }
                    ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                    interfaceModelBean.resolveSuper(modelBeans, this.fields, mergeFields);
                }
            }
            resolved = true;
        }

        if (!mergeFields) {
            return;
        }
        for (FieldBean fieldBean : this.fields.values()) {
            if (subFieldBeans.containsKey(fieldBean.getNaturalId())) {
                subFieldBeans.get(fieldBean.getNaturalId()).getProperties().setOverride(true);
                continue;
            }
            FieldBean fieldBeanLocal = new FieldBean(fieldBean);
            fieldBeanLocal.getProperties().setSuper(true);
            subFieldBeans.put(fieldBean.getFieldKey(), fieldBeanLocal);
        }
    }

    @Override
    public String toString() {
        return "(" + getShapeType() + ")" + getNaturalId();
    }

    public String getClassName() {
        return this.packagePath + "." + this.modelKey;
    }

    @Override
    public int compareTo(ModelBean modelBean) {
        return getClassName().compareTo(modelBean.getClassName());
    }

}

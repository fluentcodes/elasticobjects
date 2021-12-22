package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.PermissionInterface;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_OVERRIDE;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_PROPERTY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;

public class ModelBean extends ConfigBean implements ModelInterface, PermissionInterface, Comparable<ModelBean> {
    public static final String FIELD_KEYS = "fieldKeys";
    private boolean resolved;
    private String modelKey;
    private String packagePath;
    private String superKey;
    private String interfaces;
    private Map<String, FieldBean> fieldBeans;
    private PermissionRole rolePermissions;
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
        setModelKey(config.getModelKey());
        setPackagePath(config.getPackagePath());
        setInterfaces(config.getInterfaces());
        setSuperKey(config.getSuperKey());
        //setRolePermissions(config.g);
        defaultShapeType();
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
        setFieldMap(config);
    }

    public ModelBean(final String naturalId, final Map values) {
        super(naturalId, values);
    }

    public ModelBean(final Map<String, Object> values) {
        super(values);
    }

    protected void setFieldMap(final ModelConfig config) {
        for (FieldConfig fieldConfig: config.getFieldMap().values()) {
            addField(fieldConfig);
        }
    }

    protected void addField(final String fieldKey) {
        addField(new FieldBean(fieldKey));
    }

    protected void addField(final FieldConfig fieldConfig) {
        addField(new FieldBean(fieldConfig));
    }

    protected void addField(final String fieldKey, final Map<String, Object> fieldMap) {
        addField(new FieldBean(fieldKey, fieldMap));
    }

    protected void addField(final FieldBean fieldBean) {
        if (hasFinal()) fieldBean.setFinal(getFinal());
        if (hasOverride()) fieldBean.setOverride(getOverride());
        fieldBeans.put(fieldBean.getNaturalId(), fieldBean);
    }

    protected void setFieldMap(final Map fieldConfigMap) {
        for (Object key : fieldConfigMap.keySet()) {
            addField((String) key, (Map<String,Object>) fieldConfigMap.get(key));
        }
    }

    @Override
    public void merge(final Map valueMap) {
        if (valueMap == null) {
            throw new EoInternalException("Value Map is null for '" + getNaturalId() + "'.");
        }
        super.merge(valueMap);
        setModelKey((String) valueMap.get(MODEL_KEY));
        setPackagePath((String) valueMap.get(PACKAGE_PATH));
        setInterfaces((String) valueMap.get(INTERFACES));
        setSuperKey((String) valueMap.get(SUPER_KEY));
        mergeRolePermissions(valueMap.get(ROLE_PERMISSIONS));
        defaultShapeType();
        defaultConfigModelKey();
        modelSet = new TreeSet<>();
        fieldBeans = new TreeMap<>();
        if (!valueMap.containsKey(FIELD_KEYS)) {
            return;
        }
        Object fieldsFromMap = valueMap.get(FIELD_KEYS);
        if ((fieldsFromMap instanceof String)) {
            if (((String) fieldsFromMap).isEmpty()) {
                return;
            }
            List<String> fieldKeys = Arrays.asList(((String) fieldsFromMap).split(",\\s*"));
            for (String key : fieldKeys) {
                addField(key);
            }
        }
        else if (fieldsFromMap instanceof List) {
            if (((List) fieldsFromMap).isEmpty()) {
                return;
            }
            List<String> fieldKeys = new ArrayList<>((List) fieldsFromMap);
            for (String key : fieldKeys) {
                addField(key);
            }
        }
        else if (fieldsFromMap instanceof Map) {
            if (((Map) fieldsFromMap).isEmpty()) {
                return;
            }
            for (Object key : ((Map)fieldsFromMap).keySet()) {
                Map<String, Object> fieldMap = null;
                try {
                    fieldMap = (Map<String, Object>) ((Map) fieldsFromMap).get(key);
                }
                catch (Exception e) {
                    throw new EoException("Problem casting field value " + fieldsFromMap);
                }
                if (!fieldMap.containsKey(F_NATURAL_ID)) {
                    fieldMap.put(F_NATURAL_ID, key);
                }
                if (!fieldMap.containsKey(F_FIELD_KEY)) {
                    fieldMap.put(F_FIELD_KEY, key);
                }
                FieldBean fieldJoiner = new FieldBean(fieldMap);
                fieldJoiner.setNaturalId((String)key);
                this.fieldBeans.put((String)key, fieldJoiner);
            }
        }
        else {
            throw new EoInternalException("FieldKeys are neither String, Map or List");
        }
    }

    public Set<ModelBean> getModelSet() {
        return modelSet;
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(ModelConfigObject.class.getSimpleName());
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
        return fieldBeans!=null && !fieldBeans.isEmpty();
    }

    public FieldBean getFieldBean(final String fieldKey) {
        return fieldBeans.get(fieldKey);
    }

    @Override
    public Map<String, FieldConfig> getFieldMap() {
        throw new EoException("TODO");
    }

    @Override
    public  boolean hasDefaultImplementation() {
        return getProperties().containsKey(DEFAULT_IMPLEMENTATION);
    }

    @Override
    public  String getDefaultImplementation() {
        return (String) getProperties().get(DEFAULT_IMPLEMENTATION);
    }

    @Override
    public  Boolean getAbstract() {
        return (Boolean) getProperties().get(ABSTRACT);
    }

    @Override
    public  boolean hasAbstract() {
        return getProperties().containsKey(ABSTRACT) && getProperties().get(ABSTRACT) != null;
    }

    @Override
    public  Boolean getFinal() {
        return (Boolean) getProperties().get(F_FINAL);
    }

    @Override
    public  boolean hasFinal() {
        return getProperties().containsKey(F_FINAL) && getProperties().get(F_FINAL) != null;
    }

    @Override
    public  Boolean getOverride() {
        return (Boolean) getProperties().get(F_OVERRIDE);
    }

    @Override
    public  boolean hasOverride() {
        return getProperties().containsKey(F_OVERRIDE) && getProperties().get(F_OVERRIDE) != null;
    }

    public void setShapeType(ShapeTypes shapeType) {
        getProperties().put(SHAPE_TYPE, shapeType);
    }

    private void defaultShapeType() {
        if (!hasShapeType()) return;
    }

    @Override
    public boolean hasShapeType() {
        return getProperties().containsKey(SHAPE_TYPE) && getProperties().get(SHAPE_TYPE) != null;
    }

    @Override
    public Boolean getDbAnnotated() {
        return (Boolean) getProperties().get(DB_ANNOTATED);
    }

    @Override
    public boolean hasDbAnnotated() {
        return getProperties().containsKey(DB_ANNOTATED);
    }

    @Override
    public boolean hasCreate() {
        return getProperties().containsKey(CREATE) && getProperties().get(CREATE) != null;
    }

    @Override
    public Boolean getCreate() {
        return (Boolean) getProperties().get(CREATE);
    }

    @Override
    public Boolean getProperty() {
        return (Boolean) getProperties().get(F_PROPERTY);
    }

    @Override
    public boolean hasProperty() {
        return getProperties().containsKey(F_PROPERTY) && getProperties().get(F_PROPERTY) != null;
    }

    @Override
    public String getBean() {
        return (String) getProperties().get(BEAN);
    }

    @Override
    public boolean hasBean() {
        return getProperties().containsKey(BEAN) && getProperties().get(BEAN) != null && !((String) getProperties().get(BEAN)).isEmpty();
    }

    @Override
    public  String getIdKey() {
        return hasProperties() ? (String) getProperties().get(ID_KEY) : null;
    }

    @Override
    public String getNaturalKeys() {
        return hasProperties() ? (String) getProperties().get(NATURAL_KEYS) : null;
    }

    @Override
    public String getTable() {
        return hasProperties() ? (String) getProperties().get(TABLE) : null;
    }

    @Override
    public ShapeTypes getShapeType() {
        if (!getProperties().containsKey(SHAPE_TYPE)) {
            return ShapeTypes.BEAN;
        }
        try {
            if (getProperties().get(SHAPE_TYPE) instanceof String) {
                return ShapeTypes.valueOf((String) getProperties().get(SHAPE_TYPE));
            }
        }
        catch (IllegalArgumentException e) {
            throw new EoInternalException(e);
        }
        if (getProperties().get(SHAPE_TYPE) instanceof ShapeTypes) {
            return (ShapeTypes)getProperties().get(SHAPE_TYPE);
        }
        throw new EoException("Could not map " + getProperties().get(SHAPE_TYPE) + " " + getProperties().get(SHAPE_TYPE).getClass());
    }

    public void mergeFieldBeanMap(Map<String, FieldBean> fieldBeanMap) {
        for (FieldBean fieldBean: fieldBeans.values()) {
            if (!fieldBean.hasNaturalId()) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            if (!fieldBean.isMerged()) {
                if (!fieldBeanMap.containsKey(fieldBean.getNaturalId())) {
                    throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
                }
                FieldBean fieldBeanFromMap = fieldBeanMap.get(fieldBean.getNaturalId());
                //if (isFinal()) fieldBeanFromMap.setFinal(true);
                fieldBean.merge(fieldBeanFromMap);
            }
            fieldBean.setParentModel(this);
        }
    }

    public void mergeFieldDefinition(Map<String, Map> fieldMap) {
        for (FieldBean fieldBean: fieldBeans.values()) {
            if (!fieldBean.hasNaturalId()) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            if (!fieldMap.containsKey(fieldBean.getNaturalId())) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            FieldBean fieldBeanFromMap = new FieldBean(fieldMap.get(fieldBean.getNaturalId()));
            if (isFinal()) fieldBeanFromMap.setFinal(true);
            ((FieldBean) fieldBean).merge(fieldBeanFromMap);
            fieldBean.setParentModel(this);
        }
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
            for (String interfaceKey: interfaceArray) {
                if (!modelBeans.containsKey(interfaceKey) || modelBeans.get(interfaceKey) == null) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'." );
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                modelSet.add(interfaceModelBean);
                interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
            }
        }
        for (FieldBean fieldBean: fieldBeans.values()) {
            fieldBean.setParentModel(this);
            if (!fieldBean.hasModelKeys()) {
                throw new EoInternalException("No Model defined for field '" + fieldBean.getNaturalId() + "'");
            }
            for (String fieldModelKey: fieldBean.getModelKeys().split(",")) {
                if (!modelBeans.containsKey(fieldModelKey) || modelBeans.get(fieldModelKey) == null) {
                    throw new EoInternalException("Could not find model '" + fieldModelKey + "' for '" + fieldBean.getFieldKey() + "'." );
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
        for (FieldBean fieldBean: this.fieldBeans.values()) {
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
    public PermissionRole getRolePermissions() {
        return rolePermissions;
    }

    public ModelBean setRolePermissions(PermissionRole rolePermissions) {
        this.rolePermissions = rolePermissions;
        return this;
    }

    @Override
    public String toString() {
        return "(" + getShapeType() + ")" + getKey() ;
    }

    public String getClassName()  {
        return this.packagePath + "." + this.modelKey;
    }

    @Override
    public int compareTo(ModelBean modelBean) {
        return getClassName().compareTo(modelBean.getClassName());
    }

    private ShapeTypes convertShapeType(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof ShapeTypes) {
            return (ShapeTypes) value;
        }
        else if (value instanceof String) {
            return ShapeTypes.valueOf((String)value);
        }
        throw new EoException("Could not map " + value + " " + value.getClass());
    }

    private void mergeShapeType(Object value) {
        if (hasShapeType())  {
            return;
        }
        setShapeType(convertShapeType(value));
    }

    public void setIdKey(String value) {
        getProperties().put(ID_KEY, value);
    }
    public void setNaturalKeys(List<String> value) {
        getProperties().put(NATURAL_KEYS, value);
    }
    public void setTable(String value) {
        getProperties().put(TABLE, value);
    }

    public ModelBean setDbAnnotated(Boolean dbAnnotated) {
        getProperties().put(DB_ANNOTATED, dbAnnotated);
        return this;
    }

    public ModelBean setProperty(Boolean value) {
        getProperties().put(F_PROPERTY, value);
        return this;
    }

    public ModelBean setAbstract(Boolean dbAnnotated) {
        getProperties().put(ABSTRACT, dbAnnotated);
        return this;
    }

    public void setJavascriptType(String value) {
        getProperties().put(JAVASCRIPT_TYPE, value);
    }

    public String getJavascriptType() {
        return (String) getProperties().get(JAVASCRIPT_TYPE);
    }

    public boolean hasJavaScriptType() {
        return getJavascriptType()!=null && !getJavascriptType().isEmpty();
    }

    public void setFinal(Boolean value) {
        getProperties().put(F_FINAL, value);
    }

    public void setBean(String value) {
        getProperties().put(BEAN, value);
    }

    public ModelBean setCreate(Boolean create) {
        if (getProperties()==null) {
            throw new EoException("Could not set create .. properties not defined");
        }
        getProperties().put(CREATE, create);
        return this;
    }

    private void mergeRolePermissions(final Object value) {
        if (value == null) return;
        if (hasRolePermissions()) return;
        setRolePermissions(toPermissionRole(value));
    }

    public static PermissionRole toPermissionRole(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Map) {
            return new PermissionRole((Map) value);
        }
        else if (value instanceof PermissionRole) {
            return (PermissionRole) value;
        }
        else {
            throw new EoException("Unsupported type for permissionRole '" + value.getClass().getSimpleName() + "'");
        }
    }

}

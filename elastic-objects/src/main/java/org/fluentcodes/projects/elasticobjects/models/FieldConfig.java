package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Immutabel EO field configuration will be initalized by internal builder using map values.
 */
public abstract class FieldConfig extends Config implements FieldInterface {
    public static final String AND_MODEL = "' and model '";
    private final String fieldKey;
    private final String modelKeys;
    private boolean resolved;
    private final boolean toSerialize;
    private final ModelConfig parentModel;
    private Models models;
    private FieldConfigProperties properties;

    public FieldConfig(final ModelConfig parentModel, final FieldBean bean) {
        super(bean, parentModel.getConfigMaps());
        this.parentModel = parentModel;
        this.toSerialize = false;
        this.fieldKey = bean.getFieldKey();
        this.modelKeys = bean.getModelKeys();
        this.properties = new FieldConfigProperties(bean.getProperties());
    }

    public FieldConfig(final ConfigBean bean, final ConfigMaps configMaps) {
        this((FieldBean) bean, configMaps);
    }

    public FieldConfig(final FieldBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        this.toSerialize = false;
        this.fieldKey = bean.getFieldKey();
        this.modelKeys = bean.getModelKeys();
        this.properties = new FieldConfigProperties(bean.getProperties());
        parentModel = null;
    }

    public FieldConfigProperties getProperties() {
        return properties;
    }

    public boolean isResolved() {
        return resolved;
    }

    protected void resolve() {
        resolved = true;
        if (!hasModelKeys()) {
            throw new EoException("Every field needs a model type but '" + getNaturalId() + "' has none!");
        }
        this.models = new Models(parentModel.getConfigMaps(), modelKeys.split(","));
        if (!parentModel.isObject()) {
            return;
        }
        if (parentModel.getShapeType() == ShapeTypes.INTERFACE &&
                !isDefault() &&
                !isProperty()) {
            return;
        }
    }

    Boolean isJsonIgnore() {
        return properties.isJsonIgnore();
    }

    public Boolean isTransient() {
        return properties.isTransient();
    }

    Boolean isDefault() {
        return properties.isDefault();
    }

    Boolean isProperty() {
        return properties.isProperty();
    }

    Boolean isFinal() {
        return properties.isFinal();
    }

    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    public String getSqlType() {
        return getShapeTypeSerializer().getSqlType(getProperties().getMax());
    }

    public String getFieldName() {
        if (getProperties().hasFieldName()) {
            return getProperties().getFieldName();
        }
        return fieldKey;
    }

    @Override
    public String getModelKeys() {
        return this.modelKeys;
    }

    public Models getModels() {
        if (!resolved) {
            resolve();
        }
        return models;
    }

    public Class<?> getModelClass() {
        return getModels().getModelClass();
    }

    public String getModel() {
        return getModels().getModel().getModelKey();
    }

    public ModelInterface getParentModel() {
        return parentModel;
    }

    @Override
    public String toString() {
        return fieldKey + "(" + modelKeys + ")";
    }

    public Object get(final Object parent) {
        throw new EoException("No get defined for '" + getFieldKey() + "'.");
    }
    public void set(final Object parent, final Object value) {
        throw new EoException("No set defined for '" + getFieldKey() + "'.");
    }

    ShapeTypeSerializerInterface<Object> getShapeTypeSerializer() {
        return getModels().getModel().getShapeType().getShapeTypeSerializer();
    }
}

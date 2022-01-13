package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Immutabel EO field configuration will be initalized by internal builder using map values.
 */
public class FieldConfig extends Config implements FieldInterface {
    public static final String AND_MODEL = "' and model '";
    private final String fieldKey;
    private final String modelKeys;
    private boolean resolved;
    private final boolean toSerialize;
    private final ModelConfig parentModel;
    private Models models;
    private Method getter;
    private Method setter;
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

    private void resolve() {
        if (resolved) {
            return;
        }
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

        this.getter = getGetMethod(parentModel, this.fieldKey);

        if (isFinal()) {
            return;
        }

        this.setter = getSetMethod(parentModel, this.fieldKey);
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

    private Method getGetMethod(final ModelConfig model, final String fieldKey) {
        try {
            return model.getModelClass().getMethod("get" + ModelConfigObject.upper(fieldKey), null);
        } catch (NoSuchMethodException e) {
            throw new EoException("\nCould not find getter method for '" + fieldKey + AND_MODEL + parentModel.getModelKey() + "' with input type '" + models.getModelClass().getSimpleName() + "': " + e.getMessage());
        }
    }

    private Method getSetMethod(final ModelConfig model, final String fieldKey) {
        try {
            return model.getModelClass().getMethod("set" + ModelConfigObject.upper(fieldKey), models.getModelClass());
        } catch (NoSuchMethodException e) {
            throw new EoException("\nCould not find setter method for '" + fieldKey + AND_MODEL + parentModel.getNaturalId() + "' with input type '" + models.getModelClass().getSimpleName() + "': " + e.getMessage());
        }
    }

    protected Object get(Object parent) {
        resolve();
        if (getter == null) {
            throw new EoException("No getter defined '" + getNaturalId() + "' for '" + parent.getClass().getSimpleName() + "'.");
        }
        if (parent == null) {
            throw new EoException("Null parent for get '" + getNaturalId() + "' for '\" + parent.getClass().getSimpleName() + \"'.");
        }
        try {
            return getter.invoke(parent, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EoException("Problem invoke getter with '" + getNaturalId() + "' and model '" + parent.getClass().getSimpleName() + "':" + e.getMessage());
        }
    }

    protected void set(Object parent, Object value) {
        resolve();
        if (isFinal()) {
            throw new EoException("Field '" + getNaturalId() + "' marked as final for model '" + parent.getClass().getSimpleName() + "'.");
        }
        if (setter == null) {
            throw new EoException("Setter is null for field '" + getNaturalId() + "' and model '" + parent.getClass().getSimpleName() + "'.");
        }
        if (parent == null) {
            throw new EoException("Null parent for field '" + getNaturalId() + "'.");
        }
        try {
            if ((value instanceof String) && getProperties().hasLength() && getProperties().getLength() < ((String) value).length()) {
                throw new EoException("String value for field '" + getNaturalId() + "' has size " + ((String) value).length() + " bigger than max length " + getProperties().getLength() + ".");
            }
            setter.invoke(parent, value);
        } catch (EoException e) {
            throw e;
        } catch (Exception e) {
            throw new EoException("\nProblem invoke setter with '" + getNaturalId() + "' and model '" + parent.getClass().getSimpleName() + "' with value class'" + value.getClass().getSimpleName() + "':" + e.getMessage());
        }
    }

    /*.{javaAccessors}|*/
    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    @Override
    public String getModelKeys() {
        return this.modelKeys;
    }

    /*.{}.*/

    public Models getModels() {
        resolve();
        return models;
    }

    public Class<?> getModelClass() {
        return models.getModelClass();
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
}

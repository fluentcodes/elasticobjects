package org.fluentcodes.projects.elasticobjects.models;

import java.time.LocalDateTime;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Immutabel EO field configuration will be initalized by internal builder using map values.
 */
public class FieldConfigObject extends FieldConfig {
    private Method getter;
    private Method setter;
    public FieldConfigObject(final ModelConfig parentModel, final FieldBean bean) {
        super(parentModel, bean);
     }

    public FieldConfigObject(final ConfigBean bean, final ConfigMaps configMaps) {
        this((FieldBean)bean, configMaps);
    }

    public FieldConfigObject(final FieldBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }

    protected void resolve() {
        super.resolve();
        if (getParentModel().getShapeType() == ShapeTypes.INTERFACE &&
                !isDefault() &&
                !isProperty()) {
            return;
        }
        try {
            this.getter = ((ModelConfig)getParentModel()).getModelClass().getMethod("get" + ModelConfigObject.upper(getFieldKey()), null);
        } catch (NoSuchMethodException e) {
            throw new EoException("\nCould not find getter method for '" + getFieldKey() + AND_MODEL + getParentModel().getModelKey() + "' with input type '" + getModels().getModelClass().getSimpleName() + "': " + e.getMessage());
        }

        if (isFinal()) {
            return;
        }
        try {
            this.setter = ((ModelConfig)getParentModel()).getModelClass().getMethod("set" + ModelConfigObject.upper(getFieldKey()), getModels().getModelClass());
        } catch (NoSuchMethodException e) {
            throw new EoException("\nCould not find setter method for '" + getFieldKey() + AND_MODEL + getParentModel().getModelKey() + "' with input type '" + getModels().getModelClass().getSimpleName() + "': " + e.getMessage());
        }
    }

    @Override
    public Object get(Object parent) {
        if (!isResolved()) {
            resolve();
        }
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

    @Override
    public void set(Object parent, Object value) {
        if (!isResolved()) {
            resolve();
        }
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
            getShapeTypeSerializer().isValid(value, getProperties());
            if ((value instanceof String) && LocalDateTime.class == getModelClass()) {
                setter.invoke(parent, LocalDateTime.parse((String)value));
            }
            else {
                setter.invoke(parent, value);
            }
        } catch (EoException e) {
            throw e;
        } catch (Exception e) {
            throw new EoException("\nProblem invoke setter with '" + getNaturalId() + "' and model '" + parent.getClass().getSimpleName() + "' with value class'" + value.getClass().getSimpleName() + "':" + e.getMessage());
        }
    }
}

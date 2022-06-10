package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;
import java.util.Set;

/**
* Accessor for objects with setter and getter.
 */
public class ModelConfigObject extends ModelConfig {
    public ModelConfigObject(ConfigBean bean, final ConfigMaps configMaps) {
        this((ModelBean) bean, configMaps);
    }

    public ModelConfigObject(ModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        if (!bean.hasFields()) {
            return;
        }
        for (Map.Entry<String, FieldBean> entry : bean.getFields().entrySet()) {
            addField(entry.getKey(), new FieldConfigObject(this, entry.getValue()));
        }
    }

    public Models getFieldModels(final String fieldName) {
        return getField(fieldName).getModels();
    }

    public Set<String> keys(final Object object) {
        return this.getFieldKeys();
    }

    public int size(final Object object) {
        int counter = 0;
        for (String key : this.getFieldKeys()) {
            if (get(key, object) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public boolean exists(final String fieldName, final Object parent) {
        try {
            return get(fieldName, parent) != null;
        } catch (EoException e) {
            throw e;
        }
        catch (Exception e) {
            throw new EoException("Problem checking exists for '" + fieldName + "'");
        }
    }

    public void remove(final String fieldName, final Object object) {
        set(fieldName, object, null);
    }

    public Object create() {
        if (!isCreate()) {
            throw new EoException("Model has no create flag -> no empty instance will created for '" + getModelKey() + "'");
        }
        if (getShapeType() == ShapeTypes.CONFIG) {
            throw new EoException("A config has no empty constructor and can't initialized by eo " + getModelKey());
        }
        if (!hasDefaultImplementation()) {
            try {
                return getModelClass().newInstance();
            } catch (Exception e) {
                throw new EoException("Could not create empty instance of '" + getModelKey() + "': " + e.getMessage());
            }
        } else {
            ModelConfig implementation = getDefaultImplementationModel();
            try {
                return implementation.getModelClass().newInstance();
            } catch (Exception e) {
                throw new EoException("Problem create " + this.getModelKey(), e);
            }
        }
    }

    public boolean equals(ModelConfigObject modelCache) {
        if (getModelKey() == null) {
            return false;
        }
        if (modelCache == null) {
            return false;
        }
        return getModelKey().equals(modelCache.getModelKey());
    }

    public boolean isJsonIgnore(final String key) {
        return getField(key).isJsonIgnore();
    }

    public static String upper(String item) {
        if (item == null) {
            throw new EoException("String is null");
        }
        if (item.isEmpty()) {
            return "";
        }
        return item.substring(0, 1).toUpperCase() + item.substring(1);
    }

    public boolean hasFieldConfig(final String fieldName) {
        return getField(fieldName) != null;
    }

    public FieldConfig getField(final String fieldKey) {
        FieldConfig fieldConfig = getFields().get(fieldKey);
        if (fieldConfig == null) {
            throw new EoException("No fieldConfig '" + fieldKey + "' defined in model '" + this.getModelKey() + "' ! ");
        }
        return fieldConfig;
    }

    public ModelConfig getFieldModelConfig(final String fieldKey) {
        return getFieldModels(fieldKey).getModel();
    }

    public Set<String> getFieldKeys() {
        return getFields().keySet();
    }

    public boolean hasField(final String fieldKey) {
        return getFields().containsKey(fieldKey);
    }

    public boolean isNotEmpty(Object object) {
        for (String key : keys(object)) {
            if (exists(key, object)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        for (final String key : keys(object)) {
            if (exists(key, object)) {
                return false;
            }
        }
        return true;
    }

    public boolean set(final String fieldName, final Object parent, final Object value) {
        getField(fieldName).set(parent, value);
        return true;
    }

    @Override
    public boolean hasValue(String fieldKey, Object parent) {
        return get(fieldKey, parent) != null;
    }

    public Object get(final String fieldName, final Object parent) {
        return getField(fieldName).get(parent);
    }
}

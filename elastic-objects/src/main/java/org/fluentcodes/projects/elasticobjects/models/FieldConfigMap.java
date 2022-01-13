package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;

/**
 * Immutabel EO field configuration will be initalized by internal builder using map values.
 */
public class FieldConfigMap extends FieldConfig {
    public FieldConfigMap(final ModelConfig parentModel, final FieldBean bean) {
        super(parentModel, bean);
     }

    public FieldConfigMap(final FieldBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }

    @Override
    public Object get(Object parent) {
        if (!isResolved()) {
            resolve();
        }
        if (parent == null) {
            throw new EoException("Null parent for get '" + getNaturalId() + "' for '\" + parent.getClass().getSimpleName() + \"'.");
        }
        return ((Map)parent).get(getFieldKey());
    }

    @Override
    public void set(Object parent, Object value) {
        if (!isResolved()) {
            resolve();
        }
        if (isFinal()) {
            throw new EoException("Field '" + getNaturalId() + "' marked as final for model '" + parent.getClass().getSimpleName() + "'.");
        }
        if (parent == null) {
            throw new EoException("Null parent for field '" + getNaturalId() + "'.");
        }
        getShapeTypeSerializer().isValid(value, getProperties());
        ((Map)parent).put( getFieldKey(), value);
    }
}

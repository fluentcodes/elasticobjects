package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * Immutabel EO field configuration will be initalized by internal builder using map values.
 */
public class FieldConfigString extends FieldConfig {
    public FieldConfigString(final ModelConfig parentModel, final FieldBean bean) {
        super(bean, parentModel.getConfigMaps());
     }

    public FieldConfigString(final ConfigBean bean, final ConfigMaps configMaps) {
        super((FieldBean)bean, configMaps);
    }

    protected void set(Object parent, Object value) {
        if (getProperties().hasLength() && getProperties().getLength() < ((String)value).length())  {
            throw new EoException("String value for field '" + getNaturalId() + "' has size " + ((String)value).length() + " bigger than max length " + getProperties().getLength() + ".");
        }
        super.set(parent, value);
    }
}

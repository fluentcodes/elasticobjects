package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public abstract class ShapeTypeSerializerNumber<T extends Number> implements ShapeTypeSerializerInterface<T> {

    @Override
    public String asJson(T value) {
        return asString(value);
    }

    @Override
    public boolean isValid(final Object object, final FieldConfigProperties properties) {
        T value = asObject(object);
        if (properties == null) {
            return true;
        }
        if (value == null && !properties.hasMin() && !properties.hasMax()) {
            return true;
        }
        if (properties.hasMax() && properties.getMax() > -1 && properties.getMax() < value.intValue()) {
            throw new EoException("'" + value + "' is bigger than max value " + properties.getMax() + ".");
        }
        if (properties.hasMin() && properties.getMin() > -1  && properties.getMin() > value.intValue()) {
            throw new EoException("'" + value + "' is smaller than min value " + properties.getMin() + ".");
        }
        return true;
    }
}

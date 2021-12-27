package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

public class ShapeTypeSerializerFloat extends ShapeTypeSerializerNumber<Float> {
    @Override
    public Float asObject(String object) {
        if (object == null) {
            return null;
        }
        try {
            return Float.parseFloat(object);
        }
        catch (NumberFormatException e) {
            throw new EoException(e);
        }
    }

    @Override
    public Float asObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Float) {
            return (Float) object;
        }
        return asObject(object.toString());
    }
}

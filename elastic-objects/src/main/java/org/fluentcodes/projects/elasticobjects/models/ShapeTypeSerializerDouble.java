package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

public class ShapeTypeSerializerDouble extends ShapeTypeSerializerNumber<Double> {

    @Override
    public Double asObject(String object) {
        if (object == null) {
            return null;
        }
        try {
            return Double.parseDouble(object);
        }
        catch (NumberFormatException e) {
            throw new EoException(e);
        }
    }

    @Override
    public Double asObject(Object object) {
        if (object == null) {
            throw new EoInternalException("Null object " + object.getClass());
        }
        if (object instanceof Double) {
            return (Double) object;
        }
        return asObject(object.toString());
    }
}

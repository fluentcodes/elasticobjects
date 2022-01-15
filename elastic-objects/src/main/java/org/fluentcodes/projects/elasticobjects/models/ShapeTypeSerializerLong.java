package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

public class ShapeTypeSerializerLong extends ShapeTypeSerializerNumber<Long> {

    @Override
    public Long asObject(String object) {
        if (object == null) {
            return null;
        }
        try {
            return Long.parseLong(object);
        }
        catch (NumberFormatException e) {
            throw new EoException(e);
        }
    }

    @Override
    public Long asObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        return asObject(object.toString());
    }

}

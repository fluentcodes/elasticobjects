package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.lang.reflect.InvocationTargetException;

public class ShapeTypeSerializerEnum<T> implements ShapeTypeSerializerInterface<T> {
    @Override
    public T asObject(Object object) {
        throw new EoInternalException("Not implemented");
    }
    @Override
    public T asObject(String object) {
        throw new EoInternalException("Not implemented");
    }


    public T asObject(Class<?> enumClass, Object object) {
        if (object == null) {
            return null;
        }
        if (object.getClass() == enumClass) {
            return (T) object;
        }
        if (object instanceof String) {
            try {
                return (T) enumClass.getDeclaredMethod("valueOf", String.class).invoke(null, object);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new EoInternalException(e);
            }
        }
        throw new EoException("Could not map for class " + object.getClass());
    }

    @Override
    public boolean isValid(final Object object, final FieldConfigProperties properties) {
        return true;
    }
}

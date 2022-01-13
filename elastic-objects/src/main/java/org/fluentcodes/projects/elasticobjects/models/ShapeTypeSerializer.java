package org.fluentcodes.projects.elasticobjects.models;

public class ShapeTypeSerializer implements ShapeTypeSerializerInterface<Object>{

    @Override
    public Object asObject(Object value) {
        return value;
    }

    @Override
    public Object asObject(String value) {
        return value;
    }

    @Override
    public boolean isValid(Object value, FieldConfigProperties properties) {
        return true;
    }
}

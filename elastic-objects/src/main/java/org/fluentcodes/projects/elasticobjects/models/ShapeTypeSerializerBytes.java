package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.Base64;

public class ShapeTypeSerializerBytes implements ShapeTypeSerializerInterface<byte[]> {
    @Override
    public String asString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        if (bytes instanceof byte[]) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        throw new EoInternalException("Not a byte array but " + bytes.getClass());
    }

    @Override
    public byte[] asObject(Object object) {
        if (object == null) {
            return new byte[]{};
        }
        if (object instanceof byte[]) {
            return (byte[]) object;
        }
        throw new EoInternalException("Not a String but " + object.getClass());
    }

    @Override
    public byte[] asObject(String object) {
        if (object == null) {
            return new byte[]{};
        }
        return Base64.getDecoder().decode(object.getBytes());
    }

    @Override
    public boolean isValid(final Object object, final FieldConfigProperties properties) {
        return true;
    }

    @Override
    public String getSqlType(Integer max) {
        return "binary";
    }
}

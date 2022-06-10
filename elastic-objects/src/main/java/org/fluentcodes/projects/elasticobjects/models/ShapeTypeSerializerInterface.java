package org.fluentcodes.projects.elasticobjects.models;

public interface ShapeTypeSerializerInterface<T> {
    default String asString(final T value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    default String asJson(T value) {
        return "\"" + asString(value) + "\"";
    }

    T asObject(Object value);
    String getSqlType(Integer max);
    T asObject(String value);
    boolean isValid(Object value, FieldConfigProperties properties);

    default boolean compare(Object right, Object left) {
        if (right == null && left == null) {
            return true;
        }
        if (right == null || left == null) {
            return false;
        }
        T rightValue = asObject(right);
        T leftValue = asObject(left);
        return rightValue.equals(leftValue);
    }
}

package org.fluentcodes.projects.elasticobjects;

public interface EOInterfaceSerialize extends EOInterfaceBase {

    default JSONSerializationType getSerializationType() {
        return getRoot().getSerializationType();
    }

    default void setSerializationType(JSONSerializationType serializationType) {
        getRoot().setSerializationType(serializationType);
    }

    default boolean isCheckObjectReplication() {
        return getRoot().isCheckObjectReplication();
    }

    default void setCheckObjectReplication(boolean checkObjectReplication) {
        getRoot().setCheckObjectReplication(checkObjectReplication);
    }
}

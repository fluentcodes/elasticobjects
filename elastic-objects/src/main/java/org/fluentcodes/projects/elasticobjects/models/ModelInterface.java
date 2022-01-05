package org.fluentcodes.projects.elasticobjects.models;

public interface ModelInterface {
    String SHAPE_TYPE = "shapeType";
    String CLASS_PATH = "classPath";

    String getModelKey();

    default boolean hasModelKey() {
        return getModelKey() != null && !getModelKey().isEmpty();
    }

    default String getKey() {
        if (hasModelKey()) return getModelKey();
        return "";
    }

    default boolean hasKey() {
        return !getModelKey().isEmpty();
    }

    String getPackagePath();

    default boolean hasPackagePath() {
        return getPackagePath() != null && !getPackagePath().isEmpty();
    }

    String getSuperKey();

    default boolean hasSuperKey() {
        return getSuperKey() != null && !getSuperKey().isEmpty();
    }

    String getInterfaces();

    default boolean hasInterfaces() {
        return getInterfaces() != null && !getInterfaces().isEmpty();
    }


    default boolean hasShapeType() {
        return getShapeType() != null;
    }

    ShapeTypes getShapeType();
}

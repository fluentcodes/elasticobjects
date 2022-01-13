package org.fluentcodes.projects.elasticobjects.models;


import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 18.11.2014.
 */
public enum ShapeTypes {
    MAP(ModelConfigMap.class),
    LIST(ModelConfigList.class),
    INTERFACE(),
    OBJECT(),
    SCALAR_SERIALIZED(ModelConfigScalar.class),
    SCALAR(ModelConfigScalar.class, new ShapeTypeSerializerString()),
    STRING(ModelConfigScalar.class, new ShapeTypeSerializerString()),
    DOUBLE(ModelConfigScalar.class, new ShapeTypeSerializerDouble()),
    FLOAT(ModelConfigScalar.class, new ShapeTypeSerializerFloat()),
    INTEGER(ModelConfigScalar.class, new ShapeTypeSerializerInteger()),
    LONG(ModelConfigScalar.class, new ShapeTypeSerializerLong()),
    DATE(ModelConfigScalar.class, new ShapeTypeSerializerDate()),
    BOOLEAN(ModelConfigScalar.class, new ShapeTypeSerializerBoolean()),
    ENUM(ModelConfigScalar.class, new ShapeTypeSerializerEnum()),
    NULL(ModelConfigScalar.class),
    NONE(ModelConfigScalar.class),
    BEAN(),
    CALL_BEAN(),
    MODEL(),
    INSTANCE(),
    CONFIG();
    private Class<? extends ModelConfig> modelConfig;
    private ShapeTypeSerializerInterface serializer;

    ShapeTypes() {
        this(ModelConfigObject.class, new ShapeTypeSerializer());
    }

    ShapeTypes(Class<? extends ModelConfig> modelConfig) {
        this(modelConfig, new ShapeTypeSerializer());
    }

    ShapeTypes(Class<? extends ModelConfig> modelConfig, ShapeTypeSerializerInterface serializer) {
        this.modelConfig = modelConfig;
        this.serializer = serializer;
    }

    public Class getModelConfig() {
        return modelConfig;
    }

    public boolean hasModelConfig() {
        return modelConfig != null;
    }

    public String getModelConfigKey() {
        return modelConfig.getSimpleName();
    }

    public ShapeTypeSerializerInterface<Object> getShapeTypeSerializer() {
        return serializer;
    }

    public String asString(Object object) {
        return serializer.asString(object);
    }

    public boolean compare(Object left, Object right) {
        return serializer.compare(left, right);
    }

    public String asJson(Object object) {
        return serializer.asJson(object);
    }

    public Object asObject(Object object) {
        return serializer.asObject(object);
    }

    public Object asObject(Class<?> enumClass, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return ((ShapeTypeSerializerEnum) serializer).asObject(enumClass, (String) object);
        }
        if (object.getClass() == enumClass) {
            return object;
        }
        throw new EoException("Could not map enum for " + object.getClass());
    }

    public boolean isNumber() {
        return this == ShapeTypes.DOUBLE ||
                this == ShapeTypes.FLOAT ||
                this == ShapeTypes.LONG ||
                this == ShapeTypes.INTEGER;
    }
}

package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

public interface FieldBeanInterface extends FieldInterface {
    Map<String, Object> getProperties();

    @Override
    default Boolean getDefault() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_DEFAULT));
    }

    default FieldBeanInterface setDefault(Boolean value) {
        getProperties().put(F_DEFAULT, value);
        return this;
    }

    @Override
    default String getFieldName() {
        return new ShapeTypeSerializerString().asObject(getProperties().get(F_FIELD_NAME));
    }

    default FieldBeanInterface setFieldName(String value) {
        getProperties().put(F_FIELD_NAME, value);
        return this;
    }

    @Override
    default Boolean getFinal() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_FINAL));
    }

    default FieldBeanInterface setFinal(Boolean value) {
        getProperties().put(F_FINAL, value);
        return this;
    }

    @Override
    default Boolean getGenerated() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_GENERATED));
    }

    default FieldBeanInterface setGenerated(Boolean value) {
        getProperties().put(F_GENERATED, value);
        return this;
    }

    @Override
    default String getJavascriptType() {
        return new ShapeTypeSerializerString().asObject(getProperties().get(F_JAVASCRIPT_TYPE));
    }

    default FieldBeanInterface setJavascriptType(String value) {
        getProperties().put(F_JAVASCRIPT_TYPE, value);
        return this;
    }

    @Override
    default Boolean getJsonIgnore() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_JSON_IGNORE));
    }

    default FieldBeanInterface setJsonIgnore(Boolean value) {
        getProperties().put(F_JSON_IGNORE, value);
        return this;
    }

    @Override
    default Integer getLength() {
        return (Integer) getProperties().get(F_LENGTH);
    }

    default FieldBeanInterface setLength(final Integer length) {
        getProperties().put(F_LENGTH, length);
        return this;
    }

    @Override
    default Integer getMax(){
        return (Integer) getProperties().get(F_MAX);
    }

    default FieldBeanInterface setMax(Integer value) {
        getProperties().put(F_MAX, value);
        return this;
    }

    @Override
    default Integer getMin(){
        return (Integer) getProperties().get(F_MIN);
    }

    default FieldBeanInterface setMin(Integer value) {
        getProperties().put(F_MIN, value);
        return this;
    }

    @Override
    default Boolean getNotNull() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_NOT_NULL));
    }

    default FieldBeanInterface setNotNull(Boolean value) {
        getProperties().put(F_NOT_NULL, value);
        return this;
    }

    @Override
    default Boolean getOverride(){
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_OVERRIDE));
    }

    default FieldBeanInterface setOverride(Boolean value) {
        getProperties().put(F_OVERRIDE, value);
        return this;
    }

    @Override
    default Boolean getProperty(){
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_PROPERTY));
    }

    default FieldBeanInterface setProperty(Boolean value) {
        getProperties().put(F_PROPERTY, value);
        return this;
    }

    @Override
    default Boolean getStaticName() {
        return new ShapeTypeSerializerBoolean().asObject( getProperties().get(F_STATIC_NAME));
    }

    default FieldBeanInterface setStaticName(Boolean value) {
        getProperties().put(F_STATIC_NAME, value);
        return this;
    }

    @Override
    default Boolean getSuper() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_SUPER));
    }

    default FieldBeanInterface setSuper(Boolean value) {
        getProperties().put(F_SUPER, value);
        return this;
    }

    @Override
    default Boolean getTransient() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_TRANSIENT));
    }

    default FieldBeanInterface setTransient(Boolean value) {
        getProperties().put(F_TRANSIENT, value);
        return this;
    }

    @Override
    default Boolean getUnique() {
        return new ShapeTypeSerializerBoolean().asObject(getProperties().get(F_UNIQUE));
    }

    default FieldBeanInterface setUnique(Boolean value) {
        getProperties().put(F_UNIQUE, value);
        return this;
    }

}

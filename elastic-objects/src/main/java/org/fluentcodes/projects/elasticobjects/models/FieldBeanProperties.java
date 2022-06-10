package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ID_KEY;

/**
 * The basic bean container class for the configuration class {@link FieldConfig}. Also used as a base for building source code.
 */
public class FieldBeanProperties implements FieldPropertiesInterface {

    /*.{javaStaticNames}|*/
    public static final String F_DEFAULT = "default";
    public static final String F_FIELD_NAME = "fieldName";
    public static final String F_FINAL = "final";
    public static final String F_GENERATED = "generated";
    public static final String F_JAVASCRIPT_TYPE = "javascriptType";
    public static final String F_JSON_IGNORE = "jsonIgnore";
    public static final String F_MAX = "max";
    public static final String F_MIN = "min";
    public static final String F_NOT_NULL = "notNull";
    public static final String F_OVERRIDE = "override";
    public static final String F_PROPERTY = "property";
    public static final String F_STATIC_NAME = "staticName";
    public static final String F_SUPER = "super";
    public static final String F_TRANSIENT = "transient";
    public static final String F_UNIQUE = "unique";

    private Boolean defaultValue;
    private String fieldName;
    private Boolean finalValue;
    private Boolean generated;
    private String idKey;
    private String javascriptType;
    private Boolean jsonIgnore;
    private Boolean notNull;
    private Integer max;
    private Integer min;
    private Boolean override;
    private Boolean property;
    private Boolean staticName;
    private Boolean superValue;
    private Boolean transientValue;
    private Boolean unique;

    public FieldBeanProperties() {
    }

    public FieldBeanProperties(final Map<String, Object> properties) {
        setDefault(
                ConfigBean.toBoolean(properties.get(F_DEFAULT)));
        setFieldName(
                ConfigBean.toString(properties.get(F_FIELD_NAME)));
        setFinal(
                ConfigBean.toBoolean(properties.get(F_FINAL)));
        setGenerated(
                ConfigBean.toBoolean(properties.get(F_GENERATED)));
        setIdKey(
                ConfigBean.toString(properties.get(F_ID_KEY)));
        setJavascriptType(
                ConfigBean.toString(properties.get(F_JAVASCRIPT_TYPE)));
        setJsonIgnore(
                ConfigBean.toBoolean(properties.get(F_JSON_IGNORE)));
        setMax(
                ConfigBean.toInteger(properties.get(F_MAX)));
        setMin(
                ConfigBean.toInteger(properties.get(F_MIN)));
        setNotNull(
                ConfigBean.toBoolean(properties.get(F_NOT_NULL)));
        setOverride(
                ConfigBean.toBoolean(properties.get(F_OVERRIDE)));
        setProperty(
                ConfigBean.toBoolean(properties.get(F_PROPERTY)));
        setStaticName(
                ConfigBean.toBoolean(properties.get(F_STATIC_NAME)));
        setSuper(
                ConfigBean.toBoolean(properties.get(F_SUPER)));
        setTransient(
                ConfigBean.toBoolean(properties.get(F_TRANSIENT)));
        setUnique(
                ConfigBean.toBoolean(properties.get(F_UNIQUE)));
    }

    public FieldBeanProperties(final FieldConfigProperties config) {
        setDefault(config.getDefault());
        setFieldName(config.getFieldName());
        setFinal(config.getFinal());
        setGenerated(config.getGenerated());
        setIdKey(config.getIdKey());
        setJavascriptType(config.getJavascriptType());
        setJsonIgnore(config.getJsonIgnore());
        setMax(config.getMax());
        setMin(config.getMin());
        setNotNull(config.getNotNull());
        setOverride(config.getOverride());
        setProperty(config.getProperty());
        setStaticName(config.getStaticName());
        setSuper(config.getSuper());
        setTransient(config.getTransient());
        setUnique(config.getUnique());
    }

    void merge(final FieldBeanProperties bean) {
        mergeDefault(bean.getDefault());
        mergeFieldName(bean.getFieldName());
        mergeFinal(bean.getFinal());
        mergeGenerated(bean.getGenerated());
        mergeIdKey(bean.getIdKey());
        mergeJavascriptType(bean.getJavascriptType());
        mergeJsonIgnore(bean.getJsonIgnore());
        mergeMax(bean.getMax());
        mergeMin(bean.getMin());
        mergeNotNull(bean.getNotNull());
        mergeOverride(bean.getOverride());
        mergeProperty(bean.getProperty());
        mergeStaticName(bean.getStaticName());
        mergeSuper(true);
        mergeTransient(bean.getTransient());
        mergeUnique(bean.getUnique());
    }

    @Override
    public Boolean getDefault() {
        return defaultValue;
    }

    public FieldBeanProperties setDefault(Boolean value) {
        this.defaultValue = value;
        return this;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public FieldBeanProperties setFieldName(String value) {
        this.fieldName = value;
        return this;
    }

    @Override
    public Boolean getFinal() {
        return finalValue;
    }

    public FieldBeanProperties setFinal(Boolean value) {
        this.finalValue = value;
        return this;
    }

    @Override
    public Boolean getGenerated() {
        return generated;
    }

    public FieldBeanProperties setGenerated(Boolean value) {
        this.generated = value;
        return this;
    }

    @Override
    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    @Override
    public String getJavascriptType() {
        return javascriptType;
    }

    public FieldBeanProperties setJavascriptType(String value) {
        this.javascriptType = value;
        return this;
    }

    @Override
    public Boolean getJsonIgnore() {
        return jsonIgnore;
    }

    public FieldBeanProperties setJsonIgnore(Boolean value) {
        this.jsonIgnore = value;
        return this;
    }

    @Override
    public Integer getMax() {
        return max;
    }

    public FieldBeanProperties setMax(Integer value) {
        this.max = value;
        return this;
    }

    @Override
    public Integer getMin() {
        return min;
    }

    public FieldBeanProperties setMin(Integer value) {
        this.min = value;
        return this;
    }

    @Override
    public Boolean getNotNull() {
        return notNull;
    }

    public FieldBeanProperties setNotNull(Boolean value) {
        this.notNull = value;
        return this;
    }

    @Override
    public Boolean getOverride() {
        return override;
    }

    public FieldBeanProperties setOverride(Boolean value) {
        this.override = value;
        return this;
    }

    @Override
    public Boolean getProperty() {
        return property;
    }

    public FieldBeanProperties setProperty(Boolean value) {
        this.property = value;
        return this;
    }

    @Override
    public Boolean getStaticName() {
        return staticName;
    }

    public FieldBeanProperties setStaticName(Boolean value) {
        this.staticName = value;
        return this;
    }

    @Override
    public Boolean getSuper() {
        return superValue;
    }

    public FieldBeanProperties setSuper(Boolean value) {
        this.superValue = value;
        return this;
    }

    @Override
    public Boolean getTransient() {
        return transientValue;
    }

    public FieldBeanProperties setTransient(Boolean value) {
        this.transientValue = value;
        return this;
    }

    @Override
    public Boolean getUnique() {
        return unique;
    }

    public FieldBeanProperties setUnique(Boolean value) {
        this.unique = value;
        return this;
    }

    private void mergeDefault(final Object value) {
        if (hasDefault()) return;
        setDefault(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeFieldName(final Object value) {
        if (value == null) return;
        if (hasFieldName()) return;
        setFieldName(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeFinal(final Object value) {
        if (hasFinal()) return;
        setFinal(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeGenerated(final Object value) {
        if (hasGenerated()) return;
        setGenerated(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeIdKey(final Object value) {
        if (hasIdKey()) return;
        setIdKey(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeJavascriptType(final Object value) {
        if (hasJavascriptType()) return;
        setJavascriptType(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeJsonIgnore(final Object value) {
        if (hasJsonIgnore()) return;
        setJsonIgnore(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeMax(final Object value) {
        if (hasMax()) return;
        setMax(new ShapeTypeSerializerInteger().asObject(value, -1));
    }

    private void mergeMin(final Object value) {
        if (hasMin()) return;
        setMin(new ShapeTypeSerializerInteger().asObject(value, -1));
    }

    private void mergeNotNull(final Object value) {
        if (hasNotNull()) return;
        setNotNull(new ShapeTypeSerializerBoolean().asObject(value, false));
    }


    private void mergeOverride(final Object value) {
        if (hasOverride()) return;
        setOverride(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeProperty(final Object value) {
        if (hasProperty()) return;
        setProperty(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeStaticName(final Object value) {
        if (hasStaticName()) return;
        setStaticName(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeSuper(final Object value) {
        if (hasSuper()) return;
        setSuper(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeTransient(final Object value) {
        if (hasTransient()) return;
        setTransient(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeUnique(final Object value) {
        if (hasUnique()) return;
        setUnique(new ShapeTypeSerializerBoolean().asObject(value, false));
    }
}

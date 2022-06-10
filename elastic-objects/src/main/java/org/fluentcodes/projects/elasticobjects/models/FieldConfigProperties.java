package org.fluentcodes.projects.elasticobjects.models;

/**
 * Immutabel EO field configuration will be initalized by internal builder using map values.
 */
public class FieldConfigProperties implements FieldPropertiesInterface {
    public static final String AND_MODEL = "' and model '";
    private final Boolean defaultValue;
    private final String fieldName;
    private final Boolean finalValue;
    private final Boolean generated;
    private final String javascriptType;
    private final Boolean jsonIgnore;
    private final String idKey;
    private final Integer max;
    private final Integer min;
    private final Boolean notNull;
    private final Boolean override;
    private final Boolean property;
    private final Boolean staticName;
    private final Boolean superValue;
    private final Boolean transientValue;
    private final Boolean unique;

    public FieldConfigProperties(final FieldBeanProperties bean) {
        this.defaultValue = bean.getDefault();
        this.fieldName = bean.getFieldName();
        this.finalValue = bean.getFinal();
        this.generated = bean.getGenerated();
        this.idKey = bean.getIdKey();
        this.javascriptType = bean.getJavascriptType();
        this.jsonIgnore = bean.getJsonIgnore();
        this.max = bean.getMax();
        this.min = bean.getMin();
        this.override = bean.getOverride();
        this.property = bean.getProperty();
        this.staticName = bean.getStaticName();
        this.transientValue = bean.getTransient();
        this.unique = bean.getUnique();
        this.superValue = bean.getSuper();
        this.notNull = bean.getNotNull();
    }

    @Override
    public Boolean getDefault() {
        return defaultValue;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Boolean getFinal() {
        return finalValue;
    }

    @Override
    public Boolean getGenerated() {
        return generated;
    }

    @Override
    public String getIdKey() {
        return idKey;
    }

    @Override
    public String getJavascriptType() {
        return this.javascriptType;
    }

    @Override
    public Boolean getJsonIgnore() {
        return jsonIgnore;
    }

    @Override
    public Boolean getNotNull() {
        return notNull;
    }

    @Override
    public Integer getMax() {
        return max;
    }

    @Override
    public Integer getMin() {
        return min;
    }

    @Override
    public Boolean getOverride() {
        return override;
    }

    @Override
    public Boolean getProperty() {
        return property;
    }

    @Override
    public Boolean getStaticName() {
        return staticName;
    }

    @Override
    public Boolean getTransient() {
        return transientValue;
    }

    @Override
    public Boolean getUnique() {
        return unique;
    }

    @Override
    public Boolean getSuper() {
        return superValue;
    }
}

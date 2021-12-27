package org.fluentcodes.projects.elasticobjects.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/*.{javaHeader}|*/

/**
 * The basic bean container class for the configuration class {@link FieldConfig}. Also used as a base for building source code.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 09 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 04:43:19 CET 2021
 */
public class FieldBean extends ConfigBean implements FieldBeanInterface {
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /* fieldKey */
    private String fieldKey;
    /* A string representation for a list of modelsConfig. */
    private String modelKeys;
    /*.{}.*/
    private boolean merged = false;

    private ModelInterface parentModel;

    public FieldBean() {
        super();
    }

    public FieldBean(String key) {
        super(key);
        setFieldKey(key.replaceAll(".*\\.", ""));
        defaultNaturalId();
    }

    public FieldBean(final Map valueMap) {
        super(valueMap);
        Map<String, Object> properties = valueMap.containsKey(F_PROPERTIES) && valueMap.get(F_PROPERTIES)!=null ?
                (Map<String, Object>)valueMap.get(F_PROPERTIES):
                valueMap;
        setDefault(
                toBoolean(properties.get(F_DEFAULT)));
        setFieldKey(
                toString(valueMap.get(F_FIELD_KEY)));
        setFieldName(
                toString(properties.get(F_FIELD_NAME)));
        setFinal(
                toBoolean(properties.get(F_FINAL)));
        setGenerated(
                toBoolean(properties.get(F_GENERATED)));
        setJavascriptType(
                toString(properties.get(F_JAVASCRIPT_TYPE)));
        setJsonIgnore(
                toBoolean(properties.get(F_JSON_IGNORE)));
        setLength(
                toInteger(properties.get(F_LENGTH)));
        setMax(
                toInteger(properties.get(F_MAX)));
        setMin(
                toInteger(properties.get(F_MIN)));
        setModelKeys(
                toString(valueMap.get(F_MODEL_KEYS)));
        setNotNull(
                toBoolean(properties.get(F_NOT_NULL)));
        setOverride(
                toBoolean(properties.get(F_OVERRIDE)));
        setProperty(
                toBoolean(properties.get(F_PROPERTY)));
        setStaticName(
                toBoolean(properties.get(F_STATIC_NAME)));
        setSuper(
                toBoolean(properties.get(F_SUPER)));
        setTransient(
                toBoolean(properties.get(F_TRANSIENT)));
        setUnique(
                toBoolean(properties.get(F_UNIQUE)));
        defaultNaturalId();
    }

    public FieldBean(final FieldConfig config) {
        super(config);
        setDefault(config.getDefault());
        setFieldKey(config.getFieldKey());
        setFieldName(config.getFieldName());
        setFinal(config.getFinal());
        setGenerated(config.getGenerated());
        setJavascriptType(config.getJavascriptType());
        setJsonIgnore(config.getJsonIgnore());
        setLength(config.getLength());
        setMax(config.getMax());
        setMin(config.getMin());
        setModelKeys(config.getModelKeys());
        setNotNull(config.getNotNull());
        setOverride(config.getOverride());
        setProperty(config.getProperty());
        setStaticName(config.getStaticName());
        setSuper(config.getSuper());
        setTransient(config.getTransient());
        setUnique(config.getUnique());
    }

    public FieldBean(final String key, final Map values) {
        this(values);
        if (!hasNaturalId()) setNaturalId(key);
    }

    public FieldBean(final Field field) {
        Class<?> modelClass = field.getDeclaringClass();
        Class<?> typeClass = field.getType();
        setFieldKey(field.getName());
        setNaturalId(modelClass.getSimpleName() + "." + field.getName());
        setModelKeys(typeClass.getSimpleName());
    }

    protected FieldBean(final FieldBean fieldBean) {
        super();
        this.merge((FieldBean) fieldBean);
        setSuper(true);
    }

    public FieldBean get() {
        return this;
    }

    void merge(final FieldBean fieldBean) {
        super.merge(fieldBean);
        mergeConfigModelKey(fieldBean.getModelKey());
        mergeDefault(fieldBean.getDefault());
        mergeFieldKey(fieldBean.getFieldKey());
        mergeFieldName(fieldBean.getFieldName());
        mergeFinal(fieldBean.getFinal());
        mergeGenerated(fieldBean.getGenerated());
        mergeJavascriptType(fieldBean.getJavascriptType());
        mergeJsonIgnore(fieldBean.getJsonIgnore());
        mergeLength(fieldBean.getLength());
        mergeMax(fieldBean.getMax());
        mergeMin(fieldBean.getMin());
        mergeModelKeys(fieldBean.getModelKeys());
        mergeNotNull(fieldBean.getNotNull());
        mergeOverride(fieldBean.getOverride());
        mergeProperty(fieldBean.getProperty());
        mergeStaticName(fieldBean.getStaticName());
        mergeSuper(fieldBean.getSuper());
        mergeTransient(fieldBean.getTransient());
        mergeUnique(fieldBean.getUnique());
        setDefault();
        this.merged = true;
    }

    public void setDefault() {
        defaultConfigModelKey();
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(FieldConfig.class.getSimpleName());
    }

    private void defaultNaturalId() {
        if (hasNaturalId()) {
            return;
        }
        setNaturalId(getFieldKey());
    }

    public List<String> getModelList() {
        if (!hasModelKeys()) {
            return new ArrayList<>();
        }
        return Arrays.asList(modelKeys.split(","));
    }

    /*.{javaAccessors}|*/

    private void mergeDefault(final Object value) {
        if (hasDefault()) return;
        setDefault(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    public FieldBean setFieldKey(final String fieldKey) {
        this.fieldKey = fieldKey;
        return this;
    }

    private void mergeFieldKey(final Object value) {
        if (value == null) return;
        if (hasFieldKey()) return;
        setFieldKey(new ShapeTypeSerializerString().asObject(value));
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

    private void mergeJavascriptType(final Object value) {
        if (hasJavascriptType()) return;
        setJavascriptType(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeJsonIgnore(final Object value) {
        if (hasJsonIgnore()) return;
        setJsonIgnore(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    private void mergeLength(final Object value) {
        if (hasLength()) return;
        setLength(new ShapeTypeSerializerInteger().asObject(value, -1));
    }

    private void mergeMax(final Object value) {
        if (hasMax()) return;
        setMax(new ShapeTypeSerializerInteger().asObject(value, -1));
    }

    private void mergeMin(final Object value) {
        if (hasMin()) return;
        setMin(new ShapeTypeSerializerInteger().asObject(value, -1 ));
    }

    private void mergeNotNull(final Object value) {
        if (hasNotNull()) return;
        setNotNull(new ShapeTypeSerializerBoolean().asObject(value, false));
    }

    @Override
    public String getModelKeys() {
        return this.modelKeys;
    }

    public FieldBean setModelKeys(final String modelKeys) {
        this.modelKeys = modelKeys;
        return this;
    }

    private void mergeModelKeys(final Object value) {
        if (value == null) return;
        if (hasModelKeys()) return;
        setModelKeys(new ShapeTypeSerializerString().asObject(value));
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


    /*.{}.*/
    public boolean isMerged() {
        return merged;
    }

    void setMerged(boolean merged) {
        this.merged = merged;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!hasKey()) return "";
        builder.append(getKey());
        if (this.hasParentModelKey()) builder.insert(0, getParentModel().getModelKey() + ".");
        return hasModelKeys() ?
                "(" + modelKeys + ")" + builder.toString() :
                builder.toString();
    }

    @Override
    public ModelInterface getParentModel() {
        return parentModel;
    }

    public void setParentModel(ModelInterface modelBean) {
        this.parentModel = modelBean;
    }
}

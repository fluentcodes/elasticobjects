package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

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
    /* Length of a field. */
    private Integer length;
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
    }

    public FieldBean(final Map values) {
        super();
        merge(values);
        defaultValues();
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

    public FieldBean(final FieldConfig config) {
        super(config);
        setFieldKey(config.getFieldKey());
        setLength(config.getLength());
        setModelKeys(config.getModelKeys());
    }

    protected FieldBean(final FieldBean fieldBean) {
        super();
        this.merge((FieldBean) fieldBean);
        setSuper(true);
    }

    public FieldBean get() {
        return this;
    }

    @Override
    public void merge(final Map configMap) {
        super.merge(configMap);
        setNaturalId((String) configMap.get(F_NATURAL_ID));
        setFieldKey((String) configMap.get(F_FIELD_KEY));
        mergeFinal(configMap.get(F_FINAL));
        mergeOverride(configMap.get(F_OVERRIDE));
        mergeJsonIgnore(configMap.get(F_JSON_IGNORE));
        mergeTransient(configMap.get(F_TRANSIENT));
        mergeDefault(configMap.get(F_DEFAULT));

        setLength(new ShapeTypeSerializerInteger().asObject(configMap.get(F_LENGTH)));
        setModelKeys((String) configMap.get(F_MODEL_KEYS));
    }

    public void merge(final FieldBean fieldBean) {
        super.merge(fieldBean);
        mergeFieldKey(fieldBean.getFieldKey());
        mergeModelKeys(fieldBean.getModelKeys());
        mergeLength(fieldBean.getLength());
        mergeFinal(fieldBean.getFinal());
        mergeProperty(fieldBean.getProperty());
        mergeFieldName(fieldBean.getFieldName());
        mergeGenerated(fieldBean.getGenerated());
        this.merged = true;
    }

    private void defaultValues() {
        // default values for templates
        defaultOverride();
        defaultNotNull();
        defaultGenerated();
        defaultFinal();
        defaultTransient();
        defaultUnique();
        defaultSuper();
        defaultConfigModelKey();
    }

    public List<String> getModelList() {
        if (!hasModelKeys()) {
            return new ArrayList<>();
        }
        return Arrays.asList(modelKeys.split(","));
    }

    /*.{javaAccessors}|*/
    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    public FieldBean setFieldKey(final String fieldKey) {
        this.fieldKey = fieldKey;
        return this;
    }

    @Override
    public Integer getLength() {
        return this.length;
    }

    public FieldBean setLength(final Integer length) {
        this.length = length;
        return this;
    }

    @Override
    public Integer getMax(){
        return (Integer) getProperties().get(F_MAX);
    }

    @Override
    public boolean hasMax() {
        return getProperties().containsKey(F_MAX) && getProperties().get(F_MAX) != null;
    }

    @Override
    public Integer getMin(){
        return (Integer) getProperties().get(F_MIN);
    }

    @Override
    public boolean hasMin() {
        return getProperties().containsKey(F_MIN) && getProperties().get(F_MIN) != null;
    }

    @Override
    public Boolean getOverride(){
        return (Boolean) getProperties().get(F_OVERRIDE);
    }

    @Override
    public boolean hasOverride() {
        return getProperties().containsKey(F_OVERRIDE) && getProperties().get(F_OVERRIDE) != null;
    }

    @Override
    public Boolean getProperty(){
        return (Boolean) getProperties().get(F_PROPERTY);
    }

    @Override
    public boolean hasProperty() {
        return getProperties().containsKey(F_PROPERTY) && getProperties().get(F_PROPERTY) != null;
    }

    @Override
    public Boolean getStaticName() {
        return (Boolean) getProperties().get(F_STATIC_NAME);
    }

    @Override
    public boolean hasStaticName() {
        return getProperties().containsKey(F_STATIC_NAME) && getProperties().get(F_STATIC_NAME) != null;
    }

    @Override
    public Boolean getTransient() {
        return (Boolean) getProperties().get(F_TRANSIENT);
    }

    @Override
    public boolean hasTransient() {
        return getProperties().containsKey(F_TRANSIENT) && getProperties().get(F_TRANSIENT) != null;
    }

    @Override
    public Boolean getUnique() {
        return (Boolean) getProperties().get(F_UNIQUE);
    }

    @Override
    public boolean hasUnique() {
        return getProperties().containsKey(F_UNIQUE) && getProperties().get(F_UNIQUE) != null;
    }

    @Override
    public Boolean getSuper() {
        return (Boolean) getProperties().get(F_SUPER);
    }

    @Override
    public boolean hasSuper() {
        return getProperties().containsKey(F_SUPER) && getProperties().get(F_SUPER) != null;
    }

    @Override
    public Boolean getDefault() {
        return (Boolean) getProperties().get(F_DEFAULT);
    }

    @Override
    public boolean hasDefault() {
        return getProperties().containsKey(F_DEFAULT) && getProperties().get(F_DEFAULT) != null;
    }

    @Override
    public String getFieldName() {
        return (String) getProperties().get(F_FIELD_NAME);
    }

    @Override
    public boolean hasFieldName() {
        return getProperties().containsKey(F_FIELD_NAME) && getProperties().get(F_FIELD_NAME) != null;
    }

    @Override
    public Boolean getFinal() {
        return (Boolean) getProperties().get(F_FINAL);
    }

    @Override
    public boolean hasFinal() {
        return getProperties().containsKey(F_FINAL) && getProperties().get(F_FINAL) != null;
    }

    @Override
    public Boolean getGenerated() {
        return (Boolean) getProperties().get(F_GENERATED);
    }

    @Override
    public boolean hasGenerated() {
        return getProperties().containsKey(F_GENERATED) && getProperties().get(F_GENERATED) != null;
    }

    @Override
    public String getJavascriptType() {
        return (String) getProperties().get(F_JAVASCRIPT_TYPE);
    }

    @Override
    public boolean hasJavascriptType() {
        return getProperties().containsKey(F_JAVASCRIPT_TYPE) && getProperties().get(F_JAVASCRIPT_TYPE) != null;
    }


    @Override
    public Boolean getJsonIgnore() {
        return (Boolean) getProperties().get(F_JSON_IGNORE);
    }

    @Override
    public boolean hasJsonIgnore() {
        return getProperties().containsKey(F_JSON_IGNORE) && getProperties().get(F_JSON_IGNORE) != null;
    }

    @Override
    public Boolean getNotNull() {
        return (Boolean) getProperties().get(F_NOT_NULL);
    }

    @Override
    public boolean hasNotNull() {
        return getProperties().containsKey(F_NOT_NULL) && getProperties().get(F_NOT_NULL) != null;
    }

    @Override
    public String getModelKeys() {
        return this.modelKeys;
    }


    public FieldBean setModelKeys(final String modelKeys) {
        this.modelKeys = modelKeys;
        return this;
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

    private void mergeGenerated(final Object value) {
        if (value == null) return;
        if (hasGenerated()) return;
        setGenerated(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeJavascriptType(final Object value) {
        if (value == null) return;
        if (hasJavascriptType()) return;
        setJavascriptType(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeJsonIgnore(final Object value) {
        if (value == null) return;
        if (hasJsonIgnore()) return;
        setJsonIgnore(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeMax(final Object value) {
        if (value == null) return;
        if (hasMax()) return;
        setMax(new ShapeTypeSerializerInteger().asObject(value));
    }

    private void mergeMin(final Object value) {
        if (value == null) return;
        if (hasMin()) return;
        setMin(new ShapeTypeSerializerInteger().asObject(value));
    }

    private void mergeNotNull(final Object value) {
        if (value == null) return;
        if (hasNotNull()) return;
        setNotNull(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeProperty(final Object value) {
        if (value == null) return;
        if (hasProperty()) return;
        setProperty(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeStaticName(final Object value) {
        if (value == null) return;
        if (hasStaticName()) return;
        setStaticName(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeSuper(final Object value) {
        if (value == null) return;
        if (hasSuper()) return;
        setSuper(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeTransient(final Object value) {
        if (value == null) return;
        if (hasTransient()) return;
        setTransient(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeUnique(final Object value) {
        if (value == null) return;
        if (hasUnique()) return;
        setUnique(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeOverride(final Object value) {
        if (value == null) return;
        if (hasOverride()) return;
        setOverride(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeModelKeys(final Object value) {
        if (value == null) return;
        if (hasModelKeys()) return;
        setModelKeys(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeLength(final Object value) {
        if (value == null) return;
        if (hasLength()) return;
        setLength(new ShapeTypeSerializerInteger().asObject(value));
    }

    private void mergeFinal(final Object value) {
        if (value == null) return;
        if (hasFinal()) return;
        setFinal(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeFieldName(final Object value) {
        if (value == null) return;
        if (hasFieldName()) return;
        setFieldName(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeFieldKey(final Object value) {
        if (value == null) return;
        if (hasFieldKey()) return;
        setFieldKey(new ShapeTypeSerializerString().asObject(value));
    }

    private void defaultOverride() {
        if (hasOverride()) {
            return;
        }
        getProperties().put(F_OVERRIDE, false);
    }

    private void defaultSuper() {
        getProperties().put(F_SUPER, false);
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(FieldConfig.class.getSimpleName());
    }


    private void defaultStaticName() {
        getProperties().put(F_SUPER, true);
    }

    private void defaultGenerated() {
        setGenerated(false);
    }


    private void defaultUnique() {
        setUnique(false);
    }

    private void defaultFieldKey() {
        if (hasFieldKey()) return;
        if (!hasNaturalId()) throw new EoException("Field with neither fieldKey nor naturalId is set");
        setFieldKey(getNaturalId());
    }

    private void defaultNotNull() {
        setNotNull(false);
    }


    private void defaultTransient() {
        setTransient(false);
    }

    private void defaultDefault() {
        setDefault(false);
    }

    private void defaultFinal() {
        if (hasFinal()) {
            return;
        }
        getProperties().put(F_FINAL, false);
    }

    public FieldBean setDefault(Boolean value) {
        getProperties().put(F_DEFAULT, value);
        return this;
    }

    private void mergeDefault(final Object value) {
        if (value == null) return;
        if (hasDefault()) return;
        setDefault(new ShapeTypeSerializerBoolean().asObject(value));
    }

    public FieldBean setFieldName(String value) {
        getProperties().put(F_FIELD_NAME, value);
        return this;
    }


    public FieldBean setFinal(Boolean value) {
        getProperties().put(F_FINAL, value);
        return this;
    }


    public FieldBean setGenerated(Boolean value) {
        getProperties().put(F_GENERATED, value);
        return this;
    }


    public FieldBean setJavascriptType(String value) {
        getProperties().put(F_JAVASCRIPT_TYPE, value);
        return this;
    }

    public FieldBean setJsonIgnore(Boolean value) {
        getProperties().put(F_JSON_IGNORE, value);
        return this;
    }

    public FieldBean setMax(Integer value) {
        getProperties().put(F_MAX, value);
        return this;
    }


    public FieldBean setMin(Integer value) {
        getProperties().put(F_MIN, value);
        return this;
    }


    public FieldBean setNotNull(Boolean value) {
        getProperties().put(F_NOT_NULL, value);
        return this;
    }


    public FieldBean setOverride(Boolean value) {
        getProperties().put(F_OVERRIDE, value);
        return this;
    }

    public FieldBean setProperty(Boolean value) {
        getProperties().put(F_PROPERTY, value);
        return this;
    }


    public FieldBean setStaticName(Boolean value) {
        getProperties().put(F_STATIC_NAME, value);
        return this;
    }


    public FieldBean setSuper(Boolean value) {
        getProperties().put(F_SUPER, value);
        return this;
    }


    public FieldBean setTransient(Boolean value) {
        getProperties().put(F_TRANSIENT, value);
        return this;
    }


    public FieldBean setUnique(Boolean value) {
        getProperties().put(F_UNIQUE, value);
        return this;
    }


    private Object getFieldValue(final String key) {
        return getProperties().get(key);
    }

    /* no null */

    public void setOverride(String value) {
        getProperties().put(F_OVERRIDE, "true".equals(value));
    }


    public void setMax() {
        if (hasMax()) {
            return;
        }
        getProperties().put(F_MAX, -1);
    }

}

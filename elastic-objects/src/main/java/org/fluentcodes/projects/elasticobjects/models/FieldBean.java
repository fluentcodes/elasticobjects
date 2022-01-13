package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

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
public class FieldBean extends ConfigBean implements FieldInterface {
    /* fieldKey */
    private String fieldKey;
    /* A string representation for a list of modelsConfig. */
    private String modelKeys;
    private boolean merged = false;

    private ModelInterface parentModel;
    private FieldBeanProperties properties;

    public FieldBean() {
        super();
        this.properties = new FieldBeanProperties();
    }

    public FieldBean(String key) {
        super(key);
        setFieldKey(key.replaceAll(".*\\.", ""));
        defaultNaturalId();
        this.properties = new FieldBeanProperties();
    }

    public FieldBean(final Map valueMap) {
        super(valueMap);
        setFieldKey(
                toString(valueMap.get(F_FIELD_KEY)));
        setModelKeys(
                toString(valueMap.get(F_MODEL_KEYS)));

        if (valueMap.containsKey(F_PROPERTIES) && valueMap.get(F_PROPERTIES)!=null) {
            if (!(valueMap.get(F_PROPERTIES) instanceof Map)) {
                throw new EoInternalException("Problem properties not instance of map but " +valueMap.get(F_PROPERTIES).getClass().getSimpleName());
            }
            this.properties = new FieldBeanProperties(((Map<String, Object>)valueMap.get(F_PROPERTIES)));
        }
        else {
            this.properties = new FieldBeanProperties(valueMap);
        }

        defaultNaturalId();
    }

    public FieldBean(final FieldConfig config) {
        super(config);
        setFieldKey(config.getFieldKey());
        setModelKeys(config.getModelKeys());
        this.properties = new FieldBeanProperties(config.getProperties());
    }

    public FieldBean(final String key, final Map values) {
        this(values);
        if (!hasNaturalId()) setNaturalId(key);
        this.properties = new FieldBeanProperties();
    }

    public FieldBean(final Field field) {
        Class<?> modelClass = field.getDeclaringClass();
        Class<?> typeClass = field.getType();
        setFieldKey(field.getName());
        setNaturalId(modelClass.getSimpleName() + "." + field.getName());
        setModelKeys(typeClass.getSimpleName());
        this.properties = new FieldBeanProperties();
    }

    protected FieldBean(final FieldBean fieldBean) {
        super();
        this.properties = new FieldBeanProperties();
        this.merge(fieldBean);
    }

    void merge(final FieldBean fieldBean) {
        super.merge(fieldBean);
        mergeFieldKey(fieldBean.getFieldKey());
        mergeModelKeys(fieldBean.getModelKeys());
        properties.merge(fieldBean.properties);
        setDefault();
        this.merged = true;
    }

    public FieldBeanProperties getProperties() {
        return properties;
    }
    public void setProperties(FieldBeanProperties properties) {
        this.properties = properties;
    }
    public void setDefault() {
        defaultConfigModelKey();
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(FieldConfigObject.class.getSimpleName());
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
        if (!hasFieldKey()) return "";
        builder.append(getFieldKey());
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

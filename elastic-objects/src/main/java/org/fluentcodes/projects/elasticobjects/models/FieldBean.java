package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/*=>{javaHeader}|*/
/**
 * 
 * The basic bean container class for the configuration class {@link FieldConfig}. Also used as a base for building source code.
 * @author Werner Diwischek
 * @creationDate Wed Dec 09 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 04:43:19 CET 2021
 */
public class FieldBean extends ConfigBean implements FieldBeanInterface  {
/*=>{}.*/

/*=>{javaInstanceVars}|*/
   /* fieldKey */
   private String fieldKey;
   /* Length of a field. */
   private Integer length;
   /* A string representation for a list of modelsConfig. */
   private String modelKeys;
/*=>{}.*/
    private boolean merged = false;

    private ModelConfigInterface parentModel;

    public FieldBean() {
        super();
    }
    public FieldBean(String key) {
        super(key);
        //this.fieldKey = key;
    }

    public FieldBean(final Map values) {
        super();
        merge(values);
    }

    public FieldBean(final String key, final Map values) {
        this(values);
        if (!hasNaturalId())  setNaturalId(key);
    }

    public FieldBean(final Field field) {
        Class modelClass = field.getDeclaringClass();
        Class typeClass = field.getType();
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

    protected FieldBean(final FieldBeanInterface fieldBean) {
        super();
        this.merge((FieldBean) fieldBean);
        setSuper(true);
    }
    public FieldBean get() {
        return this;
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
        setNaturalId((String) configMap.get(NATURAL_ID));
        setFieldKey((String) configMap.get(FIELD_KEY));
        if (!hasFieldKey()) defaultFieldKey();
        mergeFinal(configMap.get(FINAL));
        mergeOverride(configMap.get(OVERRIDE));
        mergeJsonIgnore(configMap.get(JSON_IGNORE));
        mergeTransient(configMap.get(TRANSIENT));
        mergeDefault(configMap.get(DEFAULT));

        setLength(ScalarConverter.toInt(configMap.get(LENGTH)));
        setModelKeys((String) configMap.get(MODEL_KEYS));
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

    public void defaultValues() {
        // default values for templates
        defaultOverride();
        defaultNotNull();
        defaultGenerated();
        defaultFinal();
        defaultTransient();
        defaultUnique();
        defaultSuper();
    }

    public List<String> getModelList() {
        if (!hasModelKeys()) {
            return new ArrayList<>();
        }
        return Arrays.asList(modelKeys.split(","));
    }
/*=>{javaAccessors}|*/
   @Override
   public String getFieldKey() {
      return this.fieldKey;
   }

   @Override
   public FieldBean setFieldKey(final String fieldKey) {
      this.fieldKey = fieldKey;
      return this;
    }

   @Override
   public Integer getLength() {
      return this.length;
   }

   @Override
   public FieldBean setLength(final Integer length) {
      this.length = length;
      return this;
    }

   @Override
   public String getModelKeys() {
      return this.modelKeys;
   }

   @Override
   public FieldBean setModelKeys(final String modelKeys) {
      this.modelKeys = modelKeys;
      return this;
    }

/*=>{}.*/
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
        return hasModelKeys()?
                "(" + modelKeys + ")" + builder.toString():
                builder.toString();
    }

    @Override
    public ModelConfigInterface getParentModel() {
        return parentModel;
    }

    @Override
    public void setParentModel(ModelConfigInterface modelBean) {
        this.parentModel = modelBean;
    }
}

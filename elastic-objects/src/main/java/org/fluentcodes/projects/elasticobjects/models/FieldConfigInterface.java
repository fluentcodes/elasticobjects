package org.fluentcodes.projects.elasticobjects.models;

/*=>{javaHeader}|*/
/**
 * 
 * Access methods for field properties map and get method definitions for final fields.  
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 06:06:25 CET 2021
 */
public interface FieldConfigInterface extends ConfigConfigInterface  {
/*=>{}.*/
    String DEFAULT_VALUE = "defaultValue";
    String JOIN_INVERSE = "joinInverse";
    String JOIN = "join";
    String HIBERNATE = "hibernate";
    String MAP_KEY = "mapKey";

/*=>{javaStaticNames}|*/
   String DEFAULT = "default";
   String FIELD_KEY = "fieldKey";
   String FIELD_NAME = "fieldName";
   String FINAL = "final";
   String GENERATED = "generated";
   String JAVASCRIPT_TYPE = "javascriptType";
   String JSON_IGNORE = "jsonIgnore";
   String LENGTH = "length";
   String MAX = "max";
   String MIN = "min";
   String MODEL_KEYS = "modelKeys";
   String NOT_NULL = "notNull";
   String OVERRIDE = "override";
   String PROPERTY = "property";
   String STATIC_NAME = "staticName";
   String SUPER = "super";
   String TRANSIENT = "transient";
   String UNIQUE = "unique";
/*=>{}.*/

    /*=>{javaAccessors}|*/
   default Boolean getDefault(){
      return (Boolean) getProperties().get(DEFAULT);
   }
   default boolean hasDefault() {
      return getProperties().containsKey(DEFAULT) && getProperties().get(DEFAULT) != null;
   }

   default boolean isDefault() {
      return hasDefault() && getDefault();
   }

   String getFieldKey();
   default String getKey() {
      if (hasNaturalId()) return getNaturalId();
      if (hasFieldKey()) return getFieldKey();
      return "";
   }
   default boolean hasKey() {
      return !getKey().isEmpty();
   }

   default boolean hasFieldKey() {
      return getFieldKey() != null && !getFieldKey().isEmpty();
   }

   default String getFieldName(){
      return (String) getProperties().get(FIELD_NAME);
   }
   default boolean hasFieldName() {
      return getProperties().containsKey(FIELD_NAME) && getProperties().get(FIELD_NAME) != null;
   }

   default Boolean getFinal(){
      return (Boolean) getProperties().get(FINAL);
   }
   default boolean hasFinal() {
      return getProperties().containsKey(FINAL) && getProperties().get(FINAL) != null;
   }

   default boolean isFinal() {
      return hasFinal() && getFinal();
   }

   default Boolean getGenerated(){
      return (Boolean) getProperties().get(GENERATED);
   }
   default boolean hasGenerated() {
      return getProperties().containsKey(GENERATED) && getProperties().get(GENERATED) != null;
   }

   default boolean isGenerated() {
      return hasGenerated() && getGenerated();
   }

   default String getJavascriptType(){
      return (String) getProperties().get(JAVASCRIPT_TYPE);
   }
   default boolean hasJavascriptType() {
      return getProperties().containsKey(JAVASCRIPT_TYPE) && getProperties().get(JAVASCRIPT_TYPE) != null;
   }

   default Boolean getJsonIgnore(){
      return (Boolean) getProperties().get(JSON_IGNORE);
   }
   default boolean hasJsonIgnore() {
      return getProperties().containsKey(JSON_IGNORE) && getProperties().get(JSON_IGNORE) != null;
   }

   default boolean isJsonIgnore() {
      return hasJsonIgnore() && getJsonIgnore();
   }

   Integer getLength();

   default boolean hasLength() {
      return getLength() != null;
   }

   default Integer getMax(){
      return (Integer) getProperties().get(MAX);
   }
   default boolean hasMax() {
      return getProperties().containsKey(MAX) && getProperties().get(MAX) != null;
   }

   default Integer getMin(){
      return (Integer) getProperties().get(MIN);
   }
   default boolean hasMin() {
      return getProperties().containsKey(MIN) && getProperties().get(MIN) != null;
   }

   String getModelKeys();

   default boolean hasModelKeys() {
      return getModelKeys() != null && !getModelKeys().isEmpty();
   }

   default Boolean getNotNull(){
      return (Boolean) getProperties().get(NOT_NULL);
   }
   default boolean hasNotNull() {
      return getProperties().containsKey(NOT_NULL) && getProperties().get(NOT_NULL) != null;
   }

   default boolean isNotNull() {
      return hasNotNull() && getNotNull();
   }

   default Boolean getOverride(){
      return (Boolean) getProperties().get(OVERRIDE);
   }
   default boolean hasOverride() {
      return getProperties().containsKey(OVERRIDE) && getProperties().get(OVERRIDE) != null;
   }

   default boolean isOverride() {
      return hasOverride() && getOverride();
   }

   default Boolean getProperty(){
      return (Boolean) getProperties().get(PROPERTY);
   }
   default boolean hasProperty() {
      return getProperties().containsKey(PROPERTY) && getProperties().get(PROPERTY) != null;
   }

   default boolean isProperty() {
      return hasProperty() && getProperty();
   }

   default Boolean getStaticName(){
      return (Boolean) getProperties().get(STATIC_NAME);
   }
   default boolean hasStaticName() {
      return getProperties().containsKey(STATIC_NAME) && getProperties().get(STATIC_NAME) != null;
   }

   default boolean isStaticName() {
      return hasStaticName() && getStaticName();
   }

   default Boolean getSuper(){
      return (Boolean) getProperties().get(SUPER);
   }
   default boolean hasSuper() {
      return getProperties().containsKey(SUPER) && getProperties().get(SUPER) != null;
   }

   default boolean isSuper() {
      return hasSuper() && getSuper();
   }

   default Boolean getTransient(){
      return (Boolean) getProperties().get(TRANSIENT);
   }
   default boolean hasTransient() {
      return getProperties().containsKey(TRANSIENT) && getProperties().get(TRANSIENT) != null;
   }

   default boolean isTransient() {
      return hasTransient() && getTransient();
   }

   default Boolean getUnique(){
      return (Boolean) getProperties().get(UNIQUE);
   }
   default boolean hasUnique() {
      return getProperties().containsKey(UNIQUE) && getProperties().get(UNIQUE) != null;
   }

   default boolean isUnique() {
      return hasUnique() && getUnique();
   }

/*=>{}.*/
    default Object getDefaultValue() {
        return null;
    }
    default boolean hasDefaultValue() {
        return getDefaultValue()!=null;
    }

    default boolean hasSize() {
        return hasMax()||hasMin();
    }


   ModelConfigInterface getParentModel();
   default boolean hasParentModel() {
      return getParentModel()!=null;
   }
   default boolean hasParentModelKey() {
      return hasParentModel() && getParentModel().hasModelKey();
   }

    default String getModelKey() {
       if (!hasParentModelKey()) return "";
       if (this.hasParentModelKey()) return getParentModel().getModelKey();
       if (hasParentModelNaturalId()) return getParentModel().getNaturalId();
       return "";
    }

   default boolean hasParentModelNaturalId() {
      return hasParentModel() && getParentModel().hasNaturalId() ;
   }
}



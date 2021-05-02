package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.JavascriptFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/*=>{javaHeader}|*/
/**
 * 
 * An interface for {@link FieldBean} with access methods for properties.  
 * @author Werner Diwischek
 * @creationDate Fri Nov 06 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 06:47:24 CET 2021
 */
public interface FieldBeanInterface extends FieldConfigInterface, ConfigBeanInterface  {
/*=>{}.*/
    boolean isMerged();
    /*=>{javaAccessors}|*/
   default FieldBeanInterface setDefault(Boolean value) {
      getProperties().put(DEFAULT, value);
      return this;
   }
   default void mergeDefault(final Object value) {
            if (value == null) return;
            if (hasDefault()) return;
            setDefault(ScalarConverter.toBoolean(value));
   }

   FieldBeanInterface setFieldKey(final String fieldKey);
   default void mergeFieldKey(final Object value) {
            if (value == null) return;
            if (hasFieldKey()) return;
            setFieldKey(ScalarConverter.toString(value));
   }

   default FieldBeanInterface setFieldName(String value) {
      getProperties().put(FIELD_NAME, value);
      return this;
   }
   default void mergeFieldName(final Object value) {
            if (value == null) return;
            if (hasFieldName()) return;
            setFieldName(ScalarConverter.toString(value));
   }

   default FieldBeanInterface setFinal(Boolean value) {
      getProperties().put(FINAL, value);
      return this;
   }
   default void mergeFinal(final Object value) {
            if (value == null) return;
            if (hasFinal()) return;
            setFinal(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setGenerated(Boolean value) {
      getProperties().put(GENERATED, value);
      return this;
   }
   default void mergeGenerated(final Object value) {
            if (value == null) return;
            if (hasGenerated()) return;
            setGenerated(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setJavascriptType(String value) {
      getProperties().put(JAVASCRIPT_TYPE, value);
      return this;
   }
   default void mergeJavascriptType(final Object value) {
            if (value == null) return;
            if (hasJavascriptType()) return;
            setJavascriptType(ScalarConverter.toString(value));
   }

   default FieldBeanInterface setJsonIgnore(Boolean value) {
      getProperties().put(JSON_IGNORE, value);
      return this;
   }
   default void mergeJsonIgnore(final Object value) {
            if (value == null) return;
            if (hasJsonIgnore()) return;
            setJsonIgnore(ScalarConverter.toBoolean(value));
   }

   FieldBeanInterface setLength(final Integer length);
   default void mergeLength(final Object value) {
            if (value == null) return;
            if (hasLength()) return;
            setLength(ScalarConverter.toInteger(value));
   }

   default FieldBeanInterface setMax(Integer value) {
      getProperties().put(MAX, value);
      return this;
   }
   default void mergeMax(final Object value) {
            if (value == null) return;
            if (hasMax()) return;
            setMax(ScalarConverter.toInteger(value));
   }

   default FieldBeanInterface setMin(Integer value) {
      getProperties().put(MIN, value);
      return this;
   }
   default void mergeMin(final Object value) {
            if (value == null) return;
            if (hasMin()) return;
            setMin(ScalarConverter.toInteger(value));
   }

   FieldBeanInterface setModelKeys(final String modelKeys);
   default void mergeModelKeys(final Object value) {
            if (value == null) return;
            if (hasModelKeys()) return;
            setModelKeys(ScalarConverter.toString(value));
   }

   default FieldBeanInterface setNotNull(Boolean value) {
      getProperties().put(NOT_NULL, value);
      return this;
   }
   default void mergeNotNull(final Object value) {
            if (value == null) return;
            if (hasNotNull()) return;
            setNotNull(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setOverride(Boolean value) {
      getProperties().put(OVERRIDE, value);
      return this;
   }
   default void mergeOverride(final Object value) {
            if (value == null) return;
            if (hasOverride()) return;
            setOverride(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setProperty(Boolean value) {
      getProperties().put(PROPERTY, value);
      return this;
   }
   default void mergeProperty(final Object value) {
            if (value == null) return;
            if (hasProperty()) return;
            setProperty(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setStaticName(Boolean value) {
      getProperties().put(STATIC_NAME, value);
      return this;
   }
   default void mergeStaticName(final Object value) {
            if (value == null) return;
            if (hasStaticName()) return;
            setStaticName(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setSuper(Boolean value) {
      getProperties().put(SUPER, value);
      return this;
   }
   default void mergeSuper(final Object value) {
            if (value == null) return;
            if (hasSuper()) return;
            setSuper(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setTransient(Boolean value) {
      getProperties().put(TRANSIENT, value);
      return this;
   }
   default void mergeTransient(final Object value) {
            if (value == null) return;
            if (hasTransient()) return;
            setTransient(ScalarConverter.toBoolean(value));
   }

   default FieldBeanInterface setUnique(Boolean value) {
      getProperties().put(UNIQUE, value);
      return this;
   }
   default void mergeUnique(final Object value) {
       if (value == null) return;
       if (hasUnique()) return;
       setUnique(ScalarConverter.toBoolean(value));
   }

/*=>{}.*/
    default void defaultFieldKey() {
        if (hasFieldKey()) return;
        if (!hasNaturalId()) throw new EoException("Field with neither fieldKey nor naturalId is set");
        setFieldKey(getNaturalId());
    }

    default Models getModels(EO eo) {
        return new Models(eo.getConfigsCache(), getModelKeys());
    }

    default boolean hasField(String key) {
        return getFieldValue(key)!=null;
    }

    default Object getFieldValue(final String key) {
        return getProperties().get(key);
    }

    /* no null */

    default void setOverride (String value) {
        getProperties().put(OVERRIDE, "true".equals(value) );
    }

    default void defaultOverride() {
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, false );
    }

     default void defaultSuper() {
        getProperties().put(SUPER, false );
    }


    default void defaultStaticName() {
        getProperties().put(SUPER, true );
    }

    default void defaultGenerated() {
        setGenerated(false);
    }


    default void defaultUnique() {
        setUnique(false);
    }


    default void defaultNotNull() {
        setNotNull(false);
    }


    default void defaultTransient() {
        setTransient(false);
    }

    default void defaultDefault() {
        setDefault(false);
    }

    default void defaultFinal() {
        if (hasFinal()) {
            return;
        }
        getProperties().put(FINAL, false );
    }

    default void setMax () {
        if (hasMax()) {
            return;
        }
        getProperties().put(MAX, -1 );
    }


    default void setJavascriptType() {
        getProperties().put(JAVASCRIPT_TYPE, JavascriptFieldTypeCall.createType(getModelKeys()));
    }

    void setParentModel(ModelConfigInterface modelBean);


}

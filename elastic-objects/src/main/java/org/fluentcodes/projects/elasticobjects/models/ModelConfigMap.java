package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigMap extends ModelConfig implements ModelConfigInterface {
    public static final String CONFIG_MODEL_KEY = "ModelConfigMap";

    public ModelConfigMap(EOConfigsCache provider, Map map) {
        super(provider, map);
    }

    @Override
    public ModelConfigInterface getFieldModel(final String fieldName)  {
        return getConfigsCache().findModel(Object.class); //TODO
    }


    @Override
    public FieldConfig getFieldConfig(final String fieldName) {
        return null; //TODO
    }

    @Override
    public Set<String> keys(Object object)  {
        resolve();
        return ((Map) object).keySet();
    }

    public int size(Object object)  {
        resolve();
        int counter = 0;
        for (Object key : ((Map) object).keySet()) {
            if (((Map) object).get(key) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public List<Object> keysAsIs(Object object)  {
        resolve();
        return new ArrayList(((Map) object).keySet());
    }

    public void set(String fieldName, Object object, Object value)  {
        resolve();
        ((Map) object).put(fieldName, value);
    }

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldNameAsObject
     * @param object
     * @return
     * @
     */
    public Object getAsIs(Object fieldNameAsObject, Object object)  {
        resolve();
        if (fieldNameAsObject == null) {
            throw new EoException("Getter: null key request for " + this.getModelKey() + "! ");
        }
        return ((Map) object).get(fieldNameAsObject);
    }

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldName
     * @param object
     * @return
     * @
     */
    public Object get(String fieldName, Object object)  {
        resolve();
        if (((Map) object).containsKey(fieldName)) {
            return ((Map) object).get(fieldName);
        } else if (fieldName.matches("^\\d+$")) {
            Integer i = Integer.parseInt(fieldName);
            if (((Map) object).containsKey(i)) {
                return ((Map) object).get(i);
            }
        }
        throw new EoException("No value add for fieldName=" + fieldName);

    }

    public boolean exists(final String fieldName, final Object object)  {
        resolve();
        return ((Map) object).containsKey(fieldName);

    }

    @Override
    public boolean hasKey(final String fieldName) {
        return true;
    }

    public void remove(final String fieldName, final Object object)  {
        resolve();
        get(fieldName, object);
        ((Map) object).remove(fieldName);
    }

    public Object create() {
        resolve();
        return new LinkedHashMap<>();
    }
    @Override
    public boolean hasSetter(final String fieldName) {
        return true;
    }
    @Override
    public boolean hasGetter(final String fieldName) {
        return true;
    }
    @Override
    public boolean isCreate() {
        return true;
    }
    @Override
    public boolean isMap() {
        return true;
    }
    @Override
    public boolean isMapType() {
        return true;
    }



}

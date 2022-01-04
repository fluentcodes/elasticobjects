package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigMap extends ModelConfig {
    public static final String CONFIG_MODEL_KEY = "ModelConfigMap";

    public ModelConfigMap(ConfigBean bean, final ConfigMaps configMaps) {
        this((ModelBean) bean, configMaps);
    }

    public ModelConfigMap(ModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }

    @Override
    public FieldConfig getField(final String fieldName) {
        return null; //TODO
    }

    @Override
    public Set<String> keys(Object object)  {
        if (object == null) {
            return new HashSet<>();
        }
        if (!(object instanceof Map)) {
            throw new EoException("Map expected but '" + object.getClass().getSimpleName() + "'");
        }
        return ((Map) object).keySet();
    }

    public int size(Object object)  {
        int counter = 0;
        for (Object key : ((Map) object).keySet()) {
            if (((Map) object).get(key) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public boolean set(String fieldName, Object object, Object value)  {
        ((Map) object).put(fieldName, value);
        return true;
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
        return ((Map) object).containsKey(fieldName);
    }

    @Override
    public boolean isEmpty(final Object object)  {
        return object == null || ((Map) object).isEmpty();
    }

    public void remove(final String fieldName, final Object object)  {
        get(fieldName, object);
        ((Map) object).remove(fieldName);
    }

    public Object create() {
        return new LinkedHashMap<>();
    }

}

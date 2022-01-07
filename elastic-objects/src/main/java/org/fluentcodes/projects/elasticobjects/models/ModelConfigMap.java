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
    public FieldConfig getField(final String fieldKey) {
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

    public boolean set(String fieldKey, Object object, Object value)  {
        ((Map) object).put(fieldKey, value);
        return true;
    }

    /**
     * Gets the value for fieldKey of the object.
     *
     * @param fieldKey
     * @param parent
     * @return
     * @
     */
    public Object get(String fieldKey, Object parent)  {
        return ((Map) parent).get(fieldKey);
    }

    @Override
    public boolean hasValue(String fieldKey, Object parent) {
        return get(fieldKey, parent) != null;
    }

    public boolean exists(final String fieldKey, final Object object)  {
        return ((Map) object).containsKey(fieldKey);
    }

    @Override
    public boolean isEmpty(final Object object)  {
        return object == null || ((Map) object).isEmpty();
    }

    public void remove(final String fieldKey, final Object object)  {
        get(fieldKey, object);
        ((Map) object).remove(fieldKey);
    }

    public Object create() {
        return new LinkedHashMap<>();
    }

}

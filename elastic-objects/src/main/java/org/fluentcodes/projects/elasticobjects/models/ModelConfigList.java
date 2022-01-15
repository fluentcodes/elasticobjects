package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigList extends ModelConfig {
    private static final Logger LOG = LogManager.getLogger(Config.class);

    public ModelConfigList(ConfigBean bean, final ConfigMaps configMaps) {
        this((ModelBean) bean, configMaps);
    }

    public ModelConfigList(ModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }

    @Override
    public FieldConfig getField(final String fieldKey) {
        return null; //TODO
    }

    public Set<String> keys(Object object)  {
        int size = -1;
        if (object instanceof List) {
            size = ((List) object).size();
        }
        Set<String> fields = new LinkedHashSet<>();
        for (int i = 0; i < size; i++) {
            fields.add(new Integer(i).toString());
        }
        return fields;
    }

    @Override
    public int size(final Object object)  {
        int counter = 0;
        int size = -1;
        if (object instanceof List) {
            size = ((List) object).size();
        }
        for (int i = 0; i < size; i++) {
            if (((List) object).get(i) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    @Override
    public boolean set(final String fieldName, final Object object, final Object value)  {
        if (object == null) {
            throw new EoInternalException("List object for " + fieldName + " is null!");
        }
        Integer i = null;
        if (fieldName == null) {
            i = ((List) object).size();
        }
        else {
            try {
                i = Integer.parseInt(fieldName);
            } catch (NumberFormatException e) {
                throw new EoException("Could not parse for integer " + fieldName + ": ", e);
            }
        }
        if (i == ((List) object).size()) {
            ((List) object).add(value);
        } else if (i < ((List) object).size()) {
            ((List) object).set(i, value);
        } else {
            for (int j = ((List) object).size(); j < i; j++) {
                ((List) object).add(null);
            }
            ((List) object).add(value);
        }
        return true;
    }

    @Override
    public boolean hasValue(String fieldKey, Object parent) {
        return get(fieldKey, parent) != null;
    }

    @Override
    public Object get(final String fieldKey, final Object parent)  {
        if (parent == null) {
            return null;
        }
        List list = (List) parent;
        if (list.isEmpty()) {
            return null;
        }
        try {
            int i = Integer.parseInt(fieldKey);
            if (i + 1 > list.size()) {
                return null;
            }
            return list.get(i);
        } catch (Exception e) {
            throw new EoException("Could not parse fieldKey '" + fieldKey + "' for list integer: " + e.getMessage());
        }
    }

    @Override
    public boolean exists(final String fieldName, final Object object)  {
        //TODO
        Integer i = Integer.parseInt(fieldName);
        return i <= ((List) object).size() - 1;
    }

    @Override
    public boolean isEmpty(final Object object)  {
        return object == null || ((List) object).isEmpty();
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
        Integer i = Integer.parseInt(fieldName);
        List list = ((List) object);
        if (((List) object).size() <= i) {
            throw new EoException("List size " + list.size() + " greater than " + i);
        } else if (i + 1 == list.size()) {
            ((List) object).remove(i.intValue());
            return;
        }
        if (((List) object).get(i) == null) {
            throw new EoException("List value for " + i + " is already null.");
        }
        list.set(i.intValue(), null);
        LOG.info("Size of list: " + list.size());

    }

    @Override
    public Object create()  {
        return new ArrayList();
    }

}

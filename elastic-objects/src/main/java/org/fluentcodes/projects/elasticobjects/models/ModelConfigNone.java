package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigNone extends ModelConfig {
    private static final Logger LOG = LogManager.getLogger(ConfigImpl.class);

    public ModelConfigNone(EOConfigsCache provider, Builder bean) {
        super(provider, bean);
    }

    @Override
    public String getTable() {
        return null;
    }

    @Override
    public ModelInterface getFieldModel(final String fieldName)  {
        throw new EoException("No field defined for scalar models: " + fieldName);
    }

    @Override
    public FieldConfig getFieldConfig(final String fieldName)  {
        throw new EoException("No field defined for scalar models: " + fieldName);
    }


    @Override
    public Class getFieldClass(String fieldName)  {
        throw new EoException("No field defined for scalar models: " + fieldName);
    }

    public Set<String> keys(Object object)  {
        throw new EoException("No field defined for scalar models!" + object.getClass().getSimpleName());
    }

    public List keys(final Object object, final String filter)  {
        throw new EoException("No field defined for scalar models!" + object.getClass().getSimpleName());
    }

    @Override
    public int size(final Object object)  {
        throw new EoException("No field defined for scalar models!" + object.getClass().getSimpleName());
    }


    @Override
    public boolean isEmpty(final Object object)  {
        throw new EoException("No field defined for scalar models!" + object.getClass().getSimpleName());
    }

    @Override
    public void set(final String fieldName, final Object object, final Object value)  {
        throw new EoException("No field defined for scalar models: " + fieldName);
    }

    @Override
    public Object getAsIs(final Object fieldNameAsObject, final Object object)  {
        throw new EoException("No field defined for scalar models!" + object.getClass().getSimpleName());
    }

    @Override
    public Object get(final String fieldName, final Object object)  {
        throw new EoException("No field defined for scalar models: " + fieldName);
    }

    @Override
    public boolean exists(final String fieldName, final Object object)  {
        throw new EoException("No field defined for scalar models: " + fieldName);
    }

    @Override
    public boolean hasKey(final String fieldName) {
        return false;
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
        throw new EoException("No field defined for scalar models: " + fieldName);
    }

    @Override
    public Object create() {
        return null;
    }

    @Override
    public boolean hasModel() {
        return false;
    }
    @Override
    public boolean isScalar() {
        return true;
    }

    @Override
    public boolean isNull() {
        return true;
    }


}
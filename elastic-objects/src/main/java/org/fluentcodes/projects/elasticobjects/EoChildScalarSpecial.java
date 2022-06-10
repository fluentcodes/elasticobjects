package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Models;

public class EoChildScalarSpecial extends EoChildScalar {
    private Object value;
    public EoChildScalarSpecial(final EO parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        super (parentEo, fieldKey, value, fieldModels);
    }

    /**
     * Will not set the parents object value
     * @param value the value to be set
     */
    @Override
    public void set(final Object value) {
        if (getFieldKey().equals(PathElement.V_MODEL)) {
            if (!getParent().isEmpty()) {
                throw new EoException("Could not change model when values are already set");
            }
            if (!(value instanceof String)) {
                throw new EoException("Could set model only with String values");
            }
            String models = (String)value;
            ((EoChild)getParent()).setModels(models.isEmpty()?"Map":models);
        }
        this.value = getModels().asObject(value);
    }

    @Override
    public Object get() {
        return value;
    }
}

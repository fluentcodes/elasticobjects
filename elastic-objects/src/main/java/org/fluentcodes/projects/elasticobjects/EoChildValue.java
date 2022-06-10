package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.Models;

public class EoChildValue extends EoChildScalar {
    private Object fieldValue;

    public EoChildValue(final EO parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        super(parentEo, fieldKey, value, fieldModels);
    }

    @Override
    public void set(final Object value) {
        if (getParentEo().hasEo(getFieldKey())) {
            setChanged();
        }
        this.fieldValue = value;
        ((EoChild)getParent()).addChildEo(this);
    }

    @Override
    public Object get() {
        return fieldValue;
    }
}

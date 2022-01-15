package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.Models;

public class EoChildSpecial extends EoChild {
    public EoChildSpecial(final EO parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        super(parentEo, fieldKey, value, fieldModels);
    }

    @Override
    public void set(Object value) {
        if (get() == null) {
            setFieldValue(getModels().create());
        }
        map(value);
    }
}

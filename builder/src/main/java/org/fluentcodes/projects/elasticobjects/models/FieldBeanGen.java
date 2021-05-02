package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

public class FieldBeanGen extends FieldBean implements
        FieldBeanInterface4Java,
        FieldBeanInterface4Javascript {

    public FieldBeanGen() {
        super();
    }
    public FieldBeanGen(final String key) {
        super(key);
    }

    public FieldBeanGen(final FieldConfig config) {
        super(config);
    }

    public FieldBeanGen(final Map values) {
        super(values);
    }
    public FieldBeanGen(final String key, final Map values) {
        super(key, values);
    }

    protected FieldBeanGen(final FieldBean fieldBean) {
        super();
        merge(fieldBean);
        setSuper(true);
    }

    public void merge(final FieldBean fieldBean) {
        super.merge(fieldBean);
    }
}

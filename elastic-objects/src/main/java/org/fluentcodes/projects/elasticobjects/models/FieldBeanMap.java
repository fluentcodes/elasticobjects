package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

/**
 * Created by Werner on 21.1.2021.
 */

public class FieldBeanMap extends ConfigBeanMap<FieldBean> implements FieldBeanMapInterface {
    public FieldBeanMap(final Scope scope)  {
        super(scope, FieldConfig.class);
    }

    @Override
    protected FieldBean createBean(final String naturalId, final Map valueMap) {
        return new FieldBean(naturalId, valueMap);
    }
}

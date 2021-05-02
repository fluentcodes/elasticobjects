package org.fluentcodes.projects.elasticobjects.models;

public class FieldBeanMap4Sheet extends ConfigBeanMap4Sheet<FieldBeanGen> implements FieldBeanMapInterface {
    protected FieldBeanMap4Sheet()  {
        super(Scope.TEST, FieldBeanGen.class, FieldConfig.class);
    }
    protected FieldBeanMap4Sheet(final Scope scope)  {
        super(scope, FieldBeanGen.class, FieldConfig.class);
    }
    protected FieldBeanMap4Sheet(final Scope scope, final Class<? extends ConfigBean> beanClass)  {
        super(scope, beanClass, FieldConfig.class);
    }
}

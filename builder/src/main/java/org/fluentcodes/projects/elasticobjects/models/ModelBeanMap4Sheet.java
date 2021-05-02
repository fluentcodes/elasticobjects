package org.fluentcodes.projects.elasticobjects.models;

public class ModelBeanMap4Sheet extends ConfigBeanMap4Sheet<ModelBeanGen> {
    protected ModelBeanMap4Sheet()  {
        super(Scope.TEST, ModelBeanGen.class, ModelConfig.class);
    }
    public ModelBeanMap4Sheet(final Scope scope)  {
        super(scope, ModelBeanGen.class, ModelConfig.class);
    }
    public ModelBeanMap4Sheet(final Scope scope, final Class<? extends ConfigBean> beanClass)  {
        super(scope, beanClass, ModelConfig.class);
    }

    public void readSheet(final String sheetKey) {
        super.readSheet(sheetKey);
        FieldBeanMap4Sheet fieldMap = new FieldBeanMap4Sheet(this.getScope(), FieldBeanGen.class);
        fieldMap.readSheet(sheetKey);
        for (ModelBean modelBean: getBeanMap().values()) {
            modelBean.mergeFieldBeanMap(fieldMap);
        }
    }
}

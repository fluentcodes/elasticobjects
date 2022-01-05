package org.fluentcodes.projects.elasticobjects.models;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

public class FieldBeanForClasses extends FieldBean {
    private Type typeClass;

    public FieldBeanForClasses(final Field field, final ModelBean modelBean, Map<String, ModelBean> modelBeanMap) {
        setParentModel(modelBean);
        setFieldKey(field.getName());
        setNaturalId(modelBean.getModelKey() + "." + getFieldKey());
        this.typeClass = field.getType();
        setModelKeys(getTypeKey());
        setMerged(true);
    }
    protected String getTypeKey() {
        return typeClass.getTypeName().replaceAll(".*\\.", "");
    }

    public Type getTypeClass() {
        return typeClass;
    }
}

package org.fluentcodes.projects.elasticobjects.models;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ModelBeanForClasses extends ModelBean {
    private Class superClass;
    private List<Type> interfaces;

    protected ModelBeanForClasses(final Class modelClass, final List<ModelBean> modelList) {
        super();
        setNaturalId(modelClass.getSimpleName());
        setModelKey(modelClass.getSimpleName());
        setPackagePath(modelClass.getPackage().getName());
        setConfigModelKey(ModelConfigObject.class.getSimpleName());
        if (modelClass.getSuperclass() != null && modelClass.getSuperclass() != Object.class) {
            superClass = modelClass.getSuperclass();
            setSuperKey(modelClass.getSuperclass().getSimpleName());
        }
        if (modelClass.getSimpleName().endsWith("List")) {
            setShapeType(ShapeTypes.LIST);
        }
        else if (modelClass.getSimpleName().endsWith("Map")) {
            setShapeType(ShapeTypes.MAP);
        }
        else  {
            setShapeType(ShapeTypes.OBJECT);
        }

        setCreate(true);
        final Field[] fields = modelClass.getDeclaredFields();
        for (Field field : fields) {
            FieldBean fieldBean = new FieldBeanForClasses(field, this, modelList);
            getFields().put(fieldBean.getFieldKey(), fieldBean);
        }
    }

    public Class getSuperClass() {
        return superClass;
    }
    public boolean hasSuperClass() {
        return superClass != null;
    }

    public List getClassInterfaces() {
        return interfaces;
    }
    public boolean hasClassInterfaces() {
        return interfaces!=null && !interfaces.isEmpty();
    }
}

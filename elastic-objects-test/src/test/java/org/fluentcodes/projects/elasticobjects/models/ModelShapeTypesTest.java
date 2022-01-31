package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_DATE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_FLOAT;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_LIST_EMPTY;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_LONG;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_MAP_EMPTY;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_BOOLEAN;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
public class ModelShapeTypesTest {

    @Test
    public void testTypeClasses()  {
        testTypeClass(Boolean.class, ShapeTypes.BOOLEAN);
        testTypeClass(Date.class, ShapeTypes.DATE);
        testTypeClass(Long.class, ShapeTypes.LONG);
        testTypeClass(Integer.class, ShapeTypes.INTEGER);
        testTypeClass(Double.class, ShapeTypes.DOUBLE);
        testTypeClass(Float.class, ShapeTypes.FLOAT);
        testTypeClass(String.class, ShapeTypes.STRING);
        testTypeClass(List.class, ShapeTypes.LIST);
        testTypeClass(Map.class, ShapeTypes.MAP);

    }

    @Test
    public void testTypeObjects()  {
        testTypeObject(S_BOOLEAN, ShapeTypes.BOOLEAN);
        testTypeObject(SAMPLE_DATE, ShapeTypes.DATE);
        testTypeObject(SAMPLE_LONG, ShapeTypes.LONG);
        testTypeObject(S_INTEGER, ShapeTypes.INTEGER);
        testTypeObject(SAMPLE_DOUBLE, ShapeTypes.DOUBLE);
        testTypeObject(SAMPLE_FLOAT, ShapeTypes.FLOAT);
        testTypeObject(S_STRING, ShapeTypes.STRING);
        testTypeObject(SAMPLE_LIST_EMPTY, ShapeTypes.LIST);
        testTypeObject(SAMPLE_MAP_EMPTY, ShapeTypes.MAP);
    }

    private void testTypeClass(Class clazz, ShapeTypes shapeType)  {
        ShapeTypes shapeTypeStored = ObjectProvider.CONFIG_MAPS.findModel(clazz).getShapeType();
        Assert.assertEquals(shapeType, shapeTypeStored);
    }

    private void testTypeObject(Object object, ShapeTypes shapeType) {
        try {
            ShapeTypes shapeTypeStored = ObjectProvider.CONFIG_MAPS.findModel(object).getShapeType();
            Assert.assertEquals(shapeType, shapeTypeStored);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
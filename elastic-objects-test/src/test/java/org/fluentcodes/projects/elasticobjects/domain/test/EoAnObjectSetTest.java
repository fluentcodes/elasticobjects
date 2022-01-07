package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_DATE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_FLOAT;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_LONG;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_BOOLEAN;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING_OTHER;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_OBJECT;

/**
 * Created by Werner on 04.11.2016.
 */
public class EoAnObjectSetTest {

    @Test
    public void fromEoConfigsCache()  {
        ModelConfig config = ObjectProvider.CONFIG_MAPS.findModel(AnObject.class);
        Assert.assertNotNull(config.getField(AnObject.MY_STRING));
        Assert.assertEquals(AnObject.MY_STRING, config.getField(AnObject.MY_STRING).getFieldKey());
        ModelConfig aSubObject = config.getFieldModelConfig(AnObject.MY_ASUB_OBJECT);
        Assert.assertEquals(ASubObject.class.getSimpleName(), aSubObject.getModelKey());
        Assert.assertEquals(AnObject.MY_STRING, aSubObject.getField(AnObject.MY_STRING).getFieldKey());
    }

    @Test
    public void TEST__find_AnObject_get_myString__$()  {
        FieldConfig fieldConfig = ObjectProvider.CONFIG_MAPS
                .findModel(AnObject.class)
                .getField(AnObject.MY_STRING);
        Assert.assertNotNull(fieldConfig);
        Assertions.assertThat(fieldConfig.getProperties().isUnique()).isFalse();
        Assertions.assertThat(fieldConfig.getProperties().isNotNull()).isFalse();
        Assert.assertEquals(new Integer(20), fieldConfig.getProperties().getLength());
    }

    @Test
    public void testMap_forWiki()  {
        final EoRoot eo = ObjectProvider.createEo();

        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        map.put(AnObject.MY_FLOAT, 1.1D);

        final IEOScalar child = eo.set( map, "(" + AnObject.class.getSimpleName() + ")level0");
        Assert.assertEquals(AnObject.class, child.getModelClass());
        assertEquals("value", child.get(AnObject.MY_STRING));
        assertEquals(1.1F, child.get(AnObject.MY_FLOAT));
        assertEquals("value", eo.get("level0", AnObject.MY_STRING));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(AnObject.class, eo.getEo("level0").getModelClass());
        assertEquals(AnObject.class, eo.get("level0").getClass());
        assertEquals(AnObject.class, child.get().getClass());
        assertEquals(Float.class, eo.getEo("level0", AnObject.MY_FLOAT).getModelClass());
    }

    @Test
    public void bean() {
        AnObject test = new AnObject();
        test.setMyInt(1);
        Assert.assertEquals(new Integer(1), test.getMyInt());
    }

    @Test
    public void givenModelFromString_notNull()  {
        ModelConfig model = ObjectProvider.findModel(AnObject.class);
        Assertions.assertThat(model).isNotNull();
    }

    @Test
    public void givenModelFromClass_createAndSetModelFieldsWith_noError()  {
        ModelConfig model = ObjectProvider.findModel(AnObject.class);
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertTrue(model.isObject());

        AnObject object = (AnObject) model.create();
        Assertions.assertThat(model).isNotNull();
        model.set(AnObject.MY_STRING, object, S_STRING);
        Assert.assertEquals(S_STRING, model.get(AnObject.MY_STRING, object));
        Assert.assertEquals(S_STRING, object.getMyString());

        model.set(AnObject.MY_DATE, object, SAMPLE_DATE);
        Assert.assertEquals(SAMPLE_DATE, model.get(AnObject.MY_DATE, object));
        Assert.assertEquals(SAMPLE_DATE, object.getMyDate());

        Map map = new HashMap();
        model.set(MY_OBJECT, object, map);
        Assert.assertEquals(map, object.getMyObject());
        Assert.assertEquals(map, model.get(MY_OBJECT, object));
        Assert.assertTrue(map == model.get(MY_OBJECT, object));
    }

    @Test
    public void assertAnObjectFieldTest()  {
        ModelConfig model = ObjectProvider.findModel(AnObject.class);

        FieldInterface field = model.getField(AnObject.MY_STRING);
        Assert.assertEquals(String.class, ((FieldConfig)field).getModelClass());

        field = model.getField(MY_OBJECT);
        Assert.assertEquals(Object.class, ((FieldConfig)field).getModelClass());

    }

    @Test
    public void getFields()  {
        ModelConfig cache = ObjectProvider.findModel(AnObject.class);
        Assert.assertEquals(AnObject.class.getSimpleName(), cache.getModelKey());
        AnObject anObject = (AnObject) cache.create();
        cache.set(AnObject.MY_STRING, anObject, S_STRING);
        Assert.assertEquals(S_STRING, cache.get(AnObject.MY_STRING, anObject));
        cache.set(AnObject.MY_INT, anObject, S_INTEGER);
        Assert.assertEquals(S_INTEGER, cache.get(AnObject.MY_INT, anObject));
        cache.set(AnObject.MY_LONG, anObject, SAMPLE_LONG);
        Assert.assertEquals(SAMPLE_LONG, cache.get(AnObject.MY_LONG, anObject));
        cache.set(AnObject.MY_FLOAT, anObject, SAMPLE_FLOAT);
        Assert.assertEquals(SAMPLE_FLOAT, cache.get(AnObject.MY_FLOAT, anObject));
        cache.set(AnObject.MY_DOUBLE, anObject, SAMPLE_DOUBLE);
        Assert.assertEquals(SAMPLE_DOUBLE, cache.get(AnObject.MY_DOUBLE, anObject));
        cache.set(AnObject.MY_DATE, anObject, SAMPLE_DATE);
        Assert.assertEquals(SAMPLE_DATE, cache.get(AnObject.MY_DATE, anObject));
        cache.set(AnObject.MY_BOOLEAN, anObject, S_BOOLEAN);
        Assert.assertEquals(S_BOOLEAN, cache.get(AnObject.MY_BOOLEAN, anObject));
    }

    @Test
    public void testAnObject_ok()  {
        final EoRoot root = ObjectProvider.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("testObject");
        root.set( anObject, "test","test2");
        Assert.assertEquals("testObject", root.get("test","test2", AnObject.MY_STRING));
        Assert.assertEquals(AnObject.class, root.getEo("test","test2").getModelClass());
    }

    @Test
    public void givenEo_setAnObjectPathTestAndTestString_thenValueAndModelIsSet()  {
        final EoRoot root = ObjectProvider.createEo();
        root.set("testObject", "(" + AnObject.class.getSimpleName() + ")test", AnObject.MY_STRING);
        Assert.assertEquals("testObject", root.get("test", AnObject.MY_STRING));
        Assert.assertEquals(AnObject.class, root.getEo("test").getModelClass());
    }

    @Test
    public void TEST_path_model_AnObject__set_myInt__class_is_AnObject()  {
        final EoRoot root = ObjectProvider.createEo();
        root.set("testObject", "(" + AnObject.class.getSimpleName() + ")test", AnObject.MY_STRING);
        root.set(1, "test", AnObject.MY_INT);
        Assert.assertEquals(1, root.get("test", AnObject.MY_INT));
        Assertions.assertThat(root.getLog()).isEmpty();
        Assert.assertEquals(AnObject.class, root.getEo("test").getModelClass());
    }
    
    @Test
    public void givenBt_whenSetStringField_ok()  {
        final EoRoot eo = ObjectProvider.createEo(new AnObject());
        eo.set(S_STRING_OTHER, AnObject.MY_STRING);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }



    @Test
    public void TEST__setEmpty_key0_key1_key2_AnObject_key__getModelClass_key0_key1_key2_key3_AnObject()  {
        final EoRoot eo = ObjectProvider.createEo();
        eo.createChild("key0", "key1", "key2", "(" + AnObject.class.getSimpleName() + ")" + "key3");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo("key0", "key1", "key2", "key3").getModelClass()).isEqualTo(AnObject.class);
    }

}

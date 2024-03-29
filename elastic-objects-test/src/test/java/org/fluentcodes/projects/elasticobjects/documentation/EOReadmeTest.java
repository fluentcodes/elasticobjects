package org.fluentcodes.projects.elasticobjects.documentation;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class EOReadmeTest {
    @Test
    public void setAndGet() {
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
        EOInterfaceScalar child = root.set("test", "myAnObject", "myString");

        assertEquals("test", root.get("myAnObject", "myString"));
        assertEquals("test", child.get());
    }

    @Test
    public void getObject() {
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
        EOInterfaceScalar child = root.set("test", "myAnObject", "myString");

        AnObject anObject = (AnObject) root.get();
        assertEquals("test", anObject.getMyAnObject().getMyString());
    }

    @Test
    public void stringPathRepresentation() {
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
        EOInterfaceScalar child = root.set("test", "myAnObject/myString");

        assertEquals("test", root.get("myAnObject/myString"));
        assertEquals("test", child.get());
    }


    @Test
    public void remove() {
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
        EOInterfaceScalar child = root.set("test", "myAnObject/myString");

        EO parent = child.remove();
        assertFalse(parent.hasEo("myString"));

        AnObject parentObject = (AnObject) parent.get();
        assertNull(parentObject.getMyString());
    }

    @Test
    public void toJson() {
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
        EOInterfaceScalar child = root.set("test", "myAnObject", "myString");

        assertEquals("{\n" +
                "  \"_rootmodel\": \"AnObject\",\n" +
                "  \"(AnObject)myAnObject\": {\n" +
                "    \"myString\": \"test\"\n" +
                "  }\n" +
                "}", root.toJson());
    }

    @Test
    public void fromJson() {
        EoRoot root = EoRoot.ofValue(CONFIG_MAPS, new AnObject());
        root.set("test", "myAnObject", "myString");
        String json = root.toJson();

        EoRoot rootFromJson = EoRoot.ofValue(CONFIG_MAPS, json);
        assertEquals(AnObject.class, rootFromJson.get().getClass());

        AnObject myAnObject = (AnObject) rootFromJson.get();
        assertEquals("test", myAnObject.getMyAnObject().getMyString());
    }

    @Test
    public void AnObject_myString_tooLong() {
        EoRoot root = EoRoot.ofClass(CONFIG_MAPS, AnObject.class);
        assertEquals(AnObject.class, root.getModelClass());
        Assertions.assertThatThrownBy(
                ()->{root.set("test01234567890123456789", "myString");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Problem creating child at '/' with key 'myString' with value 'test01234567890123456789' with message String value for field 'test01234567890123456789' has size 24 bigger than max length 20.");
    }

    @Test
    public void Map_myString_tooLong() {
        EoRoot root = EoRoot.ofClassName(CONFIG_MAPS, "AnObjectMap");
        assertEquals(LinkedHashMap.class.getSimpleName(), root.get().getClass().getSimpleName());
        Assertions.assertThatThrownBy(
                ()->{root.set("test01234567890123456789", "myString");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Problem creating child at '/' with key 'myString' with value 'test01234567890123456789' with message String value for field 'test01234567890123456789' has size 24 bigger than max length 20.");
    }

    @Test
    public void Map_notValidKey() {
        EoRoot root = EoRoot.ofClassName(CONFIG_MAPS, "AnObjectMap");
        Assertions.assertThatThrownBy(
                ()->{root.set("test", "notValid");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Problem creating child at '/' with key 'notValid' with value 'test' with message No field defined for 'notValid'.");
    }

    @Test
    public void testClone() {
        final AnObject anObject = new AnObject();
        anObject.setMyString("value2");

        final EoRoot rootMap = EoRoot.ofValue(CONFIG_MAPS, anObject);
        final AnObject cloned = (AnObject) rootMap.get();

        assertNotEquals(anObject, cloned);
        assertEquals(anObject.getMyString(), cloned.getMyString());
    }

    @Test
    public void testTransform() {
        final AnObject anObject = new AnObject();
        anObject.setMyString("value2");

        final EoRoot rootMap = EoRoot.ofClass(CONFIG_MAPS, anObject, Map.class);
        final Map transformed = (Map) rootMap.get();

        assertEquals(anObject.getMyString(), transformed.get("myString"));
    }

    @Test
    public void compare() {
        final Map map = new HashMap();
        map.put("myString", "value1");

        final AnObject anObject = new AnObject();
        anObject.setMyString("value2");

        final EoRoot rootMap = EoRoot.ofValue(CONFIG_MAPS, map);
        final EoRoot rootAnObject = EoRoot.ofValue(CONFIG_MAPS, anObject);

        assertEquals("/myString: value1 <> value2", rootMap.compare(rootAnObject));
    }

    @Test
    public void comments() {
        final String json = "{\"myString\":\"test\", \"_comment\":\"FieldNames with underscore will not set in parent object.\"}";

        final EoRoot root = EoRoot.ofClass(CONFIG_MAPS, json, AnObject.class);

        assertEquals("FieldNames with underscore will not set in parent object.", root.get("_comment"));
        assertEquals("{\n" +
                "  \"myString\": \"test\"\n" +
                "}", root.toJson(JSONSerializationType.STANDARD));

    }
}

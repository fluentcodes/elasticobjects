package org.fluentcodes.projects.elasticobjects.documentation;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;

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

        AnObject myAnObject = (AnObject)rootFromJson.get();
        assertEquals("test", myAnObject.getMyAnObject().getMyString());
    }

}

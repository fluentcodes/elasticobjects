package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeTypeSerializerEnumTest {
    @Test
    public void asStringJSONSerializationTypeEo() {
        assertEquals("EO",
                new ShapeTypeSerializerEnum().asString(JSONSerializationType.EO));
    }

    @Test
    public void asJsonJSONSerializationTypeEo() {
        assertEquals("\"EO\"",
                new ShapeTypeSerializerEnum().asJson(JSONSerializationType.EO));
    }

    @Test
    public void asObjectsJSONSerializationTypeEo() {
        assertEquals(JSONSerializationType.EO,
                new ShapeTypeSerializerEnum<JSONSerializationType>().asObject(JSONSerializationType.class, "EO"));
    }
}

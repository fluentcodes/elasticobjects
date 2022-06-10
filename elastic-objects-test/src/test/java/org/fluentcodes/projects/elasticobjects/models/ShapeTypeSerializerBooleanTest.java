package org.fluentcodes.projects.elasticobjects.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShapeTypeSerializerBooleanTest {
    @Test
    public void asStringTrue() {
        assertEquals("true",
                new ShapeTypeSerializerBoolean().asString(true));
    }

    @Test
    public void asStringFalse() {
        assertEquals("false",
                new ShapeTypeSerializerBoolean().asString(false));
    }

    @Test
    public void asStringNullFalse() {
        assertEquals("false",
                new ShapeTypeSerializerBoolean().asString(null));
    }

    @Test
    public void asObject1() {
        assertTrue(
                new ShapeTypeSerializerBoolean().asObject(1));
    }

    @Test
    public void asObjectFalse() {
        assertFalse(
                new ShapeTypeSerializerBoolean().asObject("false"));
    }


}

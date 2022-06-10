package org.fluentcodes.projects.elasticobjects.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeTypeSerializerFloatTest {
    @Test
    public void asStringFloat2() {
        assertEquals("2.0",
                new ShapeTypeSerializerFloat().asString(new Float(2.0)));
    }

    @Test
    public void asStringFloatNative2() {
        assertEquals("2.0",
                new ShapeTypeSerializerFloat().asString(new Float(2)));
    }

    @Test
    public void asStringFloat2_1() {
        assertEquals("2.1",
                new ShapeTypeSerializerFloat().asString(new Float(2.1)));
    }

    @Test
    public void asStringDouble2() {
        assertEquals("2.0",
                new ShapeTypeSerializerFloat().asString(new Float(2.0)));
    }

    @Test
    public void asStringDouble2_1() {
        assertEquals("2.1",
                new ShapeTypeSerializerFloat().asString(new Float(2.1)));
    }

}

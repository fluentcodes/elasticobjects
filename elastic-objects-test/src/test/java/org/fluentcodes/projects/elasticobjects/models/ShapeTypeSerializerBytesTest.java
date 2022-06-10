package org.fluentcodes.projects.elasticobjects.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeTypeSerializerBytesTest {
    @Test
    public void asJsonNewLine() {
        final String input = "newline:\nnextline";

        assertEquals("\"bmV3bGluZToKbmV4dGxpbmU=\"",
                new ShapeTypeSerializerBytes().asJson(input.getBytes()));
    }

    @Test
    public void asStringNewLine() {
        final String input = "newline:\nnextline";
        final String encoded = new ShapeTypeSerializerBytes().asString(input.getBytes());
        assertEquals("bmV3bGluZToKbmV4dGxpbmU=", encoded);
        assertEquals(input, new String(new ShapeTypeSerializerBytes().asObject(encoded)));
    }
}

package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeTypeSerializerStringTest {
    @Test
    public void asJsonNewLine() {
        final String input = "newline:\nnextline";

        assertEquals("\"newline:\\nnextline\"",
                new ShapeTypeSerializerString().asJson(input));
    }

    @Test
    public void asJsonNothingSpecial() {
        assertEquals("\"Nothing special\"",
                new ShapeTypeSerializerString().asJson("Nothing special"));
    }

    @Test
    public void asJsonQuote() {
        final String input = "quote:\"";
        assertEquals("\"quote:\\\"\"",
                new ShapeTypeSerializerString().asJson(input));
    }

    @Test
    public void asJsonCR() {
        final String input = "carriageReturn:\r";
        assertEquals("\"carriageReturn:\"",
                new ShapeTypeSerializerString().asJson(input));
    }

}

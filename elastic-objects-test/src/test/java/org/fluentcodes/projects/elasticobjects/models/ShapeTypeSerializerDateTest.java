package org.fluentcodes.projects.elasticobjects.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShapeTypeSerializerDateTest {
    @Test
    public void asString1() {
        assertEquals("1",
                new ShapeTypeSerializerDate().asString(new Date(1)));
    }
}

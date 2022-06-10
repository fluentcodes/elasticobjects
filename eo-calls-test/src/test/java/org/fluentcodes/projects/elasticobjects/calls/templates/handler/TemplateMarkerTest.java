package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by werner.diwischek on 07.01.18.
 */
public class TemplateMarkerTest {
    @Test
    public void withoutSpace__containsStartSequence__true() {
        Assertions.assertThat(TemplateMarker.CURLY.hasStartSequence(".{")).isTrue();
    }

    @Test
    public void withSpace__containsStartSequence__true() {
        Assertions.assertThat(TemplateMarker.CURLY.hasStartSequence("\n.{")).isTrue();
    }

    @Test
    public void noSpace__isCloseSequence__true() {
        Assertions.assertThat(TemplateMarker.CURLY.isCloseSequence(".{}.")).isTrue();
    }

    @Test
    public void SQUARE_hasStartSequence() {
        assertFalse(TemplateMarker.SQUARE.hasStartSequence(null));
        assertFalse(TemplateMarker.SQUARE.hasStartSequence(""));
        assertFalse(TemplateMarker.SQUARE.hasStartSequence("no"));
        assertTrue(TemplateMarker.SQUARE.hasStartSequence("a@[z"));
        assertTrue(TemplateMarker.SQUARE.hasStartSequence("a&[z"));
        assertTrue(TemplateMarker.SQUARE.hasStartSequence("a#[z"));
        assertTrue(TemplateMarker.SQUARE.hasStartSequence("a.[z"));
    }

    @Test
    public void CURLY_hasStartSequence() {
        assertFalse(TemplateMarker.CURLY.hasStartSequence(null));
        assertFalse(TemplateMarker.CURLY.hasStartSequence(""));
        assertFalse(TemplateMarker.CURLY.hasStartSequence("no"));
        assertTrue(TemplateMarker.CURLY.hasStartSequence("a@{z"));
        assertTrue(TemplateMarker.CURLY.hasStartSequence("a&{z"));
        assertTrue(TemplateMarker.CURLY.hasStartSequence("a#{z"));
        assertTrue(TemplateMarker.CURLY.hasStartSequence("a.{z"));
    }
}

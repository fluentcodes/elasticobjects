package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;


public class LikeTest {

    @Test
    public void testString_string__filter_eoString__true() {
        Like like = new Like(AnObject.F_MY_STRING, "test");
        EoRoot eo = AndTest.TEST_STRING_DEV;
        Assertions.assertThat(like.filter(eo)).isTrue();
    }

    @Test
    public void testString_stringOther__filter_eoString__false() {
        Like like = new Like(AnObject.F_MY_STRING, "stringOther");
        EoRoot eo = AndTest.TEST_STRING_DEV;
        Assertions.assertThat(like.filter(eo)).isFalse();
    }

    @Test
    public void _0_string__filter_rowList__true() {
        Like like = new Like("0", "test");
        Assertions.assertThat(like.filter(AndTest.EXAMPLE_LIST)).isTrue();
    }

    @Test
    public void _2_stringOther__filter_rowList__false() {
        Like like = new Like("2", "stringOther");
        Assertions.assertThat(like.filter(AndTest.EXAMPLE_LIST)).isFalse();
    }

    @Test
    public void _4_1__filter_rowList__true() {
        Like like = new Like("4", "1");
        Assertions.assertThat(like.filter(AndTest.EXAMPLE_LIST)).isTrue();
    }
}

package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.condition.EqTest.DATA_LIST;


public class NotExistTest {
    @Test
    public void testString__filter_eoString__false() {
        NotExists notExists = new NotExists(AnObject.F_MY_STRING);
        EoRoot eo = AndTest.TEST_STRING_DEV;
        Assertions.assertThat(notExists.filter(eo)).isFalse();
    }

    @Test
    public void key__filter_eoString__true() {
        NotExists notExists = new NotExists("key");
        EoRoot eo = AndTest.TEST_STRING_DEV;
        Assertions.assertThat(notExists.filter(eo)).isTrue();
    }

    @Test
    public void _0__filterList__false() {
        NotExists notExists = new NotExists("0");
        Assertions.assertThat(notExists.filter(DATA_LIST)).isFalse();
    }

    @Test
    public void _6__filterList__true() {
        NotExists notExists = new NotExists("6");
        Assertions.assertThat(notExists.filter(DATA_LIST)).isTrue();
    }
}

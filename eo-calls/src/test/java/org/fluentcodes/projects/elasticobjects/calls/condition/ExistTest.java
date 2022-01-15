package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.condition.EqTest.DATA_LIST;

public class ExistTest {
    @Test
    public void testString__filter_eoString__true()  {
        Exists exists = new Exists(AnObject.F_MY_STRING);
        EoRoot eo = AndTest.TEST_STRING_DEV;

        Assertions.assertThat( exists.filter(eo)).isTrue();
    }

    @Test
    public void key__filter_eoString__false()  {
        Exists exists = new Exists("key");
        EoRoot eo = AndTest.TEST_STRING_DEV;

        Assertions.assertThat( exists.filter(eo)).isFalse();
    }

    @Test
    public void _0__filterList__true()  {
        Exists exists = new Exists("0");
        Assertions.assertThat( exists.filter(DATA_LIST)).isTrue();
    }

    @Test
    public void _6__filterList__false()  {
        Exists exists = new Exists("6");
        Assertions.assertThat( exists.filter(DATA_LIST)).isFalse();
    }
}

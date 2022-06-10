package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class AndTest {
    public static final List EXAMPLE_LIST = (List) ObjectProviderDev
            .createEo("[\"test\",\n\"testOther\",\n" + null + ",\n\"key0\",\n1]")
            .get();
    public static final String TEST_STRING = "{\"myString\": \"test\"}";
    public static final EoRoot TEST_STRING_DEV = ObjectProviderDev.createEo(TEST_STRING);

    @Test
    public void eq_testString_string__createQuery__expected() {
        And and = new And(new Eq(AnObject.F_MY_STRING, "test"));
        Assertions
                .assertThat(and.getCondition(0).getKey())
                .isEqualTo(AnObject.F_MY_STRING);
        Assertions
                .assertThat(and.getCondition(0).getValue())
                .isEqualTo("test");
        Assertions
                .assertThat(and.getCondition(0).createQuery(new HashMap<>()))
                .isEqualTo("" + AnObject.F_MY_STRING + "=:" + AnObject.F_MY_STRING + "_0 ");
        Assert.assertEquals(S_STRING, and.getCondition(0).getValue());
    }

    @Test
    public void eq_key0_test_and_eq_key1_testOther__createQuery__expected() {
        And and = new And(
                new Eq("key0", "test"),
                new Eq("key1", "stringOther"));
        Assertions
                .assertThat(and.getCondition(0).getValue())
                .isEqualTo("test");
        Assertions
                .assertThat(and.getCondition(1).getKey())
                .isEqualTo("key1");
        Assertions
                .assertThat(and.getCondition(1).createQuery(new HashMap<>()))
                .isEqualTo("key1=:key1_0 ");
    }

    @Test
    public void eq_testString_test__filter_eoString__true() {
        And and = new And(new Eq(AnObject.F_MY_STRING, "test"));
        EoRoot eo = TEST_STRING_DEV;
        Assertions.assertThat(and.filter(eo)).isTrue();
    }

    @Test
    public void eq_testString_test__filter_eoString__false() {
        And and = new And(new Eq(AnObject.F_MY_STRING, "testOther"));
        EoRoot eo = TEST_STRING_DEV;
        Assertions.assertThat(and.filter(eo)).isFalse();
    }


    @Test
    public void like_0_test__filterList__true() {
        And and = new And(new Like("0", "test"));
        Assertions.assertThat(and.filter(EXAMPLE_LIST)).isTrue();
    }

    @Test
    public void like_2_test__filterList__false() {
        And and = new And(new Like("2", "test"));
        Assertions.assertThat(and.filter(EXAMPLE_LIST)).isFalse();
    }

    @Test
    public void like_4_1__filterList__true() {
        And and = new And(new Like("4", 1));
        Assertions.assertThat(and.filter(EXAMPLE_LIST)).isTrue();
    }

    @Test
    public void like_0_test_and_like_4_1__filter_List__true() {
        And and = new And(new Like("0", "test"), new Like("4", 1));
        Assertions.assertThat(and.filter(EXAMPLE_LIST)).isTrue();
    }

    @Test
    public void like_0_testFalse_and_like_3_1__filter_List__false() {
        And and = new And(new Like("0", "testFalse"), new Like("3", 1));
        Assertions.assertThat(and.filter(EXAMPLE_LIST)).isFalse();
    }

    @Test
    public void string_like_0_test_and_like_4_1__filter_List__true() {
        And and = new And("0 like test && 4 like 1");
        Assertions.assertThat(and.filter(EXAMPLE_LIST)).isTrue();
    }

    @Test
    public void string_like_0_testFalse_and_like_4_1__filter_List__false() {
        And and = new And("0 like testFalse && 4 like 1");
        Assertions.assertThat(and.filter(EXAMPLE_LIST)).isFalse();
    }
}

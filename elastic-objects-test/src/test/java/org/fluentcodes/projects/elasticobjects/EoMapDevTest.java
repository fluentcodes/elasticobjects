package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObjectFromJsonTest;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoMapDevTest {
    @Test
    public void DEV__Boolean__exception() {
        final EoRoot root = ObjectProviderDev.createEo();
        Assertions.assertThatThrownBy(
                () -> {
                    root.map(true);
                })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not map scalar");
    }

    @Test
    public void DEV__JSON_List_Double_0_1_1_2__xpected() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{\"" + PathElement.V_MODEL + "\":\"List,Double\", \"0\":1,\"1\":2}");
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("0").getModelClass()).isEqualTo(Double.class);
        XpectEoJunit4.assertStaticEO(eo);
    }

    @Test
    public void DEV_List__JSON_0_true__get_0_true() {
        final EoRoot root = ObjectProviderDev.createEo(List.class);
        root
                .map("[true]");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assert.assertEquals(Boolean.class, root.getEo("0").getModelClass());
    }


    @Test
    public void DEV__JSON_List_value_1__get_List_0__1() {
        final EoRoot root = ObjectProviderDev.createEo();
        root
                .map("{\"_rootmodel\": \"List\",\"0\": 1}");
        Assertions.assertThat(root.getLog())
                .isEmpty();
        Assertions.assertThat(root.getModelClass())
                .isEqualTo(List.class);
        Assertions.assertThat(root.getEo("0").getModelClass())
                .isEqualTo(Integer.class);
        Assertions.assertThat(root.get("0"))
                .isEqualTo(1);
    }

    @Test
    public void DEV__JSON_key_value__get_key_value() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{\"key\": \"value\"}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("key")).isEqualTo("value");
    }

    @Test
    public void DEV__JSON_key1_1_key2_2_key3_3__get_key1_1() {
        final EoRoot eo = ObjectProviderDev.createEo();
        final String mapJson = "{\"key1\": 1,\"key2\": 2,\"key3\": 3}";
        eo.map(mapJson);
        Assertions.assertThat(eo.get("key1")).isEqualTo(1);
    }

    @Test
    public void DEV__JSON_empty__eo_isEmpty() {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{}");
        Assert.assertTrue(eo.isEmpty());
    }


    @Test
    public void DEV__JSON_key_true__get_key_true() {
        final EoRoot eo = ObjectProviderDev.createEo();
        ;
        eo.map("{\"key\":true}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo(true);
    }

    @Test
    public void DEV__JSON_key_1__get_key_1() {
        final EoRoot eo = ObjectProviderDev.createEo();
        ;
        eo.map("{\"key\":1}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo(1);
    }

    /**
     * Wiki example
     */
    @Test
    public void DEV__JSON_key1_key2_value__expected() {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{\"key1\":{\"key2\":\"value\"}}");
        assertThat(eo.get("key1/key2")).isEqualTo("value");
    }

    @Test
    public void DEV__JSON_2_key_value__expected() {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{\"key1\":\"value1\",\"key2\":\"value2\"}");
        assertThat(eo.get("key1")).isEqualTo("value1");
        assertThat(eo.get("key2")).isEqualTo("value2");
    }

    @Test
    public void DEV__JSON_small__expected() {
        final EoRoot eo = ObjectProviderDev.createEo();
        ;
        eo.map("{\"myString\": \"test\", \"myInt\": 1}");
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.F_MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.F_MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void DEV__JSON_all__expected() {
        final EoRoot eo = ObjectProviderDev.createEo();
        ;
        eo.map(AnObjectFromJsonTest.ALL);
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.F_MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.F_MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void TEST__JSON_all_typed__expected() {
        EoRoot root = ObjectProviderDev.createEo(AnObjectFromJsonTest.ALL);
        assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(root.get(AnObject.F_MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(root.get(AnObject.F_MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void DEV__null__size_0() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.map(null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isZero();
    }

    @Test
    public void DEV_List__String_value__exception() {
        final EoRoot root = ObjectProviderDev.createEo(List.class);
        Assertions.assertThatThrownBy(() -> {
            root.map("value");
        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not map scalar");
    }

    @Test
    public void DEV_List__true__exception() {
        final EoRoot root = ObjectProviderDev.createEo(List.class);
        Assertions.assertThatThrownBy(() -> {
            root.map(true);
        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not map scalar");
    }

    @Test
    public void DEV_List__JSONList_empty__size_0() {
        EoRoot eo = ObjectProviderDev.createEo(List.class);
        eo.map("[]");
        Assert.assertEquals(List.class, eo.getModelClass());
        Assertions.assertThat(eo.size()).isZero();
    }

    @Test
    public void DEV_List__JSONList_0_value__get_0_value() {
        EoRoot eo = ObjectProviderDev.createEo(List.class);
        eo.map("[\"value\"]");
        Assertions.assertThat(eo.get("0")).isEqualTo("value");
    }

    @Test
    public void DEV_List__JSONList_0_0_value__get_0_0_value() {
        EoRoot eo = ObjectProviderDev.createEo(List.class);
        eo.map("[[\"testObject\"]]");
        Assertions.assertThat(eo.getEo("0").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get("0/0")).isEqualTo("testObject");
    }

    @Test
    public void DEV_List__JSONList_0_1__get_0_1() {
        EoRoot eo = ObjectProviderDev.createEo(List.class);
        eo.map("[1]");
        Assertions.assertThat(eo.get("0")).isEqualTo(1);
    }

    @Test
    public void DEV_List__JSONList_0_value_1_2__get_0_value_get_1_2() {
        EoRoot eo = ObjectProviderDev.createEo(List.class);
        eo.map("[\"value\",2]");
        Assertions.assertThat(eo.get("0")).isEqualTo("value");
        Assertions.assertThat(eo.get("1")).isEqualTo(2);
    }

    @Test
    public void DEV__JSON_rootmodle_List_Float_0_1_1__get_0_1_1() {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{\"_rootmodel\": \"List\",\"(Float)0\": 1.1}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("0")).isEqualTo(1.1F);
    }

    @Test
    public void DEV__JSON_rootmodel_List_0_value_1_1__get_0_value_1_1() {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{" +
                "\n\"_rootmodel\":\"List\"," +
                "\n\"0\":\"value\"," +
                "\n\"1\":1\n}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get("0")).isEqualTo("value");
        Assertions.assertThat(eo.get("1")).isEqualTo(1);
    }

    @Test
    public void DEV_List_String__JSON_key_true__get_0_String_true() {
        EoRoot root = ObjectProviderDev.createEoWithClasses(List.class, String.class);
        root
                .map("{\"key\": true}");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get("0")).isEqualTo("true");
    }

    @Test
    public void DEV__Scalar_value__exception() {
        final EoRoot rootEo = ObjectProviderDev.createEo();
        Assertions.assertThatThrownBy(() -> {
            rootEo.map("value");
        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("");
    }

    @Test
    public void DEV_List__JSON_key_true__get_0_true() {
        final EoRoot root = ObjectProviderDev.createEoWithClasses(List.class);
        root.map("{\"key\": true}");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get("0")).isEqualTo(true);
    }

    @Test
    public void DEV__JSON_rootmodel_List_key_1__get_0_1() {
        final EoRoot root = ObjectProviderDev.createEo();
        root
                .map("{\"_rootmodel\": \"List\",\"key\": 1}");
        Assertions.assertThat(root.getLog())
                .isEmpty();
        Assertions.assertThat(root.getModelClass())
                .isEqualTo(List.class);
        Assertions.assertThat(root.get("0"))
                .isEqualTo(1);
    }

    @Test
    public void DEV__JSONList_1__get_0_1() {
        final EoRoot root = ObjectProviderDev.createEo();
        root
                .map("[1]");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.getEo("0").getModelClass()).isEqualTo(Integer.class);
        Assertions.assertThat(root.get("0")).isEqualTo(1);
    }

    @Test
    public void DEV__JSON_List_Double_key_0_1_1_2__get_key_1_2() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.map("{\"(List,Double)key\":{\"0\":1,\"1\":2}}");
        Assertions.assertThat(eo.getEo("key").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get("key/1")).isEqualTo(2.0);
        XpectEoJunit4.assertStaticEO(eo);
    }
}



package org.fluentcodes.projects.elasticobjects.documentation;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.Map;

/**
 * For documentation fluentcodes.org
 */
public class EOHtmlTest {

    @Test
    public void DEV__ConstructorEmpty__hasClassLinkedHashMap() {
        EoRoot eoRoot = ObjectProviderDev.createEo();
        Assertions.assertThat(eoRoot.getModelClass())
                .isEqualTo(Map.class);
    }

    @Test
    public void DEV__ConstructorJson__hasClassLinkedHashMap() {
        EoRoot eoRoot = ObjectProviderDev.createEo("{\"key\":1}");
        Assertions.assertThat(eoRoot.get("key"))
                .isEqualTo(1);
    }


    @Test
    public void TEST__ConstructorJsonAnObjectTyped__hasClassAnObject() {
        EoRoot eoRoot = ObjectProvider.createEo("{\"(AnObject)key\":{\"myString\":\"test\"}}");
        Assertions.assertThat(eoRoot.get("key/myString"))
                .isEqualTo("test");
        Assertions.assertThat(eoRoot.getEo("key").getModelClass())
                .isEqualTo(AnObject.class);
    }

    @Test
    public void DEV__set_abc_test__hasMapTree() {
        EoRoot eoRoot = ObjectProviderDev.createEo();
        EOInterfaceScalar eoChild = eoRoot.set("test", "a/b/c");
        Assertions.assertThat(eoChild.get())
                .isEqualTo("test");
        Assertions.assertThat(((Map) ((Map) ((Map) eoRoot.get()).get("a")).get("b")).get("c"))
                .isEqualTo("test");
    }

    @Test
    public void DEV__set_a_b_c_test__hasValue() {
        EoRoot eoRoot = ObjectProviderDev.createEo();
        EOInterfaceScalar eoChild = eoRoot.set("test", "a", "b", "c");
        Assertions.assertThat(eoRoot.get("a/b/c"))
                .isEqualTo("test");
        Assertions.assertThat(eoChild.getParent().get("/a/b/c"))
                .isEqualTo("test");
    }

    @Test
    public void TEST__set_xyz_AnObject_myString_test__hasMyString() {
        AnObject anObject = new AnObject().setMyString("test");
        EoRoot eoRoot = ObjectProvider.createEo();
        EOInterfaceScalar eoChild = eoRoot.set(anObject, "x/y/z");
        Assertions.assertThat(eoRoot.get("x/y/z/myString"))
                .isEqualTo("test");
        Assertions.assertThat(eoChild.getModelClass())
                .isEqualTo(AnObject.class);
    }

    @Test
    public void TEST__set_typed_abmyString_test__isTest() {
        EoRoot eoRoot = ObjectProvider.createEo();
        eoRoot.set("test", "a/(AnObject)b/myString");
        Assertions.assertThat(eoRoot.get("a/b/myString"))
                .isEqualTo("test");
        Assertions.assertThat(eoRoot.getEo("a/b").getModelClass())
                .isEqualTo(AnObject.class);
    }

    @Test
    public void scopeDev_set_abc_Test__getEO_get__isTest() {
        EoRoot eoRoot = ObjectProviderDev.createEo();
        EOInterfaceScalar eoChild = eoRoot.set("test", "a/b/c");
        Assertions.assertThat(eoChild
                .get())
                .isEqualTo("test");
        Assertions.assertThat(eoChild
                .getParent()
                .getEo("/a/b/c")
                .get())
                .isEqualTo("test");
        Assertions.assertThat(eoRoot
                .getEo("a", "b", "c")
                .get())
                .isEqualTo("test");
    }

    @Test
    public void DEV__getBack__isTest2() {
        EoRoot eoRoot = ObjectProviderDev.createEo();
        EOInterfaceScalar eoChild = eoRoot.set("test1", "a/b/c");
        eoRoot.set("test2", "a/b/x");
        Assertions.assertThat(eoChild
                .getParent()
                .get("x"))
                .isEqualTo("test2");
    }

    @Test
    public void scopeDev_set_abc_Test__get__isTest() {
        EoRoot eoRoot = ObjectProviderDev.createEo();
        EOInterfaceScalar eoChild = eoRoot.set("test", "a/b/c");
        Assertions.assertThat(eoChild.get())
                .isEqualTo("test");
        Assertions.assertThat(eoRoot.get("a", "b", "c"))
                .isEqualTo("test");
    }

}

package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL3;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL4;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL5;


public class EoSetDevTest {

    @Test
    public void mapSmall__set_model_List__Exception() {
        final EoRoot root = ObjectProviderDev
                .createEo("{\"key0\": \"test\", \"key1\": 1}");
        Assertions.assertThatThrownBy(() -> {
            root.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);
        })
                .hasMessage("Could not change model when values are already set");
    }

    @Test
    public void empty__eo_set_key_value__map_get_key_value() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.set("value", "key");
        Assertions.assertThat(((Map) eo.get()).get("key")).isEqualTo("value");
    }

    @Test
    public void constructor_LinkedHashMap__eo_set_key_value__map_get_key_value() {
        final EoRoot eo = ObjectProviderDev.createEo(new LinkedHashMap());
        eo.set("value", "key");
        Assertions.assertThat(((Map) eo.get()).get("key")).isEqualTo("value");
    }

    @Test
    public void key_value__set_model_List__exception() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.set("value", "key");
        Assertions.assertThatThrownBy(() -> {
            eo.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);
        })
                .hasMessage("Could not change model when values are already set");
    }

    @Test
    public void empty__setEmpty_key__ModelClass_Map() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.createChild("key");
        Assertions.assertThat(eo.getEo("key").getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void empty__set_longPath1_overLappingPath2__expected() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.createChild(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3);
        eo.createChild(S_LEVEL0, S_LEVEL1, S_LEVEL4, S_LEVEL5);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3).getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL4, S_LEVEL5).getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void empty__set_key_null__Exception() {
        final EoRoot eo = ObjectProviderDev.createEo();
        Assertions.assertThatThrownBy(() -> {
            eo.set(null, "key");
        })
                .isInstanceOf(EoException.class);
    }

    @Test
    public void empty_class_Map_Map__set_key_value__Exception() {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses(Map.class, Map.class);
        Assertions.assertThatThrownBy(() -> {
            eo.set("value", "key");
        })
                .isInstanceOf(EoException.class);
    }

    @Test
    public void empty__set_test_test2_map_test_3_value__child_get_test3_value() {
        final EoRoot root = ObjectProviderDev.createEo();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("test3", "value");
        IEOScalar child = root.set(map, "test", "test2");
        Assert.assertEquals("value", root.get("test", "test2", "test3"));
        Assert.assertEquals("value", child.get("test3"));
    }

    @Test
    public void empty__setMapComplex__getComplexValues() {
        final EoRoot root = ObjectProviderDev.createEo();

        Map map = new LinkedHashMap<>();
        map.put("test3", "value1");

        Map<String, String> subMap = new LinkedHashMap<>();
        subMap.put("test5", "value2");
        map.put("test4", subMap);

        IEOScalar child = root.set(map, "test1", "test2");
        Assert.assertEquals("value1", root.get("test1", "test2", "test3"));
        Assert.assertEquals("value2", child.get("test4", "test5"));
    }

    /**
     * Given eo with no Model
     * when adding json map small to a path
     * values will be added with the correct type to the path.
     */
    @Test
    public void empty__setJsonSmall__getJsonSmall() {
        final EoRoot eoEmpty = ObjectProviderDev.createEo();
        eoEmpty.set("{\"myString\": \"test\", \"myInt\": 1}", "test1");
        Assertions.assertThat(eoEmpty.getEo("test1").getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eoEmpty.get("test1", AnObject.MY_INT)).isEqualTo(1);
        Assertions.assertThat(eoEmpty.get("test1", AnObject.MY_STRING)).isEqualTo("test");
    }

    @Test
    public void DEV__key0_value__get_key0_value()  {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.set("value", S_LEVEL0);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(eo.getEo().getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo("value");
        Assertions.assertThat(eo.isContainer()).isTrue();
        Assertions.assertThat(eo.getEo(S_LEVEL0).isContainer()).isFalse();
    }

    @Test
    public void DEV__key0_key1_value__getPathAsString_key0_key1()  {
        final EoRoot eo = ObjectProviderDev.createEo();
        final IEOScalar child = eo.set("value", "key0", "key1");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(child.getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(child.getPathAsString()).isEqualTo(Path.DELIMITER + "key0" + Path.DELIMITER + "key1");
    }

    @Test
    public void DEV__key0_true__get_key0_true()  {
        final EoRoot root = ObjectProviderDev.createEo( List.class);
        root.set(true, "key0");
        Assert.assertEquals(true, root.get("0"));
    }

    /**
     * Basic wiki example
     */
    @Test
    public void DEV__key0_key1_key2_key3_asString_value__get_String_key0_key1_key2_key3_asString_value() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.set("value","key0/key1/key2/key3");
        Assertions.assertThat(eo.get("key0/key1/key2/key3")).isEqualTo("value");
    }


    @Test
    public void DEV__key0_key1_key2_key3_value__get_key0_key1_key2_key3_value()  {
        final EoRoot eo = ObjectProviderDev.createEo();
        final IEOScalar child = eo.set("value", "key0","key1","key2", "key3");
        assertEquals("value", eo.get("key0","key1","key2", "key3"));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(Map.class, eo.getEo("key0").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("key0").getClass());

        assertEquals(Map.class, eo.getEo("key0","key1").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("key0","key1").getClass());

        assertEquals(Map.class, eo.getEo("key0","key1","key2").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("key0","key1","key2").getClass());

        assertEquals(String.class, eo.getEo("key0","key1","key2", "key3").getModelClass());
        assertEquals(String.class, eo.get("key0","key1","key2", "key3").getClass());

        assertEquals(Map.class,child.getParent().getModelClass());
        assertEquals(LinkedHashMap.class, child.getParent().get().getClass());

        assertEquals(Map.class,child.getRoot().getModelClass());
        assertEquals(LinkedHashMap.class, child.getRoot().get().getClass());
        assertEquals("/", child.getRoot().getPathAsString());
    }

    @Test
    public void DEV__key0_JSONList_value_1__get_key0_0_value_get_key0_1_1()  {
        final EoRoot eoEmpty = ObjectProviderDev.createEo();
        eoEmpty.set("[\"value\", 1]", "key0");
        Assertions.assertThat(eoEmpty.getEo("key0").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eoEmpty.get("key0", "1")).isEqualTo(1);
        Assertions.assertThat(eoEmpty.get("key0", "0")).isEqualTo("value");
    }

    @Test
    public void DEV__JSON_List_Double_source_0_1_1_2_2_3__getModelClass_source_0_Double()  {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.set("{\"(List,Double)source\": {\"0\": 1,\"1\": 2,\"2\": 3}}");
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Double.class);
    }

    @Test
    public void DEV__JSONList_1_2_3__getModelClass_source_0_Integer()  {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.set("{\"source\": [ 1, 2, 3]}");
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Integer.class);
    }
}
package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoRootDevTest {

    @Test
    public void __empty__ModelClass_Map()  {
        final EoRoot eo = ObjectProviderDev.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void __class_LinkedHashMap__ModelClass_LinkedHashMap()  {
        final EoRoot eo = ObjectProviderDev.createEo(new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void __class_Map__ModelClass_Map()  {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses(Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void __class_Map_String__Models_Map_String()  {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses(Map.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + String.class.getSimpleName());
    }

    @Test
    public void __class_Map_List__Models_Map_List()  {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses( Map.class, List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + List.class.getSimpleName());
    }

    @Test
    public void __value_HashMap__Models_LinkedHashMap()  {
        final EoRoot eo = ObjectProviderDev.createEo(new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(LinkedHashMap.class.getSimpleName());
    }

    @Test
    public void __constructor_HashMap__Models_LinkedHashMap()  {
        final EoRoot eo = ObjectProviderDev.createEo(new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(LinkedHashMap.class.getSimpleName());
    }

    @Test
    public void __JSONMap_empty__ModelClass_Map()  {
        final EoRoot eo = ObjectProviderDev.createEo("{}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        XpectEoJunit4.assertStatic(eo);
    }

    @Test
    public void __JSONMap_rootmodel_List__get_rootmodel_List()  {
        final EoRoot eo = ObjectProviderDev.createEo("{\"_rootmodel\":\"List\"}");
        Assertions.assertThat(eo.get("_rootmodel")).isEqualTo("List");
    }

    @Test
    public void __JSONMap_key_value__get_key_value()  {
        final EoRoot eo = ObjectProviderDev
                .createEo("{\"key\":\"value\"}");
        Assertions.assertThat(eo.get("key")).isEqualTo("value");
    }


    @Test
    public void __value_ArrayList_empty__ModelClass_ArrayList()  {
        final EoRoot eo = ObjectProviderDev.createEo(new ArrayList());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(((List)eo.get()).size()).isEqualTo(0);
    }

    @Test
    public void __class_List__ModelClass_List()  {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses(List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.isEoEmpty()).isFalse();
        Assertions.assertThat(((List)eo.get()).size()).isEqualTo(0);
    }


    @Test
    public void __class_List_String__Models_List_String()  {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses(List.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + String.class.getSimpleName());
    }


    @Test
    public void __class_List_Map__Models_List_Map()  {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses(List.class, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + Map.class.getSimpleName());
        Assertions
                .assertThat(eo.get().getClass())
                .isEqualTo(ArrayList.class);
    }

    @Test
    public void __class_List_Map__class_ArrayList()  {
        final EoRoot eo = ObjectProviderDev.createEoWithClasses(List.class, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.get().getClass())
                .isEqualTo(ArrayList.class);
    }

    @Test
    public void __JSONList_empty__ModelClass_List()  {
        final EoRoot eo = ObjectProviderDev.createEo("[]");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
    }

    @Test
    public void __class_String__exception()  {
        Assertions.assertThatThrownBy(()->{
            ObjectProviderDev.createEoWithClasses( String.class);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void __class_String_String__exception()  {
        Assertions.assertThatThrownBy(()->{
            ObjectProviderDev.createEoWithClasses( String.class, String.class);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void __value_String__exception()  {
        Assertions.assertThatThrownBy(()->{
            ObjectProviderDev.createEo("test");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void __JSONMap_key0_key1_value__noError()  {
        final String json = "{\"key0\": {\"key1\":\"value\"}}";
        EoRoot rootEo = ObjectProviderDev.createEo(json);
        Assertions.assertThat(rootEo.get("key0","key1"))
                .isEqualTo("value");
    }

    @Test
    public void __JSONMap_key0_key1_key2_value__noError()  {
        final String json = "{\"key0\": {\"key1\": {\"key2\":\"value\"}}}";
        EoRoot root = ObjectProviderDev.createEo( json);
        Assertions.assertThat(root.get("key0", "key1", "key2"))
                .isEqualTo("value");
    }
}



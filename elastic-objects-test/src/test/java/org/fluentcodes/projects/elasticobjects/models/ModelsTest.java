package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsTest {
    @Test
    public void ModelConfig____isObject_true() {
        Models models = new Models(CONFIG_MAPS, ModelConfig.class);
        Assert.assertTrue(models.hasModel());
        Assertions.assertThat(models.isScalar()).isFalse();
        Assertions.assertThat(models.isObject()).isTrue();
    }

    @Test
    public void ArrayList____isList_true() {
        Models models = ObjectProviderDev.createModels(ArrayList.class);
        Assertions.assertThat(models.isCreate()).isTrue();
        Assertions.assertThat(models.isScalar()).isFalse();
        Assertions.assertThat(models.isList()).isTrue();
    }

    @Test
    public void AnObject__createChild_myString() {
        EoRoot root = ObjectProvider.createEoWithClasses( AnObject.class);
        root.getModels().createChild(root, new PathElement("myString"), "value");
        Assert.assertEquals("value", root.get("myString"));
    }

    @Test(expected = EoException.class)
    public void AnObject__createChild_notValid() {
        EoRoot root = ObjectProvider.createEoWithClasses( AnObject.class);
        Models rootModels = root.getModels();
        rootModels.createChild(root, new PathElement("notValid"), "value");
    }


    @Test
    public void DEV_List__createChild_key_true__numberUsedAsFieldName() {
        EoRoot root = ObjectProviderDev.createEoWithClasses(List.class);
        root.getModels().createChild(root, new PathElement("key"), true);
        Assert.assertEquals(true, root.get("0"));
    }

    @Test
    public void DEV__deriveChildModels_1__Integer() {
        Models childModels = ObjectProviderDev.createModels()
                .deriveChildModels(new PathElement("anyKey"), 1);
        Assert.assertEquals(Integer.class, childModels.getModelClass());
    }

    @Test
    public void DEV__deriveChildModels_true__Boolean() {
        Models childModels = ObjectProviderDev.createModels()
                .deriveChildModels(new PathElement("anyKey"), true);
        Assert.assertEquals(Boolean.class, childModels.getModelClass());
    }

    @Test
    public void DEV__deriveChildModels_value__String() {
        Models childModels = ObjectProviderDev.createModels()
                .deriveChildModels(new PathElement("anyKey"),"value");
        Assert.assertEquals(String.class, childModels.getModelClass());
    }

    @Test
    public void DEV__deriveChildModels_String_true__String() {
        final Models models = ObjectProviderDev.createModels();
        final PathElement pathElement = new PathElement("anyKey", String.class);
        Models childModels = models
                .deriveChildModels(pathElement, true);
        Assert.assertEquals(String.class, childModels.getModelClass());
    }

    @Test
    public void DEV__deriveChildModels_jsonArray__List() {
        final Models models = ObjectProviderDev.createModels();
        final PathElement pathElement = new PathElement("anyKey");
        Models childModels = models
                .deriveChildModels(pathElement, "[]");
        Assert.assertEquals(List.class, childModels.getModelClass());
    }

    @Test
    public void DEV__deriveChildModels_jsonMap__Map() {
        final Models models = ObjectProviderDev.createModels();
        final PathElement pathElement = new PathElement("anyKey");
        Models childModels = models
                .deriveChildModels(pathElement, "{}");
        Assert.assertEquals(Map.class, childModels.getModelClass());
    }

    @Test
    public void DEV__deriveChildModels_String_jsonMap__String() {
        final Models models = ObjectProviderDev.createModels();
        final PathElement pathElement = new PathElement("anyKey", String.class);
        Models childModels = models
                .deriveChildModels(pathElement, "{}");
        Assert.assertEquals(String.class, childModels.getModelClass());
    }

    @Test
    public void DEV__createChild_Map_String__String() {
        Models childModels = ObjectProviderDev.createModels(Map.class, String.class)
                .createChild(new PathElement("anyKey"));
        Assert.assertEquals(String.class, childModels.getModelClass());
    }

    @Test
    public void DEV__createChild_Map_String_Map__EoException() {
        Models models = ObjectProviderDev.createModels(Map.class, String.class);
        final PathElement pathElement = new PathElement("anyKey", Map.class);

        Assertions.assertThatThrownBy(()->{models.createChild(pathElement);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void DEV__createChild_Map_String_Boolean__String() {
        Models models = ObjectProviderDev.createModels(Map.class, String.class);
        final PathElement pathElement = new PathElement("anyKey", Boolean.class);

        Assert.assertEquals(String.class, models.createChild(pathElement).getModelClass());
    }

    @Test
    public void DEV__createChild_Map_List_Map__EoException() {
        Models models = ObjectProviderDev.createModels(Map.class, List.class);
        final PathElement pathElement = new PathElement("anyKey", Map.class);

        Assertions.assertThatThrownBy(()->{models.createChild(pathElement);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void DEV__createChild_Map_List_String__EoException() {
        Models models = ObjectProviderDev.createModels(Map.class, List.class);
        final PathElement pathElement = new PathElement("anyKey", String.class);

        Assertions.assertThatThrownBy(()->{models.createChild(pathElement);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void DEV__createChild_Map_List_List__String() {
        Models models = ObjectProviderDev.createModels(Map.class, String.class);
        final PathElement pathElement = new PathElement("anyKey", Boolean.class);

        Assert.assertEquals(String.class, models.createChild(pathElement).getModelClass());
    }

    @Test
    public void DEV__createChild_Map_Map_List__EoException() {
        Models models = ObjectProviderDev.createModels(Map.class, Map.class);
        final PathElement pathElement = new PathElement("anyKey", List.class);

        Assertions.assertThatThrownBy(()->{models.createChild(pathElement);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void DEV__createChild_Map_Map_String__EoException() {
        Models models = ObjectProviderDev.createModels(Map.class, Map.class);
        final PathElement pathElement = new PathElement("anyKey", String.class);

        Assertions.assertThatThrownBy(()->{models.createChild(pathElement);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void DEV__createChild_Map_Map_Map__String() {
        Models models = ObjectProviderDev.createModels(Map.class, Map.class);
        final PathElement pathElement = new PathElement("anyKey", Map.class);

        Assert.assertEquals(Map.class, models.createChild(pathElement).getModelClass());
    }
}

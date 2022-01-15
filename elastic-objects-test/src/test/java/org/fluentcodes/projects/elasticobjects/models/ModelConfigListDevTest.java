package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.CONFIG_MAPS_DEV;
import static org.junit.Assert.assertEquals;

public class ModelConfigListDevTest {

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(ModelConfigList.class.getSimpleName());
        assertEquals(ModelConfig.class.getSimpleName(), bean.getSuperKey());
        assertEquals(0, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(ModelConfigList.class.getSimpleName());
        assertEquals(false, config.getProperties().getCreate());
        assertEquals(0, config.getFields().size());
    }

    @Test
    public void findModel_List() {
        ModelConfigList listModel = (ModelConfigList) CONFIG_MAPS_DEV.findModel(List.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(List.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

    @Test
    public void findModel_ArrayList() {
        ModelConfigList listModel = (ModelConfigList) ObjectProviderDev.CONFIG_MAPS_DEV.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }
}

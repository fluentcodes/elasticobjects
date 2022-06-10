package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ModelConfigListTest {

    @Test
    public void findModel_ArrayList() {
        ModelConfigList listModel = (ModelConfigList) ObjectProvider.CONFIG_MAPS.findModel(ArrayList.class);
        Assertions.assertThat(listModel.getModelClass()).isEqualTo(ArrayList.class);
        List list = (List) listModel.create();
        Assertions.assertThat(list.getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(listModel.isList()).isTrue();
        Assertions.assertThat(listModel.isScalar()).isFalse();
    }

}

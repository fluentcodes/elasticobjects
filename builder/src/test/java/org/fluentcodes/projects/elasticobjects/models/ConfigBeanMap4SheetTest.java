package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ConfigBeanMap4SheetTest {
    @Test
    public void TEST_FieldConfigClass_readSheet_eoTestXlsx__hasKey_testInt__isTrue() {
        ConfigBeanMap4Sheet<FieldBean> map = new ConfigBeanMap4Sheet<>(Scope.TEST, FieldConfig.class);
        map.readSheet("eoTest.xlsx");
        Assertions.assertThat(map).isNotNull();
        Assertions.assertThat(map.hasKey("testInt")).isTrue();
    }

    @Test
    public void TEST_ModelConfigClass_readSheet_eoTestXlsx__hasKey_testInt__isTrue() {
        ConfigBeanMap4Sheet<FieldBean> map = new ConfigBeanMap4Sheet<>(Scope.TEST, ModelConfig.class);
        map.readSheet("eoTest.xlsx");
        Assertions.assertThat(map).isNotNull();
        Assertions.assertThat(map.hasKey("Integer")).isTrue();
    }


}

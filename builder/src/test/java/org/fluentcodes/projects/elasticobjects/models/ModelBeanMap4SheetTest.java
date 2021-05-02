package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ModelBeanMap4SheetTest {

    @Test
    public void TEST_readSheet_eoTestXlsx__hasKey_Integer__isTrue() {
        ModelBeanMap4Sheet map = new ModelBeanMap4Sheet(Scope.TEST);
        map.readSheet("eoTest.xlsx");
        Assertions.assertThat(map).isNotNull();
        Assertions.assertThat(map.hasKey("Integer")).isTrue();
    }

    @Test
    public void TEST_readSheet_eoTestXlsx__hasKey___isTrue() {
        ModelBeanMap4Sheet map = new ModelBeanMap4Sheet(Scope.TEST);
        map.readSheet("eoTest.xlsx");
        Assertions.assertThat(map).isNotNull();
        Assertions.assertThat(map.hasKey("ATestObject")).isTrue();
        ModelBeanGen model = map.find("ATestObject");
        FieldBeanGen field = (FieldBeanGen)model.getFieldBean("testInt");
        Assertions.assertThat(field.getJavaInstanceVar()).isEqualTo("   /* testInt */\n" +
                "   private Integer testInt;\n");
    }

}

package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.BaseConfig;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.models.ModelBeanGen;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.BaseConfigInterface.NATURAL_ID;

/**
 * @author Werner Diwischek
 * @since 3.1.2021.
 */
public class ModelBeanGenTest {
    public static EO MODEL_BEANS_EO = readEo();
    private static ModelBeanGen BASE_CONFIG_MODEL = findModelBean(BaseConfig.class);
    public static ModelBeanGen findModelBean(final Class modelClass) {
        return (ModelBeanGen) MODEL_BEANS_EO.get(modelClass.getSimpleName());
    }

    public static EO readEo() {
        ModelBeanMap4SheetCall call = new ModelBeanMap4SheetCall(ModelConfig.class.getSimpleName());
        EO eo = ProviderRootTestScope.createEo();
        call.setSourceFileConfigKey("eo.xlsx");
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    @Test
    public void BaseConfigModel____isFinal() {
        Assertions.assertThat(BASE_CONFIG_MODEL.isFinal()).isTrue();
    }

    @Test
    public void BaseConfigModel__getFieldBean_naturalId__isNotNull() {
        Assertions.assertThat(BASE_CONFIG_MODEL.getFieldBean(NATURAL_ID)).isNotNull();
    }

    @Test
    public void BaseConfigModel__getFieldBean_naturalId_isFinal__true() {
        Assertions.assertThat(BASE_CONFIG_MODEL.getFieldBean(NATURAL_ID).isFinal()).isTrue();
    }

    @Test
    public void AnObject__getImport__expected() {
        ModelBeanGen anObjectModelBean = findModelBean(AnObject.class);
        Assertions.assertThat(anObjectModelBean.getJavaImport()).isEqualTo("import java.util.ArrayList;\n" +
                "import java.util.Date;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n");
    }
}

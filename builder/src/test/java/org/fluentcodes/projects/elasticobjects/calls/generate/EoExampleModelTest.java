package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.ModelBeanGen;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 3.1.2021.
 */
public class EoExampleModelTest {

    public static EO MODEL_BEAN_MAP_EO = readEoTest();
    public static EO A_TEST_OBJECT_MODEL_EO = MODEL_BEAN_MAP_EO.getEo("ATestObject");
    public static final ModelBeanGen A_TEST_OBJECT_MODEL = (ModelBeanGen)A_TEST_OBJECT_MODEL_EO.get();
    //public static ModelBeanGen aSubTestBean = findModelBean("ASubTestObject");

    public static EO readEoTest() {
        ModelBeanMap4SheetCall call = new ModelBeanMap4SheetCall(ModelConfig.class.getSimpleName());
        EO eo = ProviderRootTestScope.createEo();
        call.setSourceFileConfigKey("eoTest.xlsx");
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        return eo;
    }

    @Test
    public void ATestObjectModel__getImport__expected() {
        Assertions.assertThat(A_TEST_OBJECT_MODEL.getJavaImport()).isEqualTo("import java.util.ArrayList;\n" +
                "import java.util.Date;\n" +
                "import java.util.HashMap;\n");
    }

    @Test
    public void Call_JavaCreateTpl__execute_ATestObjectModelEo__expected() {
        TemplateResourceCall call = new TemplateResourceCall("JavaCreate.tpl");
        Assertions.assertThat(call.execute(A_TEST_OBJECT_MODEL_EO)).contains("import java.util.ArrayList;\n" +
                "import java.util.Date;\n" +
                "import java.util.HashMap;\n");
    }

}

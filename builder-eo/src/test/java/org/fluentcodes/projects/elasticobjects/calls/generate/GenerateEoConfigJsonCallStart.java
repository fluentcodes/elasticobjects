package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModuleScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateEoConfigJsonCallStart {
   @Test
    public void call__execute__logEmpty() {
       GenerateEoConfigJsonCall call = new GenerateEoConfigJsonCall(ModelConfig.class.getSimpleName());
        EO eo = ProviderRootTestScope.createEo();
        call.setSourceFileConfigKey("eo.xlsx");
        call.setProjectDirectory("..");
        call.setModule(Moduls.ALL.getName());
        call.setModuleScope(ModuleScope.MAIN.dir());
        String result = call.execute(eo);
        System.out.println(result);
        Assertions.assertThat(result).contains("src/main/resources");
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
}

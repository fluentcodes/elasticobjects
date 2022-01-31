package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModuleScope;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created 10.6.2018
 */
@Ignore("Check to delete")
public class ConfigCallCheck {

    @Test
    public void call_ModelConfig_configFilter_ConfigCall__execute__xpected() {
        ConfigCall call = new ConfigCall(ModelConfig.class,"ConfigCall");
        EoRoot eo = ObjectProvider.createEo();
        List result = (List) call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        XpectEoJunit4.assertStatic(result);
    }


    @Test
    public void call_FieldConfig_module_eoTest__execute__xpected()  {
        final EoRoot eo = ObjectProvider.createEo();
        final ConfigCall call = new ConfigCall(FieldConfig.class);
        call.setModule("elastic-objects-test");
        call.setModuleScope(ModuleScope.MAIN.dir());
        List result = (List) call.execute(eo);
        XpectEoJunit4.assertStatic(result);
    }

    @Test
    public void call_FieldConfig_configFilter_length_condition__execute__xpected() {
        final EoRoot eo = ObjectProvider.createEo();
        final Call call = new ConfigCall(FieldConfig.class)
                .setConfigFilter("length")
                .setStartCondition("length eq $(0/modelKey)$");
        List result = (List) call.execute(eo);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Ignore("Check to delete")
    @Test
    public void eo_ModelConfig_module_eoTest_submodule_main__execute__xpected()  {
        final EoRoot eo = ObjectProvider.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class);
        call.setModule("elastic-objects-test");
        call.setModuleScope(ModuleScope.MAIN.dir());
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        XpectEoJunit4.assertStatic(eo.get());
    }


    @Ignore("Check to delete")
    @Test
    public void eo_ModelConfig_configFilter_Map__execute__xpected() {
        final EoRoot eo = ObjectProvider.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class, ".*Map");
        List result = (List) call.execute(eo);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        XpectEoJunit4.assertStatic(eo.get());
    }
}

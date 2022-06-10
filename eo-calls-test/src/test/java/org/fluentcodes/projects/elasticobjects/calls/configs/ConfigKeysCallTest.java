package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class ConfigKeysCallTest {
    @Test
    public void call_configFilter_naturalId__execute__list_size_1() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setConfigFilter("naturalId");
        List<String> result = (List<String>) call.execute(ObjectProvider.createEo());
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void template_ModelConfig_ConfigKeysCall_WEB_configurationList__execute__xpected() {
        EoRoot eo = ObjectProvider.createEo();
        final String targetPath = "configurationList";
        new Parser("#{ConfigKeysCall->ModelConfig, ConfigKeysCall, WEB, " + targetPath + "}.")
                .parse(eo);
        XpectEoJunit4.assertStatic(eo.get(targetPath));
    }

    @Test
    public void eo_configFilter_naturalId__execute__list_size_1() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setTargetPath("fieldKeys");
        call.setConfigFilter("naturalId");

        EoRoot eo = ObjectProvider.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(((List) eo.get("fieldKeys")).size()).isGreaterThan(0);
    }
}

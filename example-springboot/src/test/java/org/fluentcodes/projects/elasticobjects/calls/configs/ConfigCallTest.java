package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Ignore;
import org.junit.Test;

public class ConfigCallTest {
    // TODO later...
    @Ignore
    @Test
    public void testConfigCallForm__configTypeFieldConfig__fieldConfigsUsedForLinkList() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("Scope", "configSelected");
        eo.set("ModelConfig", "configType");
        eo.set("Scope", "configFilter");
        eo.set("-", "selectedItem");
        eo.addCall(new TemplateResourceCall("ConfigsPage.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        XpectStringJunit4.assertStatic((String) eo.get("_template"));
    }

}

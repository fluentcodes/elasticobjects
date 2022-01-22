package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateDirResourceCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

public class WebCallTest {
    @Test
    public void eo_TemplateDirResourceCall_HeaderHtml__execute__xpected() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("test", "selectedItem");
        eo.addCall(new TemplateDirResourceCall("WEB")
                .setFileName("Header.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        XpectStringJunit4.assertStatic((String) eo.get("_template"));
    }
}

package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Ignore;
import org.junit.Test;

public class FaqNavGenerate {
    @Ignore
    @Test
    public void generate() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("examples", "directory");
        TemplateResourceCall call = new TemplateResourceCall("Nav.tpl");
        String result = call.execute(eo);
    }
}

package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;


/**
 * @author Werner Diwischek
 * @since 20.10.20.
 */
public class TemplateResourceCallConfigsNavigationTest {

    @Test
    public void ModelConfig_configFilter_TemplateCall_expose_WEB__execute__xpected() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("ModelConfig", "configType");
        eo.set("TemplateCall","configFilter");
        eo.set("WEB","expose");
        eo.addCall(new TemplateResourceCall("ConfigsNavigation.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get(PathElement.TEMPLATE)).isNotEmpty();
       XpectStringJunit4.assertStatic((String)eo.get(PathElement.TEMPLATE));
    }

    @Test
    public void ModelConfig_configFilter_TemplateCall_expose_WEB_role_guest__execute__xpected() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("ModelConfig", "configType");
        eo.set("TemplateCall","configFilter");
        eo.set("WEB","expose");
        eo.setRoles("guest");
        eo.addCall(new TemplateResourceCall("ConfigsNavigation.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get(PathElement.TEMPLATE)).isNotEmpty();
    }

    @Test
    public void ModelConfig_configFilter_TemplateCall_expose_WEB_role_none__execute__xpected() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("ModelConfig", "configType");
        eo.set("TemplateCall","configFilter");
        eo.set("WEB","expose");
        eo.setRoles("none");
        eo.addCall(new TemplateResourceCall("ConfigsNavigation.html"));
        eo.execute();
        Assertions.assertThat(eo.getLogList().size()).isEqualTo(1);
        Assertions.assertThatThrownBy(()-> {
            eo.get(PathElement.TEMPLATE);
        })
                .isInstanceOf(EoException.class);
    }
}

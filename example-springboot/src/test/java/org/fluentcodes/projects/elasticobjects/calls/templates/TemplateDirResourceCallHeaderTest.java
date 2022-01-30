package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Werner Diwischek
 * @since 30.10.20.
 */
public class TemplateDirResourceCallHeaderTest {

    @Test
    public void call_fileName_HeaderHtml__execute__noException() {
        EoRoot eo = ObjectProvider.createEo();
        TemplateDirResourceCall call = new TemplateDirResourceCall("WEB", "Header.html");
        call.execute(eo);
    }

    @Test
    public void call_fileName_empty__execute__exception() {
        EoRoot eo = ObjectProvider.createEo();
        TemplateDirResourceCall call = new TemplateDirResourceCall("WEB");
        Assertions.assertThatThrownBy(() -> {
            call.execute(eo);
        })
                .isInstanceOf(EoException.class);
    }

    @Test
    public void no_role__execute__noException() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("problem", "selectedItem");
        eo.addCall(new TemplateDirResourceCall("WEB", "Header.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get(PathElement.TEMPLATE)).isNotEmpty();
    }

    @Test
    public void role_guest_has_selectedItem__execute__noException() {
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles("guest");
        eo.set("problem", "selectedItem");
        eo.addCall(new TemplateDirResourceCall("WEB", "Header.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get(PathElement.TEMPLATE)).isNotEmpty();
    }

    @Ignore
    @Test
    public void role_guest_no_selectedItem__execute__hasError() {
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles("guest");
        eo.addCall(new TemplateDirResourceCall("WEB", "Header.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat((String) eo.get(PathElement.TEMPLATE)).isNotEmpty();
    }

    @Test
    public void role_none_has_selectedItem__execute__noTemplate() {
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles("none");
        eo.set("problem", "selectedItem");
        eo.addCall(new TemplateDirResourceCall("WEB", "Header.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThatThrownBy(() -> {
            eo.get(PathElement.TEMPLATE);
        })
                .isInstanceOf(EoException.class);
    }
}

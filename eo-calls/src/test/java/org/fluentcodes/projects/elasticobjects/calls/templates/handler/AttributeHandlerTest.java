package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.createEo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AttributeHandlerTest {

    @SuppressWarnings({"java:S5778", "maybe later"})
    @Test
    public void callCallDirectiveNullThrowsEoException() {
        Assertions.assertThatThrownBy(() -> {
                    new AttributeHandler()
                            .setEo(createEo())
                            .call();
                }
        ).isInstanceOf(EoException.class);
    }

    @SuppressWarnings({"java:S5778", "maybe later"})
    @Test
    public void callNoEoReturnDefault() {
        String returnValue =
                (String) new AttributeHandler()
                        .setDefaultValue("default")
                        .call();
        assertEquals("default", returnValue);
    }

    @SuppressWarnings({"java:S5778", "maybe later"})
    @Test
    public void callCallDirectiveEmptyThrowsEoException() {
        Assertions.assertThatThrownBy(() -> {
                    new AttributeHandler()
                            .setCallDirective("")
                            .setEo(createEo())
                            .call();
                }
        ).isInstanceOf(EoException.class);
    }

    @Test
    public void callFileReadCall() {
        EoRoot eo = createEo();
        final String directive = FileReadCall.class.getSimpleName() + " targetPath=\"test\" fileConfigKey=\"ContentExample\" ";
        Object result = new AttributeHandler()
                .setTemplateMarker(TemplateMarker.CURLY)
                .setCallDirective(directive)
                .setEo(eo)
                .call();
        assertNotNull(eo.get("test"));
    }
}

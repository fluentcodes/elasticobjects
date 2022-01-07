package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;


/**
 * @author Werner Diwischek
 * @since 2.11.20.
 */
public class TemplateDirResourceCallTest {

    @Test
    public void eo_examples_fileName_DbModelReadCallHtml__execute__noException() {
        EoRoot eo = ObjectProvider.createEo("{\"(TemplateDirResourceCall)_asString\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"DbModelReadCall.html\",\n" +
                "                 \"logLevel\":\"NONE\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get(PathElement.TEMPLATE)).isNotEmpty();
        XpectStringJunit4.assertStatic((String) eo.get(PathElement.TEMPLATE));
    }

    @Test
    public void eo_examples_fileName_CallImplHtml_logLevel_none__execute__noLog() {
        EoRoot eo = ObjectProvider.createEo("{\"(TemplateDirResourceCall)_asString\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\",\n" +
                "                 \"logLevel\":\"NONE\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void eo_examples_fileName_CallImplHtml__execute__hasLog() {
        EoRoot eo = ObjectProvider.createEo("{\"(TemplateDirResourceCall)_asString\":{\n" +
                "                 \"fileConfigKey\":\"examples\",\n" +
                "                 \"fileName\":\"CallImpl.html\"\n" +
                "                }\n" +
                "            }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

}

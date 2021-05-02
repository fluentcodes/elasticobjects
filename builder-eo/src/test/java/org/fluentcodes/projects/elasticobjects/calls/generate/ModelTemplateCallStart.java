package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryMapReadCall;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class ModelTemplateCallStart {

    public static void createAll(final String naturalId) {
        ModelTemplateTargetMapCall call = new ModelTemplateTargetMapCall();
        call.setSourceFileConfigKey("eo.xlsx");
        call.setTargetFileConfigKey("classes.java");
        call.setProjectDirectory("..");
        call.setPackagePath("org.fluentcodes.projects.elasticobjects.");
        call.setFileEnding("");
        call.setNaturalId(naturalId);
        EO eo = ProviderRootTestScope.createEo();
        eo.setLogLevel(LogLevel.INFO);
        String result = call.execute(eo);
        Assertions.assertThat(eo.hasErrors()).isFalse();
        Assertions.assertThat(result).contains("src/main");
        System.out.println("Executed within " + call.getDuration() + " ms: " + result);
        System.out.println(eo.getLog());

    }

    @Test
    public void call_AnObject__execute__logEmpty() {
        createAll(AnObject.class.getSimpleName());
    }

    @Test
    public void call_ASubObject__execute__logEmpty() {
        createAll(ASubObject.class.getSimpleName());
    }

    @Test
    public void call_DirectoryMapReadCall__execute__logEmpty() {
        createAll(DirectoryMapReadCall.class.getSimpleName());
    }
    @Test
    public void call__execute__logEmpty() {
        createAll(ConfigConfigInterface.class.getSimpleName());
    }
}

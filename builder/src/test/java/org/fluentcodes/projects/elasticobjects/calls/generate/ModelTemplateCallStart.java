package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class ModelTemplateCallStart {

    public static void createAll(final String naturalId) {
        ModelTemplateTargetMapCall call = new ModelTemplateTargetMapCall();
        call.setSourceFileConfigKey("eoTest.xlsx");
        call.setTargetFileConfigKey("Create.java");
        call.setProjectDirectory("..");
        call.setPackagePath("org.fluentcodes.projects.elasticobjects.");
        call.setFileEnding("");
        call.setNaturalId(naturalId);
        EO eo = ProviderRootTestScope.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" file ");
        System.out.println(result);
    }

    @Test
    public void call_AnObject__execute__logEmpty() {
        createAll("ATestObject");
    }

    @Test
    public void call_ASubObject__execute__logEmpty() {
        createAll("ATestSubObject");
    }

    @Test
    public void call_All__execute__logEmpty() {
        createAll(".*");
    }

    @Test
    public void call_String__execute__logEmpty() {
        createAll(String.class.getSimpleName());
    }
}

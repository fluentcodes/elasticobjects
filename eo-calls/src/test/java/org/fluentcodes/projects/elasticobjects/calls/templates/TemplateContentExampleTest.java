package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateContentExampleTest {
    public static final String CONTENT_EXAMPLE_DATA = "ContentExampleData";
    public static final String STATIC_KEEP_TPL = "ContentExampleStaticKeep";
    public static final String DYNAMIC_TPL = "ContentExampleDynamic";
    private static final String DATA = "[\n" +
            "    {\n" +
            "        \"header\": \"header1\",\n" +
            "            \"summary\": \"summary1\",\n" +
            "            \"content\": \"content1\",\n" +
            "            \"template\": \"ContentExampleElement1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"header\": \"header2\",\n" +
            "            \"summary\": \"summary2\",\n" +
            "            \"content\": \"content2\",\n" +
            "            \"template\": \"ContentExampleElement2\"\n" +
            "    }\n" +
            "]";

    @Test
    public void DATA__() {
        EoRoot eo = ObjectProvider.createEo(DATA);
        Assert.assertEquals("header1", eo.get("0/header"));
        TemplateResourceCall call = new TemplateResourceCall("ContentExampleElement1");
        call.setTargetPath(Call.TARGET_AS_STRING);
        String value = call.execute(eo.getEo("/0"));
        Assert.assertEquals("\n" +
                "<h1>header1</h1>\n" +
                "<strong>summary1</strong>\n" +
                "<p>content1</p>", value);
    }

    @Test
    public void eoList_FileReadCall_ContentExampleData__execute__xpected() {
        EoRoot eo = ObjectProvider.createEoWithClasses(List.class);
        final FileReadCall call = new FileReadCall(CONTENT_EXAMPLE_DATA);
        call.setTargetPath(".");
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals("header1", eo.get("0/header"));
    }

    @Test
    public void __eo_ContentExampleDataJson__xpected() {
        EoRoot eo = ObjectProvider.createEo(DATA);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        eo.setSerializationType(JSONSerializationType.STANDARD);
        XpectEoJunit4.assertStaticEO(eo);
    }

    @Test
    public void eo_DataJson__parse_Template__replaced() {
        EoRoot eo = ObjectProvider.createEo(DATA);
        String value = new Parser("-.{0/header}.-").parse(eo);
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_DataJson__parse_Template_WrongPathRelativePath__notReplaced() {
        EoRoot eo = ObjectProvider.createEo(DATA);
        String value = new Parser("-.{0/header}.-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-!!0/headerCould not move to path '0' because key '0' does not exist on '/1'.!!-");
    }

    @Test
    public void eo_DataJson__parse_template_absolutePath__replaced() {
        EoRoot eo = ObjectProvider.createEo(DATA);
        String value = new Parser("-.{/0/header}.-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_StaticJson__execute_xpected() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "  \"(FileReadCall)data\": {\n" +
                "    \"fileConfigKey\":\"ContentExampleData\"\n" +
                "  },\n" +
                "  \"(TemplateCall)_asString\": {\n" +
                "    \"content\":\"Start of content with a fileConfigKey: \"\n" +
                "  },\n" +
                "  \"(TemplateResourceCall)_asString\": {\n" +
                "    \"sourcePath\": \"data/*\",\n" +
                "    \"fileConfigKey\":\"ContentExampleElement1\"\n" +
                "  }\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isNotEmpty();
        XpectStringJunit4.assertStatic((String) eo.get("_template"));
    }

    @Ignore
    @Test
    public void call_StaticTpl__execute__xpected() {
        final EoRoot eo = ObjectProvider.createEo();

        final TemplateResourceCall call = new TemplateResourceCall("ContentExampleStatic");
        final String result = call.execute(eo);
        XpectStringJunit4.assertStatic(result);
    }

    @Ignore("problem with maven")
    @Test
    public void call_StaticKeepTpl__execute__xpected() {
        final EoRoot eo = ObjectProvider.createEo();

        final TemplateResourceCall call = new TemplateResourceCall(STATIC_KEEP_TPL);
        final String result = call.execute(eo);
        XpectStringJunit4.assertStatic(result);
    }

    @Test
    public void call_StaticConditionTpl__execute__xpected() {
        final EoRoot eo = ObjectProvider.createEo();

        final TemplateResourceCall call = new TemplateResourceCall("ContentExampleStaticCondition");
        final String result = call.execute(eo);
        XpectStringJunit4.assertStatic(result);
    }

    @Test
    public void eo_DynamicJson__execute__xpected() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "  \"(FileReadCall)data\": {\n" +
                "    \"fileConfigKey\":\"ContentExampleData\"\n" +
                "  },\n" +
                "  \"(TemplateCall)_asString\": {\n" +
                "    \"content\":\"Start of content with a fileConfigKey as variable: \"\n" +
                "  },\n" +
                "  \"(TemplateResourceCall)_asString\": {\n" +
                "    \"sourcePath\": \"data/*\",\n" +
                "    \"fileConfigKey\":\".[template].\"\n" +
                "  }\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isNotEmpty();
        XpectStringJunit4.assertStatic((String) eo.get("_template"));
    }

    @Test
    public void call_DynamicTpl__execute__xpected() {
        final EoRoot eo = ObjectProvider.createEo();
        final TemplateResourceCall call = new TemplateResourceCall(DYNAMIC_TPL);
        final String result = call.execute(eo);
        XpectStringJunit4.assertStatic(result);
    }
}

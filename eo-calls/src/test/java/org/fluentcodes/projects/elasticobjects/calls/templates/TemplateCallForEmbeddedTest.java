package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateCallForEmbeddedTest {
    private static final String DATA = "{\n" +
            "    \"path\":\"value0\",\n" +
            "    \"level0\":{\n" +
            "        \"path\":\"value1\",\n" +
            "            \"level1\":{\n" +
            "                \"path\":\"value2\",\n" +
            "                    \"level2\":{\n" +
            "                        \"path\":\"value3\"\n" +
            "                    }\n" +
            "            }\n" +
            "    }\n" +
            "}";
    private static final EoRoot DATA_EO = ObjectProvider.createEo(DATA);

    @Test
    public void call_sourcePath_level0__execute__replaced() {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0\"}" +
                        "}|" +
                        " ..path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(DATA_EO);
        Assertions.assertThat(result).isEqualTo("path='value0' --> ..path='value0', path='value1'");
    }

    @Test
    public void call_sourcePath_lstar__execute__replaced() {
        final TemplateCall call = new TemplateCall(
                "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"l*\"}" +
                        "}|" +
                        " ..path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(DATA_EO);
        Assertions.assertThat(result).isEqualTo("--> ..path='value0', path='value1'");
    }

    // TODO
    @Ignore
    @Test
    public void call_sourcePath_star__execute__replaced() {
        final TemplateCall call = new TemplateCall(
                "-->  @{(TemplateCall).\":{" +
                        "\"sourcePath\":\"*\"}" +
                        "}|" +
                        " ..path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(DATA_EO);
        Assertions
                .assertThat(result)
                .isEqualTo("--> ..path='value0', path='!!Could not move to path 'path' because key 'path' does not exist on '/path'.!!'..path='value0', path='value1'");
    }

    @Test
    public void call_sourcePath_level0level1_placeholders__execute__replaced() {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0/level1\"}" +
                        "}| ../path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(DATA_EO);
        Assertions.assertThat(result).isEqualTo("path='value0' --> ../path='value1', path='value2'");
    }

    @Test
    public void call_sourcePath_level0_level1_placeholders__execute__replaced() {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0\"}" +
                        "}| " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level1\"}" +
                        "}|" +
                        " ../path='.{../path}.', path='.{path}.'" +
                        ".{}." +
                        ".{}.");
        final String result = call.execute(DATA_EO);
        Assertions.assertThat(result).isEqualTo("path='value0' --> --> ../path='value1', path='value2'");
    }

    @Test
    public void call_sourcePath_level0level1level3_placeholders__execute__replaced() {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "--> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0/level1/level2\"}" +
                        "}|" +
                        " ../path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(DATA_EO);
        Assertions.assertThat(result).isEqualTo("path='value0' --> ../path='value2', path='value3'");
    }

    @Test
    public void call_sourcePath_level0_level1_level3_placeholders__execute__replaced() {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "--> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0\"}" +
                        "}|" +
                        " --> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level1\"}" +
                        "}|" +
                        " --> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level2\"}" +
                        "}|" +
                        " ../path='.{../path}.', path='.{path}.'" +
                        ".{}." +
                        ".{}." +
                        ".{}.");
        final String result = call.execute(DATA_EO);
        Assertions.assertThat(result).isEqualTo("path='value0' --> --> --> ../path='value2', path='value3'");
    }
}

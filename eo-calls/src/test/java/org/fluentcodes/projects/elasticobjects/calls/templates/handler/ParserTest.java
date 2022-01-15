package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 07.01.18.
 */
public class ParserTest {

    @Test
    public void tmp__parse__error() {
        String replace = ".{TMP}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("!!Null eo wrapper defined to get 'TMP'!!");
    }

    @Test
    public void tmp_default__parse__default() {
        String replace = ".{TMP|>TEST}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_not_exist_default__parse__default() {
        String replace = "#{NO_CALL->|>TEST}.";
        String result = new Parser(replace).parse(ObjectProviderDev.createEo());
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_not_exist_default__parse_without_eo__default() {
        String replace = "#{NO_CALL->|>TEST}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_json_not_exist_default__parse__default() {
        String replace = "@{\"(NO_CALL)\":{} |>TEST}.";
        String result = new Parser(replace).parse(ObjectProviderDev.createEo());
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void call_json_not_exist_default__parse_without_eo__default() {
        String replace = "#{NO_CALL->|>TEST}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void placeHolder_testPath_eo__parse__replace() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set("testValue", "testPath");
        String replace = "-.{testPath}.-";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(result).isEqualTo("-" + "testValue" + "-");
    }

    @Test
    public void placeHolder_testPath_noEo__parse__exception_in_template() {
        String replace = "-.{testPath}.-";
        String result = new Parser(replace).parse();
        Assertions.assertThat(result).isEqualTo("-!!Null eo wrapper defined to get 'testPath'!!-");
    }

    @Test
    public void json_TemplateCall_testPath_testValue__parse__xpected() {
        final EoRoot eo = ObjectProvider.createEo("{\"testPath\":\"testString\"}");
        final String replace = "- @{\"(TemplateCall).\":{" +
                "\"sourcePath\":\"testPath\"}" +
                "}|+.{_value}.+" +
                ".{}.-";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+testString+-");
    }

    @Test
    public void set_TemplateCall_testPath_testValue__parse__xpected() {
        final EoRoot eo = ObjectProvider.createEo("{\"testPath\":\"testString\"}");
        final String replace = "- #{TemplateCall->testPath}|" +
                "+.{_value}.+" +
                ".{}.-";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+testString+-");
    }

    @Test
    public void TemplateCall_testPath__parse__replaced() {
        final EoRoot eo = ObjectProvider.createEo();
        eo.set(AnObject.F_MY_STRING, "testPath");
        final String replace = "-" +
                " @{\"(TemplateCall).\":{" +
                "\"sourcePath\":\"testPath\"}" +
                "}|" +
                "+" +
                ".{_value}." +
                "+" +
                ".{}.-";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("-+" + AnObject.F_MY_STRING + "+-");
    }

    @Test
    public void ValueCall_level0_map__parse__valueSet() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = " @{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "{\"1\":2}" +
                ".{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEmpty();
        Assertions.assertThat(eo.get("level0", "1")).isEqualTo(2);
    }

    @Test
    public void ValueCall_targetPath_level0_placeHolder__parse__replaced() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = " @{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "{\"1\":2}" +
                ".{}." +
                "Value: \n.{level0/1}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("Value: 2");
    }

    @Test
    public void direct_targetPath_level0_placeHolder__parse__replaced() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = " @{\"level0\":{\"1\":2}}." +
                "Value: \n.{level0/1}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("Value: 2");
    }

    @Test
    public void array_TemplateCall_content_trailing_space__parse__123() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"data\":[1,2,3]}." +
                "#{TemplateCall->/data/*}|.{_value}. .{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("123");
    }

    @Test
    public void array_123_TemplateCall_content_trailing_space_newline__parse__1_2_3_() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"data\":[1,2,3]}." +
                "#{TemplateCall->/data/*}|.{_value}. \n.{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("1 2 3 ");
    }

    @Test
    public void array_123_TemplateCall_content_trailing_space_backslash_newline__parse__1_2_3_() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"data\":[1,2,3]}." +
                "#{TemplateCall->/data/*}|.{_value}. \\\n.{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("1 2 3 ");
    }

    @Test
    public void array_TemplateCall_content_start_space_new_line__parse___1_2_3() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"data\":[1,2,3]}." +
                "#{TemplateCall->/data/*}| \n.{_value}..{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" 1 2 3");
    }

    @Test
    public void array_TemplateCall_content_start_space_backslash_newline__parse___1_2_3() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"data\":[1,2,3]}." +
                "#{TemplateCall->/data/*}| \\\n.{_value}. .{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" 1 2 3");
    }

    @Test
    public void array_TemplateCall_content_start_space__parse__123() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"data\":[1,2,3]}." +
                "#{TemplateCall->/data/*}| .{_value}..{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("123");
    }

    @Test
    public void array_TemplateCall_content_start_a_space__parse__123() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"data\":[1,2,3]}." +
                "#{TemplateCall->/data/*}| a.{_value}..{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" a1 a2 a3");
    }

    @Test
    public void TemplateCall_parent__parse__val2a() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"val1\":{\"a\":\"1\"},\"val2\":{\"a\":\"2\"}}." +
                "#{TemplateCall->/val1/a}| val2/a = \n .{/val2/_parent}.  .{}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains(" val2/a = 2");
    }

    @Test
    public void eo_key0_key1_value__parse__value() {
        final EoRoot eo = ObjectProvider.createEo();
        final String replace = "" +
                "@{\"key0\":{\"key1\":\"value\"}}." +
                ".{key0/key1}.";
        String result = new Parser(replace).parse(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("value");
    }


}

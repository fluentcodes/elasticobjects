package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SinusValueCall}
 *
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class SinusValueCallTest implements IModelConfigCreateTests {
    private static final Logger LOG = LogManager.getLogger(SinusValueCallTest.class);
    private static final String SOURCE = "source";
    private static final String TARGET = "/target";
    private static final Double SIMPLE_RESULT = 0.8632093666488737;
    private static final Double ARRAY_RESULT2 = 0.1411200080598672;

    private static final String DATA = "{\"(List,Double)source\":[1,2,3]}";
    private static final EoRoot DATA_EO = ObjectProvider.createEo(DATA);

    @Override
    public Class<?> getModelConfigClass() {
        return SinusValueCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }


    public static final EoRoot createSimple() {
        return ObjectProvider.createEo("{\"(Double)source\":2.1}");
    }

    /**
     * Wiki example
     */
    @Test
    public void eo_source_1_call_target__execute__get_target_0_8414709848078965() {
        final EoRoot eo = ObjectProvider.createEo();
        final String jsonString = "{\n" +
                "  \"(Double)source\":1,\n" +
                "  \"(SinusValueCall)/target\": {\n" +
                "    \"sourcePath\": \"/source\"\n" +
                "  }\n" +
                "}";
        eo.map(jsonString);
        eo.execute();
        assertThat(eo.get("target")).isEqualTo(0.8414709848078965);
    }

    @Ignore("Should be solved. ")
    @Test
    public void givenCallSinusValue_thenInputValueIsReplaced() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "  \"(Double)source\":1,\n" +
                "  \"(SinusValueCall)\": {\n" +
                "      \"sourcePath\": \"/source\"\n" +
                "  }\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.get("source")).isEqualTo(0.8414709848078965);
        Assertions.assertThat(eo.getEo("source").isChanged()).isTrue();
    }

    @Test
    public void eo_source_1__call_execute__return_0_8632093666488737() {
        final Call call = new SinusValueCall();
        EoRoot eo = createSimple();
        Double result = (Double) call.execute(eo.getEo("source"));
        Assertions.assertThat(result).isEqualTo(0.8632093666488737);
    }

    @Test
    public void checkSerializationSimple() {
        Call call = new SinusValueCall();
        call.setSourcePath(SOURCE);
        EoRoot eo = createSimple().getRoot();
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        String value = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJson(eo);
        EoRoot eoFromString = ObjectProvider.createEo(value);
        Assertions.assertThat(eoFromString.getLog()).isEmpty();
        eoFromString.execute();
    }

    @Ignore("Check to delete")
    @Test
    public void createFromModel_ok() {
        final ModelConfig model = ObjectProvider.findModel(SinusValueCall.class);
        final Call call = (Call) model.create();
        EoRoot eo = createSimple();
        Double result = (Double) call.execute(eo);
        Assertions.assertThat(result).isNotNull();
        final EoRoot eoCall = ObjectProvider.createEo(call);
        final String asString = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJson(eoCall);
        final Call fromString = (Call) ObjectProvider.createEo(asString).get();
        Double fromResult = (Double) fromString.execute(eo);
        Assertions.assertThat(fromResult).isEqualTo(result);
    }

    @Test
    public void givenEoSimple_WhenExecuteDirect_ResultExpected() {
        EoRoot eo = createSimple();
        Double result = (Double) new SinusValueCall().execute(eo.getEo("source"));
        Assertions.assertThat(result).isEqualTo(SIMPLE_RESULT);
    }

    @Test
    public void givenEoSimple_whenExecuteWithEo_thenDurationIsSet() throws InterruptedException {
        final Call call = new SinusValueCall().setSourcePath(SOURCE);
        EoRoot eo = createSimple();
        eo.setLogLevel(LogLevel.WARN);
        eo.addCall(call);
        eo.execute();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        XpectEoJunit4.assertStatic(eo.getRoot());
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.setSerializationType(JSONSerializationType.EO);
        final String asString = new EOToJSON()
                .toJson(eo.getRoot());
        final EoRoot fromString = ObjectProvider.createEo(asString);
        fromString.setLogLevel(LogLevel.INFO);
        fromString.execute();
        Assertions.assertThat(fromString.get(SOURCE)).isEqualTo(SIMPLE_RESULT);
        Long duration = (Long) fromString.get(PathElement.CALLS, "0", "duration");
        Assertions.assertThat(duration).isNotNull();
    }

    @Test
    public void givenEoArrayWithSourceAndTargetFromFile_whenExecute_hasSinusValueInTarget() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "  \"(List,Double)source\": {\n" +
                "    \"0\": 1,\n" +
                "    \"1\": 2,\n" +
                "    \"2\": 3\n" +
                "  },\n" +
                "  \"(LogLevel)_logLevel\": \"WARN\",\n" +
                "  \"(List)_calls\": {\n" +
                "    \"(SinusValueCall)0\": {\n" +
                "      \"sourcePath\": \"/source/*\",\n" +
                "      \"targetPath\": \"/target\"\n" +
                "    }\n" +
                "  }\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
    }

    @Ignore("Check to delete")
    @Test
    public void givenEoArrayWithSourceAndTargetFromFileOnTargetPath_whenExecute_hasSinusValueInTarget() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "  \"(List,Double)source\": {\n" +
                "    \"0\": 1,\n" +
                "    \"1\": 2,\n" +
                "    \"2\": 3\n" +
                "  },\n" +
                "  \"(LogLevel)_logLevel\": \"WARN\",\n" +
                "  \"(SinusValueCall)target\": {\n" +
                "      \"sourcePath\": \"/source/*\"\n" +
                "  }\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
    }

    @Test
    public void eo_1_2_3__execute_serialize__() {
        Call call = new SinusValueCall()
                .setTargetPath(TARGET)
                .setSourcePath("/source/*");
        EoRoot eo = DATA_EO;
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
        String value = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJson(eo.getRoot());
        EoRoot eoFromJson = ObjectProvider.createEo(value);
        Assertions.assertThat(eoFromJson.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS, "0")).isNotNull();
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS, "0", "targetPath")).isEqualTo(TARGET + "/2");
    }

    @Test
    public void givenEoWithSimpleSinusCall_whenExecuteEo_thenPlaceHolderIsReplaced() {
        TemplateCall call = new TemplateCall();
        call.setContent("sin(.{testKey}.) = \n" +
                "@{\"(SinusValueCall).\":{" +
                "\"sourcePath\":\"testKey\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}.");
        EoRoot eo = ObjectProvider.createEo();
        eo.addCall(call);
        eo.set(2, "testKey");
        eo.execute();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isEqualTo("sin(2) = 0.9092974268256817");
    }


    @Test
    public void eo_CallSinusArrayTemplateJson__execute__3times() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "   \"(List,Double)source\": {\n" +
                "     \"0\": 1,\n" +
                "     \"1\": 2,\n" +
                "     \"2\": 3\n" +
                "   },\n" +
                "   \"(List)_call\":{\n" +
                "     \"(SinusValueCall)0\": {\n" +
                "       \"sourcePath\": \"/source/*\",\n" +
                "       \"targetPath\": \"/target\"\n" +
                "     },\n" +
                "     \"(TemplateCall)1\": {\n" +
                "        \"sourcePath\": \"/source/*\",\n" +
                "       \"content\": \"sin( .{_value}.) = \\n .{/target/_parent}.\\n\"\n" +
                "     }\n" +
                "   }\n" +
                " }");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isEqualTo(
                "sin(1.0) = 0.8414709848078965\n" +
                        "sin(2.0) = 0.9092974268256817\n" +
                        "sin(3.0) = 0.1411200080598672\n");
    }

    @Test
    public void Eo_value_2_template__parse__get_value_0() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set(2, "value");
        String result = new Parser("-" +
                " @{\"(SinusValueCall)value\":{" +
                "\"sourcePath\":\"value\"}" +
                "}." +
                "-").parse(eo);
        Assertions.assertThat(result).isEqualTo("--");
        Assertions.assertThat(eo.get("value")).isEqualTo(Double.parseDouble("0.9092974268256817")); // was integer before and 0.9.. will be mapped to 0.
    }

    @Test
    public void givenEo_whenReplaceStringInTemplate_thenPlaceHolderIsReplaced() {
        EoRoot eo = ObjectProvider.createEo();
        eo.set(2, "value");
        String result = new Parser("-" +
                " @{\"(SinusValueCall).\":{" +
                "\"sourcePath\":\"value\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}." +
                "-").parse(eo);
        Assertions.assertThat(result).isEqualTo("-0.9092974268256817-");
        Assertions.assertThat(eo.get("value")).isEqualTo(2); // was integer before.
    }
}

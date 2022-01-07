package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL3;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * Tests for {@link SinusValueCall}
 *
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class ValueCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ValueCall.class;
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

    @Test
    public void givenModelConfig_whenIsCall_thenTrue() {
        Assertions
                .assertThat(
                        ObjectProvider.findModel(ValueCall.class).isCall()
                ).isTrue();
    }

    @Test
    public void givenModelCreate_whenSetValue_thenValueIsSet() {
        final ModelConfig model = ObjectProvider.findModel(ValueCall.class);
        final ValueCall call = (ValueCall) model.create();
        model.set(ValueCall.CONTENT, call, S_STRING);
        Assertions.assertThat(model.get(ValueCall.CONTENT, call)).isEqualTo(S_STRING);
    }

    @Test
    public void givenModels_thenChildValueTypeIsString() {
        final EoRoot eo = ObjectProvider.createEo();
        final Models models = new Models(eo.getConfigMaps(), ValueCall.class);
        Models childModels = models.getChildModels(new PathElement(ValueCall.CONTENT));
        Assertions.assertThat(childModels.isScalar()).isTrue();
        Assertions.assertThat(childModels.getModelClass()).isEqualTo(String.class);
    }

    @Test
    public void call_String__execute__return_String() {
        final ValueCall call = new ValueCall(S_STRING);
        EoRoot eo = ObjectProvider.createEo();
        Assertions.assertThat(call.execute(eo)).isEqualTo(S_STRING);
    }

    @Ignore
    @Test
    public void givenEoWithCallString_whenExecuteEo_thenEoIsValue() {
        EoRoot eo = ObjectProvider.createEo();
        final ValueCall call = new ValueCall(S_STRING);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallStringWithPath_whenExecuteEo_thenEoIsValue() {
        EoRoot eo = ObjectProvider.createEo();
        final ValueCall call = new ValueCall(S_STRING);
        call.setTargetPath(S_LEVEL0);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallJsonMapWithPath_whenExecuteEo_thenEoIsMap() {
        EoRoot eo = ObjectProvider.createEo();
        final ValueCall call = new ValueCall("{\"key1\":\"test\"}");
        call.setTargetPath("key0");
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("key0", "key1")).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallJsonListWithPath_whenExecuteEo_thenEoIsList() {
        EoRoot eo = ObjectProvider.createEo();
        final ValueCall call = new ValueCall("[1,2]");
        call.setTargetPath(S_LEVEL0);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, "0")).isEqualTo(1);
    }

    @Test
    public void call_TemplateCall_level0_content__execute__level0_1() {
        EoRoot eo = ObjectProvider.createEo();
        final String template = " @{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "[1,2]" +
                ".{}.";
        final TemplateCall call = new TemplateCall(template);
        call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, "0")).isEqualTo(1);
    }

    @Test
    public void givenTemplateWithValueCallJsonMap_whenExecute_thenEoIsMap() {
        EoRoot eo = ObjectProvider.createEo();
        final String template = " @{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "{\"level1\",\"test\"}" +
                ".{}.";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, S_LEVEL1)).isEqualTo(S_STRING);
    }

    @Test
    public void givenTemplateWithValueCallJsonMapAndLongerPath_whenExecute_thenEoIsMap() {
        EoRoot eo = ObjectProvider.createEo();
        final String template = " @{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0/level1/level2\"}" +
                "}|" +
                "{\"level3\",\"test\"}" +
                ".{}.";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3)).isEqualTo(S_STRING);
    }
}

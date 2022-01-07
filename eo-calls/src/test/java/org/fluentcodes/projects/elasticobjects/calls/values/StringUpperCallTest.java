package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.calls.values.StringUpperCall.upper;

/**
 * Tests for {@link StringUpperCall}
 *
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class StringUpperCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return StringUpperCall.class;
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
    public void givenModelCreateAndValueTest_whenExecute_thenUpperCaseReturned() {
        final ModelConfig model = ObjectProvider.findModel(StringUpperCall.class);
        final StringUpperCall call = (StringUpperCall) model.create();
        IEOScalar eo = ObjectProvider.createEo().set(S_STRING, S_LEVEL0);
        Assertions.assertThat(eo.get()).isEqualTo("test");
        Assertions.assertThat(call.execute(eo)).isEqualTo("TEST");
    }

    @Test
    public void call_TemplateCall_level0_test__execute__valueInTemplate() {
        EoRoot eo = ObjectProvider.createEo();
        final String template = "START" +
                "@{\"level0\":\"test\"}. - \n" +
                "@{\"(StringUpperCall)\":{" +
                "\"sourcePath\":\"level0\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}." +
                " END";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("START - TEST END");
    }


    @Test
    public void givenStringUpper_whenUpper_thenUpperCase() {
        Assert.assertEquals("TEST_UPDATE_ALL", upper("testUpdate-All"));
    }
}

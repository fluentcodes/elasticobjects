package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

/**
 * Created by werner.diwischek on 11.03.18.
 */
public class StringReplaceCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return StringReplaceCall.class;
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
    public void givenModelConfigPackagePath_whenExecuteCall_thenDotsAreReplaced() {
        EoRoot eo = ObjectProvider.createEo();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        eo.map(ObjectProvider.CONFIG_MAPS.findModel(StringReplaceCall.class));
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new StringReplaceCall("\\.", "/").execute(eo.getEo(PACKAGE_PATH));
        Assertions.assertThat(content).isEqualTo("org/fluentcodes/projects/elasticobjects/calls/values");
    }

    @Test
    public void call_TemplateCall_eo_StringReplaceCall_packagePath__execute__dotsAreReplaced() {
        EoRoot eo = ObjectProvider.createEo();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        eo.map(ObjectProvider.CONFIG_MAPS.findModel(StringReplaceCall.class));
        Assertions.assertThat(eo.getLog()).isEmpty();
        String content = new TemplateCall("* \n" +
                "@{\"(StringReplaceCall).\":{" +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\", " +
                "\"toReplace\"=\"\\\\.\", " +
                "\"replaceBy\"=\"/\" }" +
                "}." +
                " *")
                .execute(eo.getEo(PACKAGE_PATH));
        Assertions.assertThat(content).isEqualTo("* org/fluentcodes/projects/elasticobjects/calls/values *");
    }
}

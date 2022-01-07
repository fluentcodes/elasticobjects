package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.Call.F_KEEP_CALL;

/**
 * Created 6.8.2020
 */
public class TemplateResourceCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return TemplateResourceCall.class;
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
    public void __setKeepCall__accessOk() {
        ModelConfig modelConfig = ObjectProvider.CONFIG_MAPS.findModel(TemplateResourceCall.class.getSimpleName());
        TemplateResourceCall templateResourceCall = new TemplateResourceCall();

        modelConfig.set(F_KEEP_CALL, templateResourceCall, KeepCalls.JAVA);
        Assertions.assertThat(templateResourceCall.getKeepCall()).isEqualTo(KeepCalls.JAVA);
        Assertions.assertThat(modelConfig.get(F_KEEP_CALL, templateResourceCall)).isEqualTo(KeepCalls.JAVA);
    }
}

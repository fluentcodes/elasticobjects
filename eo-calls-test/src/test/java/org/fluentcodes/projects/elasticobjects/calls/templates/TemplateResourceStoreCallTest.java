package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.Call.F_KEEP_CALL;
import static org.fluentcodes.projects.elasticobjects.calls.files.FileReadWriteCall.TARGET_FILE_CONFIG_KEY;

/**
 * Created 21.9.2020
 */
public class TemplateResourceStoreCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return TemplateResourceStoreCall.class;
    }

    @Override
    @Test
    public void create_noEoException()  {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig()  {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig()  {
        assertBeanFromModelConfigEqualsPersisted();
    }


    @Test
    public void call__set_targetFile_model_test__accessOk() {
        ModelConfig modelConfig = ObjectProvider.CONFIG_MAPS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(TARGET_FILE_CONFIG_KEY, call, "test");
        Assertions.assertThat(call.getTargetFileConfigKey()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(TARGET_FILE_CONFIG_KEY, call)).isEqualTo("test");
    }

    @Test
    public void __setKeepCall__accessOk() {
        ModelConfig modelConfig = ObjectProvider.CONFIG_MAPS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(F_KEEP_CALL, call, KeepCalls.JAVA);
        Assertions.assertThat(call.getKeepCall()).isEqualTo(KeepCalls.JAVA);
        Assertions.assertThat(modelConfig.get(F_KEEP_CALL, call)).isEqualTo(KeepCalls.JAVA);
    }
}

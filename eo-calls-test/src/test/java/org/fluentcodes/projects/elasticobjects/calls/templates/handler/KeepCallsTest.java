package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

public class KeepCallsTest implements IModelConfigNoCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return KeepCalls.class;
    }

    @Override
    public void createThrowsEoException() {
        assertCreateThrowingException();
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

    @Test(expected = EoException.class)
    public void createByModelConfig() {
        ModelConfig modelConfig = ObjectProvider.CONFIG_MAPS
                .findModel(KeepCalls.class);
        Assertions.assertThat(modelConfig.isCreate()).isFalse();
        Assertions.assertThat(modelConfig.isScalar()).isTrue();
        Assertions.assertThat(modelConfig.isEnum()).isTrue();
        modelConfig.create();
    }

    @Test
    public void DEV_fieldBeanMap__find_keepCall__notNull() {
        FieldBean bean = new FieldFactory(ObjectProvider.CONFIG_MAPS).createBeanList()
                .get(1);
        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getModelKeys()).isEqualTo(Boolean.class.getSimpleName());
    }

    @Test
    public void ModelConfig__find_keepCall__notNull() {
        ModelConfig modelConfig = ObjectProvider.CONFIG_MAPS
                .findModel(CallImpl.class);
        Assertions.assertThat(modelConfig).isNotNull();
        FieldConfig fieldConfig = modelConfig.getField("keepCall");
        Assertions.assertThat(fieldConfig.getProperties().isNotNull());
    }
}

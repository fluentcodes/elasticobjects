package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

public class SuperExampleClassTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return SuperExampleClass.class;
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
    public void givenScopeDev_whenFindSuperExampleClass_thenExceptionThrown() {
        try {
            ModelConfig model = ProviderConfigMapsDev.CONFIG_MAPS_DEV.findModel(SuperExampleClass.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        } catch (EoException e) {

        }
    }

    @Test
    public void setValue() {
        ModelConfig config = ProviderConfigMaps.CONFIG_MAPS.findModel(SuperExampleClass.class);
        Object object = config.create();
        config.set("id", object, 1L);
        Assertions.assertThat(config.get("id", object)).isEqualTo(1L);
        Assertions.assertThat(((SuperExampleClass) object).getId()).isEqualTo(1L);

    }

}

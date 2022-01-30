package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

public class FieldExampleClassTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return FieldExampleClass.class;
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
    public void givenScopeDev_whenFindFieldExampleClass_thenExceptionThrown() {
        try {
            ModelConfig model = ObjectProviderDev.CONFIG_MAPS_DEV.findModel(FieldExampleClass.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        } catch (EoException e) {

        }
    }
}

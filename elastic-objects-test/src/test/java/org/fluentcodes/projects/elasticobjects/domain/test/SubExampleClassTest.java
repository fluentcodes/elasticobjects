package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.junit.Test;

public class SubExampleClassTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return SubExampleClass.class;
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

}

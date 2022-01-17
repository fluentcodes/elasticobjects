package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.junit.Test;

public class ExecutorCallTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return ExecutorCall.class;
    }

    @Override
    @Test
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

}

package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.junit.Test;

public class CallImplTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return CallImpl.class;
    }

    @Test
    public void createThrowsEoException() {
        assertCreateThrowingException();
    }

    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }
}

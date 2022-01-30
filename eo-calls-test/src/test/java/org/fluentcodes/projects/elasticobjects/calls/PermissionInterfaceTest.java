package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigNoCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class PermissionInterfaceTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return PermissionInterface.class;
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

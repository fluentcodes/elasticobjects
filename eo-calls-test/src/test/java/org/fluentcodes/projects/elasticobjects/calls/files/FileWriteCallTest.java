package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 23.8.2020.
 */
public class FileWriteCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return FileWriteCall.class;
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

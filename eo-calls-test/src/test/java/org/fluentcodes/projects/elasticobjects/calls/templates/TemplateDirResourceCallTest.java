package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.junit.Test;

/**
 * Created 21.9.2020
 */
public class TemplateDirResourceCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return TemplateDirResourceCall.class;
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

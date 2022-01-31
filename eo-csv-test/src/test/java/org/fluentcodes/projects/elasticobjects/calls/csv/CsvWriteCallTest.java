package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 08.10.2016.
 */
public class CsvWriteCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return CsvWriteCall.class;
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

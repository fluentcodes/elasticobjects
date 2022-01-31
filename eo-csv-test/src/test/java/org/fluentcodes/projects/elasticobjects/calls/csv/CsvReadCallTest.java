package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.junit.Test;

/**
 * These Tests will checks all CsvActions.
 * Created by Werner on 11.10.2016.
 */
public class CsvReadCallTest implements IModelConfigCreateTests {
    private static final String LIST_SIMPLE_CSV = "ListSimple.csv";

    @Override
    public Class<?> getModelConfigClass() {
        return CsvReadCall.class;
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

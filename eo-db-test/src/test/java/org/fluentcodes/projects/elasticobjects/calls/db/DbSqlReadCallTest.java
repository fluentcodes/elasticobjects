package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbSqlReadCallTest implements IModelConfigCreateTests {
    public static final String H2_MEM_BASIC_BASIC_TEST = "h2:mem:basic:AnObject";

    @Override
    public Class<?> getModelConfigClass() {
        return DbSqlReadCall.class;
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


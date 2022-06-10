package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Werner on 17.8.2020.
 */
public class DbSqlExecuteCallTest implements IModelConfigCreateTests {
    private final static String DROP = "h2:mem:basic:AnObjectDrop";
    private final static String CREATE = "h2:mem:basic:Create";

    @Override
    public Class<?> getModelConfigClass() {
        return DbSqlExecuteCall.class;
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
    public void queryAnObject() {
        DbSqlExecuteCall call = new DbSqlExecuteCall(DbConfig.H2_BASIC, CREATE);
        Assert.assertNotNull(call);
        EoRoot eo = ObjectProvider.createEo();
        call.execute(eo);
    }
}


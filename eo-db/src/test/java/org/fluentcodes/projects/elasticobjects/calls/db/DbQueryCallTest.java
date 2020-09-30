package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbQueryCallTest {
    public static final String H2_MEM_BASIC_BASIC_TEST = "h2:mem:basic:AnObject";
    public static final String H2_MEM_BASIC_SUB_TEST = "h2:mem:basic:SubTest";
    public static final String H2_MEM_BASIC_TEST_DROP = "h2:mem:basic:AnObjectDrop";
    public static final String H2_MEM_BASIC_CREATE = "h2:mem:basic:Create";


    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DbQueryCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbQueryCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(DbQueryCall.class);
    }

    // TODO make some reasonable tests afterwards
    @Ignore
    @Test
    public void queryAnObject()  {
        DbQueryCall call = new DbQueryCall(H2_MEM_BASIC_BASIC_TEST);
        Assert.assertNotNull(call);
        EO eo = ProviderRootTestScope.createEo();
        call.execute(eo);
    }
}


package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class JsonCallTest {
    @Test
    public void readModuleConfigJson()  {
        final EO eoEmpty = TestEOProvider.create();
        final JsonCall call = TestCallsProvider.createJsonCall(J_MODULE_CONFIG_JSON);
        call.read(eoEmpty);
        Assert.assertEquals(MODULE_SHORT, eoEmpty.getChild(MODULE).get(F_SHORT));
        AssertEO.compare(eoEmpty);
    }

    @Test
    public void readTargetJsonGuest_hasLog()  {
        final JsonCall call = TestCallsProvider.createJsonCall(FILE_TARGET_JSON);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_GUEST);
        call.read(eo);
        TestObjectProvider.checkLogNotEmpty(eo);
    }

    @Test
    public void readTargetJson_ok()  {
        final JsonCall call = TestCallsProvider.createJsonCall(FILE_TARGET_JSON);
        EO eo = TestEOProvider.create();
        call.read(eo);
        TestObjectProvider.checkLogEmpty(eo);
    }

    @Test
    public void readTargetJsonSuperAdmin_()  {
        final JsonCall call = TestCallsProvider.createJsonCall(FILE_TARGET_JSON);
        EO eoWrite = TestEOProvider.create();
        eoWrite.setPathValue(S0 + Path.DELIMITER + S_KEY1,S_VALUE11);
        eoWrite.setPathValue(S0 + Path.DELIMITER + S_KEY2,S_VALUE12);
        eoWrite.setPathValue(S1 + Path.DELIMITER + S_KEY1,S_VALUE21);
        eoWrite.setPathValue(S1 + Path.DELIMITER + S_KEY2,S_VALUE22);
        eoWrite.setRoles(R_SUPER_ADMIN);
        call.write(eoWrite);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_SUPER_ADMIN);
        call.read(eo);
        TestObjectProvider.checkLogEmpty(eo);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getChild(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getChild(S0).get(S_KEY1));
    }


    @Test
    public void readSourceJsonGuest_ok()  {
        final JsonCall call = TestCallsProvider.createJsonCall(FILE_SOURCE_JSON);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_GUEST);
        call.read(eo);
        TestObjectProvider.checkLogEmpty(eo);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getChild(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getChild(S0).get(S_KEY1));
    }

    @Test
    public void readSourceJsonAnonym_hasLog()  {
        final JsonCall call = TestCallsProvider.createJsonCall(FILE_SOURCE_JSON);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_ANONYM);
        call.read(eo);
        TestObjectProvider.checkLogNotEmpty(eo);
    }
}

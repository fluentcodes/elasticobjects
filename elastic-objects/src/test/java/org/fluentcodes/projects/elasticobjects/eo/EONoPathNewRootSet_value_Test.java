package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathNewRootSet_value_Test {
    private static final Logger LOG = LogManager.getLogger(EONoPathNewRootSet_value_Test.class);

    @Test
    public void withNull_ok() throws Exception {
        EOTest.setValue_ok(null);
    }

    @Test
    public void withString_ok() throws Exception {
        EOTest.setValue_ok(S_STRING);
    }

    @Test
    public void withInteger_ok() throws Exception {
        EOTest.setValue_ok(S_INTEGER);
    }

    @Test
    public void withHashMap_ok() throws Exception {
        EOTest.setValue_ok(new HashMap());
    }

    @Test
    public void withMapInteger_ok() throws Exception {
        EO eoMapInteger = EOTest.setValue_ok(MapProvider.createInteger());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eoMapInteger.get(F_TEST_INTEGER));
    }

    @Test
    public void withArrayList_ok() throws Exception {
        EOTest.setValue_ok(new ArrayList());
    }

    @Test
    public void withBT_ok() throws Exception {
        EOTest.setValue_ok(new BasicTest());
    }

    @Test
    public void withBTTestString_ok() throws Exception {
        EO eoBTWithString = EOTest.setValue_ok(BTProvider.createString());
        Assert.assertEquals(S_STRING, eoBTWithString.get(F_TEST_STRING));
        Assert.assertEquals(String.class, eoBTWithString.getChild(F_TEST_STRING).getModelClass());
    }

    @Test
    public void withST_ok() throws Exception {
        final EO root = EOTest
                .setValue_ok(STProvider.createString(), SubTest.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }

    @Test
    public void mapWithIntegerKey() throws Exception {
        TestHelper.printStartMethod();
        Map map = new HashMap();
        map.put(3, 1);
        EO adapter = TestObjectProvider.createEOBuilder()
                .set(map);
        Assert.assertEquals(new Integer(1), adapter.get(S3));
    }

    @Test
    public void mapMap_ChangesInOriginalMap_changeInEO() throws Exception {
        TestHelper.printStartMethod();
        Map map = MapProvider.createString();

        EO adapter = EOTest.setValue_ok(map);

        map.put(S_KEY1, map);
        Assert.assertEquals(S_STRING, ((Map) map.get(S_KEY1)).get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, adapter.getChild(S_KEY1).get(F_TEST_STRING));
    }

    @Test
    public void withList() throws Exception {
        EO adapter = TestObjectProvider.createEOBuilder()
                .set(Arrays.asList(S_STRING, S_INTEGER));
        Assert.assertEquals(S_STRING, adapter.get(S0));
        Assert.assertEquals(S_INTEGER, adapter.get(S1));
        Assert.assertEquals(2, adapter.keys().size());
        Assert.assertEquals(2, ((EOContainer) adapter).keysValue().size());
    }

}


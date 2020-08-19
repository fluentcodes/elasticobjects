package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class EoCompare2Test {
    @Ignore
    @Test
    public void test()  {
        final Map map = new HashMap();
        map.put("testString", "value");
        final EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(map);

        BasicTest BT = new BasicTest();
        BT.setTestString("value");
        final EO eo2 = ProviderRootTestScope.createEo();
        eo2.mapObject(BT);

        StringBuilder diff = new StringBuilder();
        eo.compare(diff, eo2);
        Assert.assertEquals("", diff.toString());
    }
}
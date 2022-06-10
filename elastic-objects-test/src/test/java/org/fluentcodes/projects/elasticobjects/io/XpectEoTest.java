package org.fluentcodes.projects.elasticobjects.io;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.projects.elasticobjects.testobjects.ForTestClass;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Some examples are from generated code delivered by a maven artifact from a project last year with gematik
 */
public class XpectEoTest {
    private static final ConfigMaps configMaps = new ConfigMaps(Scope.DEV);
    @Test
    public void testHashMap() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        XpectEoJunit4.assertStatic(map);
    }

    @Test
    public void testHashMapAsObject() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        XpectEoJunit4.assertStaticEO(map);
    }

    @Ignore("Check later")
    @Test
    public void testForTestClass() {
        ForTestClass forTest  = ForTestClass.of1();
        XpectEoJunit4.assertStatic(forTest);
    }
}

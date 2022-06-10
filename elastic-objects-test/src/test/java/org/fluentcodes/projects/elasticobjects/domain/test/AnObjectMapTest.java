package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObjectMapDevTest.AN_OBJECT_MAP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AnObjectMapTest {
    @Test
    public void findModel() {
        ModelConfig config = ObjectProvider.findModel(AN_OBJECT_MAP);
        assertEquals(AN_OBJECT_MAP, config.getModelKey());
        assertEquals(2, config.getFieldKeys().size());
    }

    @Test
    public void create() {
        ModelConfig config = ObjectProvider.findModel(AN_OBJECT_MAP);
        Map map = (Map) config.create();
        assertNotNull(map);
    }

    @Test
    public void set_myString() {
        ModelConfig config = ObjectProvider.findModel(AN_OBJECT_MAP);
        Map map = new HashMap();
        config.set(F_MY_STRING, map, "test");
        assertEquals("test", map.get(F_MY_STRING));
        assertEquals("test", config.get(F_MY_STRING, map));
    }

    @Test(expected = EoException.class)
    public void set_noField() {
        ModelConfig config = ObjectProvider.findModel(AN_OBJECT_MAP);
        Map map = new HashMap();
        config.set("noField", map, "test");
    }

    @Test(expected = EoException.class)
    public void set_myString_tooLong() {
        ModelConfig config = ObjectProvider.findModel(AN_OBJECT_MAP);
        Map map = new HashMap();
        config.set(F_MY_STRING, map, "test01234567890123456789");
    }
}

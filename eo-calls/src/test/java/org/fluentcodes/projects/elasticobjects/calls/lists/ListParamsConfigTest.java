package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.COL_KEYS;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.F_FILTER;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.LENGTH;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.ROW_END;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.ROW_HEAD;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean.ROW_START;

public class ListParamsConfigTest {
    @Test
    public void testConstructor() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ROW_HEAD, 1);
        properties.put(ROW_START, 2);
        properties.put(ROW_END, 3);
        properties.put(LENGTH, 1);
        properties.put(F_FILTER, "FILTER");
        properties.put(COL_KEYS, "A,B,C");
        ListParamsConfig config = new ListParamsConfig(properties);

        Assert.assertTrue(config.hasRowHead());
        Assert.assertTrue(config.hasRowStart());
        Assert.assertTrue(config.hasRowEnd());
        Assert.assertTrue(config.hasLength());
        Assert.assertTrue(config.hasFilter());
        Assert.assertTrue(config.hasColKeys());

        Assert.assertEquals(new Integer(1), config.getRowHead());
        Assert.assertEquals(new Integer(2), config.getRowStart());
        Assert.assertEquals(new Integer(3), config.getRowEnd());
        Assert.assertEquals(new Integer(1), config.getLength());
        Assert.assertEquals("FILTER", config.getFilter());
        Assert.assertEquals(Arrays.asList("A", "B", "C"), config.getColKeys());

    }
}

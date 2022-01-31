package org.fluentcodes.projects.elasticobjects.testitems;

import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev.createJson;
import static org.junit.Assert.assertEquals;

public class ObjectProviderDevTest {
    @Test
    public void createJson_keytest() {
        assertEquals("{\"key\":\"test\"}",
                createJson("test", "key"));
    }

    @Test
    public void createJson_key1() {
        assertEquals("{\"key\":1}",
                createJson(1, "key"));
    }

    @Test
    public void createJson_key1keytest() {
        assertEquals("{\"key1\":{\"key\":\"test\"}}",
                createJson("test","key1", "key"));
    }

    @Test
    public void createJson_key2key1keytest() {
        assertEquals("{\"key2\":{\"key1\":{\"key\":\"test\"}}}",
                createJson("test","key2", "key1", "key"));
    }

    @Test
    public void createJson_key3key2key1keytest() {
        assertEquals("{\"key3\":{\"key2\":{\"key1\":{\"key\":\"test\"}}}}",
                createJson("test","key3", "key2", "key1", "key"));
    }

    @Test
    public void createJson_key4key3key2key1keytest() {
        assertEquals("{\"key4\":{\"key3\":{\"key2\":{\"key1\":{\"key\":\"test\"}}}}}",
                createJson("test","key4", "key3", "key2", "key1", "key"));
    }
}

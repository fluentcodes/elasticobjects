package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;

import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootDev;

import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderMapJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class JsonToEoMapJsonTest {
    private static final Logger LOG = LogManager.getLogger(JsonToEoMapJsonTest.class);

    @Test
    public void testEmpty()  {
        EO eo = TestProviderRootDev.createEo();
        eo.mapObject("{}");
        Assert.assertTrue(eo.isEmpty());
    }

    @Test
    public void testOneValue()  {
        EO eo = TestProviderRootDev.createEo();
        eo.mapObject("{\"testKey\":\"testObject\"}");
        Assertions.assertThat(eo.get("testKey")).isEqualTo("testObject");
    }

    @Test
    public void testOneValueEmbedded()  {
        EO eo = TestProviderRootDev.createEo();
        eo.mapObject("{\"test1\":{\"test2\":\"testObject\"}}");
        Assertions.assertThat(eo.get("test1/test2")).isEqualTo("testObject");
    }

    @Test
    public void testTwoValues()  {
        EO eo = TestProviderRootDev.createEo();
        eo.mapObject("{\"test1\":\"testObject1\",\"test2\":\"testObject2\"}");
        Assertions.assertThat(eo.get("test1")).isEqualTo("testObject1");
        Assertions.assertThat(eo.get("test2")).isEqualTo("testObject2");
    }

    @Test
    public void testFromFile()  {
        EO eo = TestProviderMapJson.EMPTY.createMapEo();
        Assert.assertEquals(Map.class, eo.getModelClass());
    }

    @Test
    public void testFloat()  {
        EO eo = TestProviderMapJson.FLOAT.createMapEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, SAMPLE_FLOAT, eo.get(F_TEST_FLOAT));
    }

    @Test
    public void testSmall()  {
        EO eo = TestProviderMapJson.SMALL.createMapEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(F_TEST_INTEGER));
    }

    @Test
    public void testAll()  {
        EO eo = TestProviderMapJson.ALL.createMapEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(F_TEST_INTEGER));
        //AssertEO.compareJSON(eo);
    }
}

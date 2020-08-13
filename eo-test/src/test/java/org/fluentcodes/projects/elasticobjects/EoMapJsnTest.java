package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class EoMapJsnTest {
    private static final Logger LOG = LogManager.getLogger(EoMapJsnTest.class);

    @Test
    public void testEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        String valueBeforeCheckLog = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJSON(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        String value = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJSON(eo);
        EO eoFromJson = ProviderRootTestScope.createEo();
        eoFromJson.mapObject(value);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void testDouble()  {
        EO eo = TestProviderBtJson.DOUBLE_TYPED.createTestEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(BasicTest.TEST_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void givenJsonFloatTypes_thenSetValue()  {
        EO eo = TestProviderBtJson.FLOAT_TYPED.createTestEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(BasicTest.TEST_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void testSmall()  {
        EO eo = TestProviderBtJson.SMALL.createTestEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(BasicTest.TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(BasicTest.TEST_INTEGER));
    }

    @Test
    public void testAll()  {
        EO eo = TestProviderBtJson.ALL_TYPED.createTestEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(BasicTest.TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(BasicTest.TEST_INTEGER));
        //AssertEO.compareJSON(eo);
    }
}

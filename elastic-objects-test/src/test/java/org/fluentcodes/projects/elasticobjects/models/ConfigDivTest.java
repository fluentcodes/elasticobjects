package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_TEST_STRING;

/**
 * Created by Werner on 04.11.2016.
 */
public class ConfigDivTest {

    private static final Logger LOG = LogManager.getLogger(ConfigDivTest.class);


    @Test
    public void testMap()  {
        
        ModelConfig mapModel = ObjectProvider.CONFIG_MAPS.findModel(Map.class.getSimpleName());
        Assert.assertEquals(Map.class.getSimpleName(), mapModel.getModelKey());
        Map map = (Map) mapModel.create();
        Assert.assertEquals(LinkedHashMap.class, map.getClass());

        mapModel.set(S_TEST_STRING, map, S_STRING);
        Assert.assertEquals(S_STRING, map.get(S_TEST_STRING));
        Assert.assertEquals(S_STRING, mapModel.get(S_TEST_STRING, map));
        Assert.assertTrue(mapModel.isMap());
    }

    @Test(expected = EoException.class)
    public void scalarModel__setKeyValue__EoException()  {
        ModelConfig scalarModel = ObjectProvider.CONFIG_MAPS.findModel(String.class.getSimpleName());
        Assert.assertEquals(String.class.getSimpleName(), scalarModel.getModelKey());
        Assert.assertTrue(scalarModel.isScalar());

        scalarModel.create();
    }

}

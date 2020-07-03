package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 12.09.2018.
 */
public class ModelsMapMapTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsMapMapTest.class);

    private static final Class SUB_CLASS = Map.class;
    private static final String NAME = F_UNTYPED_MAP;
    private static final Models MODELS = createModels();

    private static final Models createModels() {
        try {
            return new Models(TestEOProvider.EO_CONFIGS, Map.class, SUB_CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void setNull_ok()  {
        final Models child = MODELS.createChildForSet(NAME, null, null);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void mapNull_ok()  {
        final Models child = MODELS.createChildForMap(NAME, null);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setJson_fails()  {
        try {
            MODELS.createChildForSet(NAME, TestEOProvider.createJSONToEOMapEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapJson_ok()  {
        final Models child = MODELS.createChildForMap(NAME, TestEOProvider.createJSONToEOMapEmpty());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void mapString_fails()  {
        try {
            MODELS.createChildForMap(NAME, S_STRING);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setString_fails()  {
        try {
            MODELS.createChildForSet(NAME, S_STRING, null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setMap_fails()  {
        final Models child = MODELS.createChildForSet(NAME, MapProvider.createEmpty(), null);
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void mapMap_ok()  {
        final Models child = MODELS.createChildForMap(NAME, MapProvider.create());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setList_fails()  {
        try {
            MODELS.createChildForSet(NAME, ListProvider.createEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapList_ok()  {
        final Models child = MODELS.createChildForMap(NAME, ListProvider.createEmpty());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void setBT_fails()  {
        try {
            MODELS.createChildForSet(NAME, BTProvider.createEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapBT_ok()  {
        final Models child = MODELS.createChildForMap(F_KEY, new BasicTest());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

    @Test
    public void mapST_ok()  {
        final Models child = MODELS.createChildForMap(F_KEY, new SubTest());
        Assert.assertEquals(SUB_CLASS, child.getModelClass());
    }

}

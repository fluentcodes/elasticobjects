package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_INTEGER;

public class EOPathNewRootSet_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathNewRootSet_models_Test.class);

    @Test
    public void setIntegerNull_ok()  {
        EOTest.setValue_ok(Integer.class);
    }

    @Test
    public void setInteger_ok()  {
        EOTest.set_ok(F_TEST_INTEGER, Integer.class);
    }
}


package org.fluentcodes.projects.elasticobjects.testitems;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.junit.Assert;

/**
 * Created by Werner on 18.11.2021.
 */
public interface IModelConfigNoCreateTests extends IModelConfigTests {
    final Logger LOG = LogManager.getLogger(IModelConfigTests.class);

    void createThrowsEoException();

    default void assertCreateThrowingException() {
        try {
            getModelConfig().create();
            Assert.fail("Create should throw exception for " + getModelConfig().getModelKey());
        } catch (EoException e) {
            LOG.debug("Expected Exception for {}", getModelConfig().getModelKey());
        }
    }
}

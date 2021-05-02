package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class ModelTemplateCallTest {

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(ModelTemplateCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ModelTemplateCall.class);
    }
}

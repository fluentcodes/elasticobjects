package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class TemplateCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(TemplateCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(TemplateCall.class);
    }
}
package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.XlsxConfig;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 04.11.2016.
 */
public class XlsxConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(XlsxConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(XlsxConfig.class);
    }

    @Test
    public void resolveModel()  {
        ModelConfigChecks.resolve(XlsxConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(FileConfig.class);
    }

    // TODO check maven problems within 0.5.0
    @Ignore
    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(FileConfig.class);
    }

}

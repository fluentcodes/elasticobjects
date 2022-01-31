package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL3;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * Created by Werner on 22.03.2017.
 */
public class EOFilterPathsTest {

    @Test
    public void keyPath() {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.set(S_STRING, S_LEVEL3);
        eo.set(S_STRING, S_LEVEL0, S_LEVEL1, S_KEY0);
        eo.set(S_STRING, S_LEVEL0, S_LEVEL2, S_KEY0);
        eo.set(S_STRING, S_LEVEL0, S_LEVEL2, S_KEY1);

        List<String> keys = eo.filterPaths(S_LEVEL3);
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));
    }

}

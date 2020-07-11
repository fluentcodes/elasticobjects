package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


public class EoSetEmptyTest {
    private static final Logger LOG = LogManager.getLogger(EoSetEmptyTest.class);

    @Test
    public void level0_isMap()  {
        final EO eo = TestProviderRootDev.createEo();
        eo.setEmpty(S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
    }
    @Test
    public void level0WithBT_isBT()  {
        final EO eo = TestProviderRootTest.createEo();
        eo.setEmpty("(BasicTest)" + S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(BasicTest.class);
    }

    @Test
    public void level0OverwriteWithBT_isMap()  {
        final EO eo = TestProviderRootTest.createEo();
        eo.setEmpty(S_LEVEL0);
        eo.setEmpty("(BasicTest)" + S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void longPathWithBT_isBT()  {
        final String path = Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2, "(BasicTest)" + S_LEVEL3);
        final EO eo = TestProviderRootTest.createEo();
        eo.setPathValue(path);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(path).getModelClass()).isEqualTo(BasicTest.class);
    }

    @Test
    public void longPath1AndLongPath2PartlyOverlapping_bothObjectsRemain()  {
        final String path1 = Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL2,  S_LEVEL3);
        final String path2 = Path.ofs(S_LEVEL0, S_LEVEL1, S_LEVEL4,  S_LEVEL5);
        final EO eo = TestProviderRootTest.createEo();
        eo.setPathValue(path1);
        eo.setPathValue(path2);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(path1)).isNotNull();
        Assertions.assertThat(eo.getEo(path2)).isNotNull();
    }
}
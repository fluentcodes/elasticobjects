package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

/**
 * Created by werner.diwischek on 14.8.2020.
 */
public class EoCallsTest {

    @Test
    public void givenTest_thenCallsAreEmpty()  {
        EO eo = ProviderConfigMaps.createEo();
        Assertions.assertThat(eo.hasCalls()).isEqualTo(false);
        Assertions.assertThat(eo.size()).isEqualTo(0);
    }
}

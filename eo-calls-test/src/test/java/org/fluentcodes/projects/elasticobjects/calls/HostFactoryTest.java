package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.Map;

public class HostFactoryTest {

    @Test
    public void TEST_hostBeanMap__find_localhost__notNull() {
        HostBean bean = new HostFactory(ObjectProvider.CONFIG_MAPS)
                .createBeanList()
                .get(0);
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void TEST_hostBeanMap_createConfigMap__find_localhost__notNull() {
        Map<String, HostConfig> hostConfigMap = new HostFactory(ObjectProvider.CONFIG_MAPS)
                .createConfigMap();
        Assertions.assertThat(hostConfigMap.containsKey("localhost")).isTrue();
    }



}

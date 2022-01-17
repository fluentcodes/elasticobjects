package org.fluentcodes.projects.elasticobjects.io;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.HostBean;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;

public class IOClasspathEoFlatMapTest {
    @Test
    public void TEST_HostConfigHostBeanMap() {
        IOClasspathEOFlatList<HostBean> io = new IOClasspathEOFlatList<>(CONFIG_MAPS, "HostConfig.json", HostBean.class);
        List<HostBean> hostMap = io.read();
        Assertions.assertThat(0).isNotNull();
        Assertions.assertThat(hostMap.get(0)).isNotNull();
        Assertions.assertThat(hostMap.get(0).getClass()).isEqualTo(HostBean.class);
    }

}

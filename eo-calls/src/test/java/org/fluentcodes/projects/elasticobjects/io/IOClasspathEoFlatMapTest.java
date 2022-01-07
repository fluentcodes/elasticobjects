package org.fluentcodes.projects.elasticobjects.io;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.HostBean;
import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;

public class IOClasspathEoFlatMapTest {
    @Test
    public void TEST_HostConfigHostBeanMap() {
        IOClasspathEOFlatMap<HostBean> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS, "HostConfig.json", HostBean.class);
        Map<String, HostBean> hostMap = io.read();
        Assertions.assertThat(hostMap).isNotNull();
        Assertions.assertThat(hostMap.get("localhost")).isNotNull();
        Assertions.assertThat(hostMap.get("localhost").getClass()).isEqualTo(HostBean.class);
    }

    @Test
    public void TEST_FileConfigFileBeanMap() {
        IOClasspathEOFlatMap<FileBean> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS, "FileConfig.json", FileBean.class);
        Map<String, FileBean> fileMap = io.read();
        Assertions.assertThat(fileMap).isNotNull();
        Assertions.assertThat(fileMap.get("target.csv")).isNotNull();
        Assertions.assertThat(fileMap.get("target.csv").getClass()).isEqualTo(FileBean.class);
    }
}

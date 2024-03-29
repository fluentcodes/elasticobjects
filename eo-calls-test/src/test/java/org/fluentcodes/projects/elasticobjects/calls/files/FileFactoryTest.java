package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

public class FileFactoryTest {

    @Test
    public void fileBeanMap__find_AnObjectCsv__notNull() {
        FileBean bean = new FileFactory(ObjectProvider.CONFIG_MAPS).createBeanList()
            .get(1);
        Assertions.assertThat(bean).isNotNull();
    }

}

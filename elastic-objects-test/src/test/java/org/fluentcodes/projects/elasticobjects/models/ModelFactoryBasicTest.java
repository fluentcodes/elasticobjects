package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.Map;

public class ModelFactoryBasicTest {

    @Test
    public void createBeanMap__get_Map__notNull() {
        ModelBean bean = new ModelFactoryBasic(ObjectProviderDev.CONFIG_MAPS_DEV).createBeanMap()
                .get(Map.class.getSimpleName());
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void createConfigMap__get_Map__notNull() {
        ModelInterface config = new ModelFactoryBasic(ObjectProvider.CONFIG_MAPS).createConfigMap()
                .get(Map.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

    @Test
    public void createImmutableConfig__get_Map__notNull() {
        ModelInterface config = (ModelInterface) new ModelFactoryBasic(ObjectProviderDev.CONFIG_MAPS_DEV).createImmutableConfig()
                .get(Map.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

}


package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObjectTest;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

public class EoConfigsCacheTest {

    /**
     * {@link AnObject} - {@link AnObjectTest}
     */
    @Test
    public void DEV__findModel_AnObject__exception()  {
        Assertions
                .assertThatThrownBy(()->{
                    ObjectProviderDev.CONFIG_MAPS_DEV.findModel(AnObject.class);})
                .hasMessageContaining("Could not find config key 'AnObject' within 'ModelConfig'!")
                .isInstanceOf(EoException.class);
    }

    @Test
    public void TEST__findModel__notNull()  {
        Assertions.assertThat(ObjectProvider.CONFIG_MAPS.findModel(AnObject.class)).isNotNull();
    }
}

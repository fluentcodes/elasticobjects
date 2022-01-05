package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;

/**
 * Created by Werner on 17.11.2021.
 */
public interface IModelConfigTests {

    Class<?> getModelConfigClass();

    void compareModelConfig();

    void compareBeanFromModelConfig();

    default ModelConfig getModelConfig() {
        return ProviderConfigMaps.CONFIG_MAPS.findModel(getModelConfigClass());
    }

    default void assertModelConfigEqualsPersisted() {
        XpectStringJunit4.assertStatic(getModelConfig().toString());
    }

    default void assertBeanFromModelConfigEqualsPersisted() {
        ModelBean modelBean = new ModelBean(getModelConfig());
        XpectEoJunit4.assertStaticEO(
                ProviderConfigMaps.createEo(modelBean)
        );
    }

}

package org.fluentcodes.projects.elasticobjects.testitems;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.CONFIG_MAPS;

/**
 * Created by Werner on 17.11.2021.
 */
public interface IModelConfigTests {

    Class<?> getModelConfigClass();

    void compareModelConfig();

    void compareBeanFromModelConfig();

    default ModelConfig getModelConfig() {
        return CONFIG_MAPS.findModel(getModelConfigClass());
    }

    default void assertModelConfigEqualsPersisted() {
        ModelConfig config = getModelConfig();
        EoRoot cloneMap = EoRoot.ofClass(CONFIG_MAPS, Map.class);
        cloneMap.setSerializationType(JSONSerializationType.STANDARD);
        cloneMap.map(config);
        XpectEoJunit4.assertStatic(cloneMap);
    }

    default void assertBeanFromModelConfigEqualsPersisted() {
        ModelBean modelBean = new ModelBean(getModelConfig());
        XpectEoJunit4.assertStaticEO(
                ObjectProvider.createEo(modelBean)
        );
    }

}

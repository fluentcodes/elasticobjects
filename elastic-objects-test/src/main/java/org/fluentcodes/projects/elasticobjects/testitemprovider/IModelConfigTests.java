package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;

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
        EoRoot cloneMap = EoRoot.ofClass(CONFIG_MAPS, Map.class);
        cloneMap.setSerializationType(JSONSerializationType.STANDARD);
        cloneMap.map(getModelConfig());
        XpectEoJunit4.assertStatic(cloneMap);
    }

    default void assertBeanFromModelConfigEqualsPersisted() {
        ModelBean modelBean = new ModelBean(getModelConfig());
        XpectEoJunit4.assertStaticEO(
                ObjectProvider.createEo(modelBean)
        );
    }

}

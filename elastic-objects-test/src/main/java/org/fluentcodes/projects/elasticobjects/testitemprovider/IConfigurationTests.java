package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;

/**
 * Created by Werner on 17.11.2021.
 */
public interface IConfigurationTests extends IModelConfigNoCreateTests {
    default void assertConfigBeanEqualsPersisted(final String configKey)  {
        Config selectedConfig = (Config) ObjectProvider.
                CONFIG_MAPS.
                find((Class<? extends Config>)getModelConfigClass(), configKey);

        XpectStringJunit4.assertStatic(selectedConfig.toString());
    }

    default void assertLoadedConfigurationsEqualsPersisted()  {
        String configurationsAsString = ObjectProvider.
                CONFIG_MAPS.
                toString((Class<? extends Config>)getModelConfigClass());

        XpectStringJunit4.assertStatic(configurationsAsString);
    }
}

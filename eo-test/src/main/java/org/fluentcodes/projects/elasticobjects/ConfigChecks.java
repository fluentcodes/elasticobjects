package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.tools.xpect.XpectEo;

import java.util.List;
import java.util.Set;

public class ConfigChecks {

    public static void resolveConfigs(final Class<? extends Config> configClass)  {
        Set<String> keys = ProviderRootTest.EO_CONFIGS.getConfigKeys(configClass);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            Config config = (Config) ProviderRootTest.EO_CONFIGS.find(configClass, key);
            Assertions.assertThat(config).isNotNull();
            config.resolve();
        }
    }

    public static void compareConfigurations(final Class<? extends Config> configClass) {
        ConfigCall call = new ConfigCall(configClass,".*");
        EO eo = ProviderRootTest.createEo();
        List result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo(eo.getConfigsCache()).compareAsString(result);
    }

    public static void resolveConfigEntries(Class<? extends Config> configClass)  {
        Set<String> keys = ProviderRootTest.EO_CONFIGS.getConfigKeys(configClass);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            Config config = (Config) ProviderRootTest.EO_CONFIGS.find(configClass, key);
            Assertions.assertThat(config).isNotNull();
            config.resolve();
        }
    }
}

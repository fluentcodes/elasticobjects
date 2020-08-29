package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonConfig;
import org.fluentcodes.projects.elasticobjects.calls.lists.ScsConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@Configuration
public class EOConfigCached {
    @Value("${elasticobjects.scope:QS}")
    String scope;

    /**
     * Initialize the configs cache object with the json config files in the classpath.
     * @return An initaliazed ConfigsCache object
     * @throws Exception on misconfiguration of the json config files in the classpath.
     */
    @Bean
    public EOConfigsCache createProvider() throws Exception {
        EOConfigsCache provider = new EOConfigsCache(Scope.valueOf(scope), true);
        provider.getKeys(HostConfig.class);
        provider.getKeys(FileConfig.class);
        provider.getKeys(JsonConfig.class);
        provider.getKeys(ScsConfig.class);
        provider.getKeys(TemplateConfig.class);
        //FileUtil.writeFile("src/main/resources/static/eoConfigurations.json", provider.toString());
        return provider;
    }
}
package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created 8.9.2020
 */
@Ignore
public class ConfigAsFlatListCallCheck {

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(ConfigAsFlatListCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ConfigAsFlatListCall.class);
    }


    @Test
    public void call_configType_ModelConfig__execute_xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall().setConfigType(ModelConfig.class.getSimpleName());
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_configType_FieldConfig__execute_xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FieldConfig.class.getSimpleName())
                .setFieldKeys("naturalId","modelKeys","description","module","subModule","fieldKey","scope",
                        "dbFieldParams","eoFieldParams","viewFieldParams");
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }
}

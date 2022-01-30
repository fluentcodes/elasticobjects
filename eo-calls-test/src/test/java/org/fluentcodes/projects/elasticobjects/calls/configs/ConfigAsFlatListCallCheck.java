package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created 8.9.2020
 */
public class ConfigAsFlatListCallCheck implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ConfigAsFlatListCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Ignore
    @Test
    public void call_configType_ModelConfig__execute_xpected() {
        final EoRoot eo = ObjectProvider.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall().setConfigType(ModelConfig.class.getSimpleName());
        String result = call.execute(eo);
        XpectStringJunit4.assertStatic(result);
    }

    @Ignore("Check later")
    @Test
    public void call_configType_FieldConfig__execute_xpected() {
        final EoRoot eo = ObjectProvider.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FieldConfig.class.getSimpleName())
                .setFieldKeys("naturalId", "modelKeys", "description", "module", "subModule", "fieldKey", "scope",
                        "dbFieldParams", "eoFieldParams", "viewFieldParams");
        String result = call.execute(eo);
        XpectStringJunit4.assertStatic(result);
    }
}

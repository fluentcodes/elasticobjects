package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.List;

public class ConfigTypesCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ConfigTypesCall.class;
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

    @Test
    public void givenCall_whenExecute_thenResultIsOrderedList() {
        ConfigTypesCall call = new ConfigTypesCall();
        EoRoot eo = ObjectProvider.createEo();
        List<String> result = (List<String>) call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
    }
}

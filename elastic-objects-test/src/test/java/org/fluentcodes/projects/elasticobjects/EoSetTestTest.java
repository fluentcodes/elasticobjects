package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;

public class EoSetTestTest {
    @Test
    public void TEST_AnObject__set_key_value__exception()  {
        final EoRoot eo = ProviderConfigMaps.createEo(new AnObject());
        Assertions.assertThatThrownBy(
                ()->{ eo.set("value", "key");}
        )
                .hasMessageContaining("No fieldConfig 'key' defined in model '" + AnObject.class.getSimpleName() + "' ! ");
    }

    @Test
    public void TEST_AnObject__set_myString_AnObject__exception()  {
        final EoRoot eo = ProviderConfigMaps.createEo(new AnObject());
        Assertions
                .assertThatThrownBy(
                        ()->{eo.set(new AnObject(), AnObject.MY_STRING);}
                )
                .hasMessageContaining("Mismatch for Models: Expected is String but the other model is AnObject");
    }

    @Test
    public void TEST__set_key_AnObject__getModelClass_key_AnObject()  {
        final EoRoot eo = ProviderConfigMaps.createEo();
        eo.set(new AnObject(), "key");
        Assertions.assertThat(eo.getEo("key").getModelClass()).isEqualTo(AnObject.class);
    }
}


package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EoSetTestTest {
    @Test
    public void TEST_AnObject__set_key_value__exception()  {
        final EoRoot eo = ObjectProvider.createEo(new AnObject());
        Assertions.assertThatThrownBy(
                ()->{ eo.set("value", "key");}
        )
                .hasMessageContaining("No fieldConfig 'key' defined in model '" + AnObject.class.getSimpleName() + "' ! ");
    }

    @Test
    public void TEST_AnObject__set_myString_AnObject__exception()  {
        final EoRoot eo = ObjectProvider.createEo(new AnObject());
        Assertions
                .assertThatThrownBy(
                        ()->{eo.set(new AnObject(), AnObject.F_MY_STRING);}
                )
                .hasMessageContaining("Mismatch for Models: Expected is String but the other model is AnObject");
    }

    @Test
    public void TEST__set_key_AnObject__getModelClass_key_AnObject()  {
        final EoRoot eo = ObjectProvider.createEo();
        eo.set(new AnObject(), "key");
        Assertions.assertThat(eo.getEo("key").getModelClass()).isEqualTo(AnObject.class);
    }
}


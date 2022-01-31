package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;

public class EoDevGetTest {

    @Test(expected = EoException.class)
    public void __get_path__exception_thrown() {
        EoRoot eo = ObjectProviderDev.createEo();
        eo.get(S_LEVEL0);
    }

    @Test(expected = EoException.class)
    public void __map_put_key_value__eo_get_key_value() {
        final EoRoot eo = ObjectProviderDev.createEo();
        ((Map) eo.get()).put("key", "value");
        eo.get("key");
    }

    @Test
    public void eo_set_key_value__map_put_key_value2___eo_get_key_value2() {
        final EoRoot eo = ObjectProviderDev.createEo();
        eo.set("value", "key");
        ((Map) eo.get()).put("key", "value2");
        Assertions.assertThat(eo.get("key")).isEqualTo("value2");
    }
}


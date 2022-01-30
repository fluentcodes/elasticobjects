package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoSetParentValueTest {

    @Test
    public void DEV__null__exception() {
        final EoRoot eo = ObjectProviderDev.createEo();
        Assertions.assertThatThrownBy(() -> {
            eo.setParentValue(null);
        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Root has no parent!");
    }

    @Test
    public void DEV_root__exception() {
        final EoRoot eo = ObjectProviderDev.createEo();
        Assertions.assertThatThrownBy(() -> {
            ((EoChild) eo).setParentValue("value");
        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Root has no parent!");
    }


    @Test
    public void givenDevString_whenSetOtherString_thenIsChanged() {
        final EoRoot root = ObjectProviderDev.createEo();
        final EOInterfaceScalar childEo = root.set("value", "key");
        Assertions.assertThat(childEo.isChanged()).isFalse();
        childEo.set("valueOther");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(childEo.get()).isEqualTo("valueOther");
        Assertions.assertThat(childEo.isChanged()).isTrue();
    }

}



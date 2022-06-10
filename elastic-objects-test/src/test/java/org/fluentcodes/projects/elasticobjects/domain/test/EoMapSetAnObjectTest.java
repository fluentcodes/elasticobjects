package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;


public class EoMapSetAnObjectTest {
    /**
     * Basic Wiki Example
     */
    @Test
    public void givenTest_whenSetAnObjectWithTestStringOnExistingModelMap_thenModelIsMap()  {
        final EoRoot eo = ObjectProvider.createEo();
        AnObject bt = new AnObject()
                .setMyString("value");
        eo.set(bt, "level0");
        assertThat(eo.get("level0/" + AnObject.F_MY_STRING)).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(AnObject.class);
    }


    @Test
    public void givenTest_whenSetAnObjectOnExistingModelMap_thenModelIsMap()  {
        final EoRoot eo = ObjectProvider.createEo();
        eo.createChild(S_LEVEL0);
        eo.set(new AnObject(), S_LEVEL0);
        assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        assertThat(eo.getLog()).isEmpty();
    }
}

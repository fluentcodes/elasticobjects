package org.fluentcodes.projects.elasticobjects.domain.test;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_FLOAT;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_FLOAT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_INT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObjectFromJsonTest.createAnObjectEo;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class EoAnObjectJsonTest {

    @Test
    public void testFloat()  {
        EoRoot eo = createAnObjectEo("{\"(Float)myFloat\": 1.1}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(MY_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void givenJsonSmallType_thenSetValues()  {
        EoRoot eo = createAnObjectEo("{\"(String)myString\": \"test\", \"(Integer)myInt\": 1}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void testAll()  {
        EoRoot eo = createAnObjectEo(AnObjectFromJsonTest.ALL_TYPED);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(MY_INT)).isEqualTo((S_INTEGER));
    }
}

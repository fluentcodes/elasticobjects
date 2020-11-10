package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;


public class AnObjectTest {
    @Test
    public void create() {
        AnObject anObject = new AnObject();
        Assertions.assertThat(anObject).isNotNull();
    }
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(AnObject.class);
    }
}
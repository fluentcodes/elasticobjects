package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

public class SystemValueHandlerTest {
    @Test
    public void tmpExists() {
        String replace = "§{TMP}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).doesNotContain("Could not find env");
    }

    @Test
    public void nonsenseNotFoundMessage() {
        String replace = "§{nonsense}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).contains("Could not find system");
    }

    @Test
    public void nonsenseDefault() {
        String replace = "§{nonsense|>default}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("default");
    }

}

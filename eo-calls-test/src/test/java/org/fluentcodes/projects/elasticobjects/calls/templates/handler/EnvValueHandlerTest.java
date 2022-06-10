package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class EnvValueHandlerTest {
    @Test
    public void tmpExists() {
        Map<String, String> envMap = System.getenv();
        for (Map.Entry<String, String> env: envMap.entrySet()) {
            System.out.println(env.getKey() + "=" + env.getValue());
        }
        String replace = "%{USER}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).doesNotContain("Could not find env");
    }

    @Test
    public void nonsenseNotFoundMessage() {
        String replace = "%{nonsense}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).contains("Could not find env");
    }

    @Test
    public void nonsenseDefault() {
        String replace = "%{nonsense|>default}.";
        String result = new Parser(replace).parse();
        Assert.assertNotNull(result);
        Assertions.assertThat(result).isEqualTo("default");
    }

}

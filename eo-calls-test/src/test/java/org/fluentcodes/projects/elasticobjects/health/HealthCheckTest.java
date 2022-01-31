package org.fluentcodes.projects.elasticobjects.health;

import org.junit.Assert;
import org.junit.Test;

public class HealthCheckTest {
    @Test
    public void checkUnusedFields() {
        Assert.assertTrue(HealthCheck.checkUnusedFields().isEmpty());
    }
}

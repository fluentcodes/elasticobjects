package org.fluentcodes.projects.elasticobjects.paths;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PathElementTest {
    @Test
    public void testWithoutModel() {
        PathElement pathElement = new PathElement("test");
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertFalse(pathElement.hasModelArray());
    }

    @Test
    public void emptyModel____noModel() {
        PathElement pathElement = new PathElement("()test");
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertFalse(pathElement.hasModelArray());
    }

    @Test
    public void _logLevel____LogLevel() {
        PathElement pathElement = new PathElement(PathElement.LOG_LEVEL);
        Assert.assertEquals(PathElement.LOG_LEVEL, pathElement.getKey());
        Assertions.assertThat(Arrays.stream(pathElement.getModelsArray()).collect(Collectors.joining(",")))
                .isEqualTo("LogLevel");
    }

    @Test
    public void _call____List() {
        PathElement pathElement = new PathElement(PathElement.CALLS);
        Assert.assertEquals(PathElement.CALLS, pathElement.getKey());
        Assertions.assertThat(Arrays.stream(pathElement.getModelsArray()).collect(Collectors.joining(",")))
                .isEqualTo("List");
    }

    @Test
    public void testWithOneModel() {
        PathElement pathElement = new PathElement("(nonsense)test");
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertTrue(pathElement.hasModelArray());
        Assert.assertEquals(1, pathElement.getModelsArray().length);
    }

    @Test
    public void testWithTwoModel() {
        final String pathElementAsString = "(List," + AnObject.class.getSimpleName() + ")test";
        PathElement pathElement = new PathElement(pathElementAsString);
        Assert.assertEquals("test", pathElement.getKey());
        Assert.assertTrue(pathElement.hasModelArray());
        Assert.assertEquals(2, pathElement.getModelsArray().length);
        Assertions.assertThat(pathElement.toString()).isEqualTo(pathElementAsString);
    }
}

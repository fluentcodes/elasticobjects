package org.fluentcodes.projects.elasticobjects.domain.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ASubObjectSetTest {

    @Test
    public void givenBean_whenSetTestString_thenOk() {
        ASubObject test = new ASubObject();
        test.setMyString("x");
        Assert.assertEquals("x", test.getMyString());
    }
}

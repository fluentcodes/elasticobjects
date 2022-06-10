package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class ConditionTest {

    public static final Pattern ifPattern = Pattern.compile("[\\s]*([^\\s]*?)[\\s]+(eq|ne|equals|le|ge|notEquals|like|notLike|in)[\\s]+(.*)");

    static void patternTest(String condition) {

        Matcher matcher = ifPattern.matcher(condition);
        if (matcher.find()) {
            int i = matcher.groupCount();
            Assert.assertEquals(3, i);
            Assert.assertEquals(AnObject.F_MY_STRING, matcher.group(1));
            Assert.assertEquals(Condition.EQ, matcher.group(2));
            Assert.assertEquals("test", matcher.group(3));
        }
    }

    @Test
    public void simple() {
        ConditionTest.patternTest(AnObject.F_MY_STRING + " eq test");
    }

    @Test
    public void simpleLeadingSpace() {
        ConditionTest.patternTest(" " + AnObject.F_MY_STRING + " eq test");
    }

    @Test
    public void simpleMultipleSpacesBeforeOperator() {
        ConditionTest.patternTest(AnObject.F_MY_STRING + "   eq test");
    }

    @Test
    public void simpleMultipleSpacesAfterOperator() {
        ConditionTest.patternTest(AnObject.F_MY_STRING + " eq     test");
    }


    public void simpleMultipleSpacesAfterValue() {
        ConditionTest.patternTest("key eq value   ");
    }


}

package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * Created by Werner on 14.04.2017.
 */
public class DivReflectionTest {
    private static final String METHOD_SET_TEST = "setTest";
    private static final String METHOD_SET_MY_OBJECT = "setMyObject";

    @Test
    public void callObjectClass() {
        TestObjectClass object = new TestObjectClass();
        object.setTest(S_STRING);
        Assert.assertEquals(S_STRING, object.getTest());
    }

    @Test
    public void callObjectSetter()  throws Exception{
        TestObjectClass object = new TestObjectClass();
        Method setter = TestObjectClass.class.getMethod(METHOD_SET_TEST, Object.class);
        setter.invoke(object, S_STRING);
        Assert.assertEquals(S_STRING, object.getTest());
    }

    @Test
    public void testObjectClass__getMethodsetTest__exception() {
        Assertions.assertThatThrownBy(()->{
            TestObjectClass.class.getMethod(METHOD_SET_TEST, null);})
                .isInstanceOf(NoSuchMethodException.class);
    }


    @Test
    public void AnObject__setMyObject_with_reflection__$() throws Exception {
        AnObject object = new AnObject();

        object.setMyObject(S1);
        Assert.assertEquals(S1, object.getMyObject());

        Method setter = AnObject.class.getMethod(METHOD_SET_MY_OBJECT, Object.class);
        setter.invoke(object, S_STRING);
        Assert.assertEquals(S_STRING, object.getMyObject());
    }

    private class TestObjectClass {
        private Object test;

        public Object getTest() {
            return test;
        }

        public void setTest(final Object test) {
            this.test = test;
        }
    }
}

package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_TEST_STRING;

/**
 * Created by Werner on 23.05.2016.
 */
public class EoRemoveTest {

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void givenAnObject_thenRemoved() {
        EoRoot root = ObjectProvider.createEo(new AnObject());
        root.set(S_STRING, AnObject.F_MY_STRING);
        Assert.assertEquals(1, (root).size());

        root.remove(AnObject.F_MY_STRING);
        Assert.assertEquals(0, (root).size());
    }

    @Test(expected = EoException.class)
    public void givenBtEmpty_WhenRemove_thenExceptionThrown() {
        EoRoot root = ObjectProvider.createEo(AnObject.class);
        root.remove(AnObject.F_MY_STRING);
    }

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void givenMap_thenRemoved() {
        EoRoot root = ObjectProvider.createEo();
        root.set(S_STRING, S_TEST_STRING);
        Assert.assertEquals(1, root.size());
        Assert.assertEquals(S_STRING, root.get(S_TEST_STRING));
        EO child = root.remove(S_TEST_STRING);
        Assert.assertEquals(0, root.size());
        Assert.assertEquals(0, root.sizeEo());
        Assert.assertEquals(0, child.size());
        Assert.assertEquals(0, child.sizeEo());
    }

    @Test(expected = EoException.class)
    public void givenMapEmpty_WhenRemove_thenExceptionThrown() {
        EoRoot root = ObjectProviderDev.createEo(Map.class);
        root.remove("test");
    }

    @Test
    public void givenList_thenRemoved() {
        EoRoot root = ObjectProvider.createEo(new ArrayList<>());
        root.set(S_STRING, S0);
        Assert.assertEquals(1, root.size());
        root.remove(S0);
        Assert.assertEquals(0, root.size());
    }

    @Test(expected = EoException.class)
    public void givenListEmpty_WhenRemove_thenExceptionThrown() {
        EoRoot root = ObjectProviderDev.createEo(new ArrayList<>());
        root.remove(S0);
    }
}

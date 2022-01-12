package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
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

    @Test
    public void givenBtEmpty_WhenRemove_thenExceptionThrown() {
        EoRoot root = ObjectProvider.createEo(AnObject.class);
        Assertions
                .assertThatThrownBy(() -> {
                    root.remove(AnObject.F_MY_STRING);
                })
                .hasMessage("Could not remove entry '" + AnObject.F_MY_STRING + "' because it is not set in '" + AnObject.class.getSimpleName() + "'");
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
        root.remove(S_TEST_STRING);
        Assert.assertEquals(0, root.size());

    }

    @Test
    public void givenMapEmpty_WhenRemove_thenExceptionThrown() {
        EoRoot root = ObjectProviderDev.createEo(Map.class);
        Assertions
                .assertThatThrownBy(() -> {
                    root.remove("test");
                })
                .hasMessage("Could not remove entry 'test' because it is not set in 'Map'");
    }

    @Test
    public void givenList_thenRemoved() {
        EoRoot root = ObjectProvider.createEo(new ArrayList<>());
        root.set(S_STRING, S0);
        Assert.assertEquals(1, root.size());
        root.remove(S0);
        Assert.assertEquals(0, root.size());
    }

    @Test
    public void givenListEmpty_WhenRemove_thenExceptionThrown() {
        EoRoot root = ObjectProviderDev.createEo(new ArrayList<>());
        Assertions
                .assertThatThrownBy(() -> {
                    root.remove(S0);
                })
                .hasMessage("Could not remove entry '0' because it is not set in 'ArrayList'");
    }
}

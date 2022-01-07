package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_TEST_STRING;

/**
 * Created by Werner on 11.07.2020.
 */
public class EoOverwriteTest {

    @Test
    public void givenAnObject_whenOverwriteWithMap_isMap()  {
        AnObject bt = new AnObject();
        bt.setMyString(S_TEST_STRING);
        EoChild child = (EoChild) ObjectProvider.createEo(new LinkedHashMap<>())
                .set(bt, S_LEVEL0,S_LEVEL1,S_LEVEL2);

        EoRoot root = child.getRoot();
        Assert.assertEquals(AnObject.class, root.getEo(S_LEVEL0,S_LEVEL1,S_LEVEL2).getModelClass());
        Assert.assertEquals(S_TEST_STRING, root.get(S_LEVEL0,S_LEVEL1,S_LEVEL2, AnObject.MY_STRING));

        Map map = new HashMap();
        map.put(S_KEY1, S_STRING);
        root.overWrite( map, S_LEVEL0,S_LEVEL1,S_LEVEL2);

        Assert.assertEquals(HashMap.class, root.getEo(S_LEVEL0,S_LEVEL1,S_LEVEL2).getModelClass());
        Assert.assertEquals(S_STRING, root.get(S_LEVEL0,S_LEVEL1,S_LEVEL2, S_KEY1));
    }
}

package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 22.4.2017
 */
public class ListMapperTest {
    private static final Logger LOG = LogManager.getLogger(ListMapperTest.class);
    private static final Map<String, Object> LIST_ROW_MAPPER = createListMapperMap();

    /*@Test
    public void init()  {
        ListMapper rowMapper = new ListMapper(LIST_ROW_MAPPER);
        Assert.assertEquals(F_COL_KEYS, rowMapper.getColKeys().get(0));
        Assert.assertEquals(S_BOOLEAN, rowMapper.isDoMap());
        Assert.assertEquals(S_BOOLEAN, rowMapper.isIgnoreHeader());
        Assert.assertEquals(join(CON_COMMA, List.class.getSimpleName(), Map.class.getSimpleName()), rowMapper.getModelKeys());
        Assert.assertEquals(Path.DELIMITER + F_PATH_PATTERN, rowMapper.getPathPattern().getSerialized());
    }

    @Test
    public void checkList()  {
        List row = List.of(S_STRING, S_INTEGER, S_BOOLEAN);
        ListMapper rowMapper = new ListMapper();
        EO eo = ProviderRootTest.createEo();
        rowMapper.createRow(eo, row);
        Assert.assertEquals(S_STRING, eo.get(S0));
        Assert.assertEquals(S_INTEGER, eo.get(S1));
        Assert.assertEquals(S_BOOLEAN, eo.get(S2));
    }

    @Test
    public void checkListWithColKeys()  {
        List row = List.of(S_STRING, S_INTEGER, S_BOOLEAN);
        Map map = EO_STATIC.toMap(F_COL_KEYS, join(CON_COMMA, S0, S1, S2));
        ListMapper rowMapper = new ListMapper(map);
        EO adapter = ProviderRootTest.createEo();
        rowMapper.createRow(adapter, row);
        Assert.assertEquals(S_STRING, adapter.get(S0));
        Assert.assertEquals(S_INTEGER, adapter.get(S1));
        Assert.assertEquals(S_BOOLEAN, adapter.get(S2));
    }*/

}

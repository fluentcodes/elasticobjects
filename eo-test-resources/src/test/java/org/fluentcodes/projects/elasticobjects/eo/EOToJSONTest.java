package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.MapProviderJSON;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONTest extends TestHelper {

    @Test
    public void setSameMaps()  {
        Map map = new LinkedHashMap();
        map.put(S_KEY0, S_STRING);
        map.put(S_KEY1, S_INTEGER);
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(S_LEVEL0)
                .set(map);
        adapter.add(S_LEVEL1)
                .set(map);
        String toCompare = MapProviderJSON.toJSONMap(S_LEVEL0,
                MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_KEY1, S_INTEGER),
                S_LEVEL1,
                MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_KEY1, S_INTEGER)
        );
        String serialized = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);

        Assert.assertEquals(toCompare, serialized);
    }

    @Test
    public void setSameMapsWithCheck()  {
        Map map = new LinkedHashMap<>();
        map.put(S_KEY0, S_STRING);
        TestHelper.printStartMethod();
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add()
                .set(map);
        adapter
                .add(S_LEVEL0)
                .set(map);
        adapter.setCheckObjectReplication(true);
        String serialized = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        String toCompare = MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_LEVEL0,
                MapProviderJSON.toJSONMap(EOToJSON.REPEATED, Path.DELIMITER));
        Assert.assertEquals(toCompare, serialized);
    }
}

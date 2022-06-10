package org.fluentcodes.projects.elasticobjects.paths;


import org.fluentcodes.projects.elasticobjects.Path;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_EMPTY;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;

public class PathGetFirstEntryTest {

    @Test
    public void givenPathWith3PathElements_whenFirstEntry_thenFirstPathElement() {
        Path path = new Path(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
    }

    @Test
    public void givenPathWith1PathElements_whenFirstEntry_thenFirstPathElement() {
        Path path = new Path(S_LEVEL0);
        Assert.assertEquals(S_LEVEL0, path.getFirstEntry());
    }

    @Test
    public void givenPathWith1EmptyPathElements_whenFirstEntry_thenFirstPathElement() {
        Path path = new Path(S_EMPTY);
        Assert.assertNull(path.getFirstEntry());
    }
}
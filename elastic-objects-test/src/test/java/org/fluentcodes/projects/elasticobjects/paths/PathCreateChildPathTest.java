package org.fluentcodes.projects.elasticobjects.paths;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.Path;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.Path.DELIMITER;
import static org.fluentcodes.projects.elasticobjects.PathElement.SAME;

public class PathCreateChildPathTest {

    @Test
    public void given3Elements_then2Elements() {
        Path path = new Path(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Path childPath = path.createChildPath();
        Assertions.assertThat(childPath.directory()).isEqualTo(
                String.join(DELIMITER, new String[]{S_LEVEL1, S_LEVEL2}));
    }

    @Test
    public void given1Element_then0Elements() {
        Path path = new Path(S_LEVEL0);
        Path childPath = path.createChildPath();
        Assertions.assertThat(childPath.directory()).isEqualTo(SAME);;
    }

    @Test
    public void given1ElementAbsolute_then0Elements() {
        Path path = new Path(DELIMITER, S_LEVEL0);
        Path childPath = path.createChildPath();
        Assertions.assertThat(childPath.directory()).isEqualTo(DELIMITER);
    }

}
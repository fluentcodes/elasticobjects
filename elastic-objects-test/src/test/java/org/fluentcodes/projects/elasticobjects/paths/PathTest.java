package org.fluentcodes.projects.elasticobjects.paths;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_EMPTY;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.Path.DELIMITER;
import static org.fluentcodes.projects.elasticobjects.PathElement.SAME;

public class PathTest {

    @Test
    public void constructorStringEmpty() {
        Assert.assertEquals(SAME, new Path(S_EMPTY).directory());
        Assert.assertEquals(SAME, new Path(null).directory());
        Assert.assertEquals(DELIMITER, new Path("///").directory());
        Assert.assertEquals(DELIMITER, new Path("/././").directory());
        Assert.assertEquals(DELIMITER, new Path("/ /./\t/").directory());
    }

    @Test
    public void withNull_directoryEqualsSame() {
        Assert.assertEquals(SAME, new Path(null).directory());
    }

    @Test
    public void withDelimiter_directoryEqualsDelimiter() {
        Assert.assertEquals(DELIMITER, new Path(DELIMITER).directory());
    }

    @Test
    public void constructorString_back() {
        Assert.assertEquals("../../back", new Path("../../back").directory());
    }

    @Test
    public void withSameAtBeginning_removed() {
        Path other = new Path(SAME, S_LEVEL0);
        Assertions.assertThat(other.directory())
                .isEqualTo(String.join(DELIMITER, new String[]{S_LEVEL0}));
    }

    @Test
    public void with3Entries_sizeIs3() {
        Path path = new Path(S_LEVEL0, S_LEVEL1, S_LEVEL2);
        Assertions.assertThat(path.size()).isEqualTo(3);
        Assertions.assertThat(path.directory()).isEqualTo(
                String.join(DELIMITER, new String[]{S_LEVEL0, S_LEVEL1, S_LEVEL2}));
    }

    @Test
    public void doubleDelimiter_filtered() {
        Path path = new Path(S_LEVEL0 + DELIMITER + DELIMITER +S_LEVEL1);
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory())
                .isEqualTo(S_LEVEL0 + DELIMITER + S_LEVEL1);
    }

    @Test
    public void given3EntriesAsStringWithOneEmpty_thenSizeIs2() {
        Path path = new Path(S_LEVEL0 + DELIMITER + DELIMITER + S_LEVEL2);
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + DELIMITER + S_LEVEL2);
    }

    @Test
    public void givenFirstPathDelimiter_whenIsAbsolute_thenTrue() {
        Path path = new Path(DELIMITER, S_LEVEL0);
        Assertions.assertThat(path.isAbsolute()).isTrue();
        Assertions.assertThat(path.size()).isEqualTo(1);
        Assertions.assertThat(path.directory()).isEqualTo(DELIMITER + S_LEVEL0);
    }

    @Test
    public void given3EntriesWith1Empty_thenSizeIs2() {
        Path path = new Path(S_LEVEL0, "", S_LEVEL2);
        Assertions.assertThat(path.isAbsolute()).isFalse();
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + DELIMITER + S_LEVEL2);
    }

    @Test
    public void given3Entrieswith1Null_thenSizeIs2() {
        Path path = new Path(S_LEVEL0, null, S_LEVEL2);
        Assertions.assertThat(path.isAbsolute()).isFalse();
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + DELIMITER + S_LEVEL2);
    }

    @Test
    public void given3EntriesAnd1Same_thenSizeIs2() {
        Path path = new Path(S_LEVEL0, SAME, S_LEVEL2);
        Assertions.assertThat(path.isAbsolute()).isFalse();
        Assertions.assertThat(path.size()).isEqualTo(2);
        Assertions.assertThat(path.directory()).isEqualTo(S_LEVEL0 + DELIMITER + S_LEVEL2);
    }

    @Test
    public void level0Level1_andAbsoluteLevel2__absolute_ignored() {
        Path path = new Path("level0/level1", "/level2");
        Assertions.assertThat(path.toString()).hasToString("level0/level1/level2");
    }

}
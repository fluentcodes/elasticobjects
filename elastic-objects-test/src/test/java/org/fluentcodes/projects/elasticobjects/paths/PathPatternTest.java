package org.fluentcodes.projects.elasticobjects.paths;

import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.CON_COMMA;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_PATH1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_PATH2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.join;
import static org.fluentcodes.projects.elasticobjects.Path.DELIMITER;


public class PathPatternTest {

    public PathPattern createPathPatternExample()  {
        
        PathPattern pattern = new PathPattern();
        String path1 = new Path(S_LEVEL0, S_LEVEL1).directory();
        pattern.addPath(path1);
        Assert.assertEquals(path1, pattern.getPath(0).directory(false));
        String path2 = new Path(S_LEVEL0, S_LEVEL2).directory();
        pattern.addPath(path2);
        Assert.assertEquals(path2, pattern.getPath(1).directory(false));
        pattern.addPath(PathElement.MATCHER_ALL);
        Assert.assertEquals(PathElement.MATCHER_ALL, pattern.getPath(2).directory(false));
        return pattern;
    }

    /*@Test
    public void compareSerialized()  {
        
        final PathPattern pattern = new PathPattern();
        pattern.addPath(Path.ofs(S_LEVEL0, S_LEVEL1));
        pattern.addPath(Path.ofs(S_LEVEL0, S_LEVEL1));
        new XpectEo<>(ProviderRootTest.EO_CONFIGS).compareAsString(pattern);
        //AssertEO.compare(TRootTestProvider.EO_CONFIGS, pattern);
    }*/

   /* @Test
    public void getPathListTest()  {
        final PathPattern pathPattern = createPathPatternExample();
        final PathPattern nextPath = pathPattern.getPathList(S_LEVEL0);
        Assert.assertEquals(new Integer(3), nextPath.size());
        Assert.assertEquals(S_LEVEL1, nextPath.getPath(0).directory(false));
        Assert.assertEquals(S_LEVEL2, nextPath.getPath(1).directory(false));
        Assert.assertEquals(PathElement.MATCHER_ALL, nextPath.getPath(2).directory(false));
    }*/

    @Test
    public void getFirstPathTest()  {
        
        final PathPattern pattern = createPathPatternExample();
        final List<String> nextPath = pattern.getFirstPath();
        Assert.assertEquals(2, nextPath.size());
        Assert.assertEquals(S_LEVEL0, nextPath.get(0));
        Assert.assertEquals(PathElement.MATCHER_ALL, nextPath.get(1));
    }

    @Test
    public void getFirstPathFilteredTest()  {
        
        final PathPattern pattern = createPathPatternExample();
        List<String> nextPath = pattern.filter(Arrays.asList(S_LEVEL0, S_PATH1, S_PATH2));
        Assert.assertEquals(3, nextPath.size());
        Assert.assertEquals(S_LEVEL0, nextPath.get(0));
    }

    @Test
    public void toStringTest() {
        
        final PathPattern pattern = new PathPattern(new String[]{S_LEVEL0});
        Assert.assertEquals(S_LEVEL0, pattern.getSerialized(false));
        pattern.addPath(S_LEVEL1);
        Assert.assertEquals(join(CON_COMMA, S_LEVEL0, S_LEVEL1), pattern.getSerialized(false));
        PathPattern patternFromString = new PathPattern(pattern.getSerialized());
        Assert.assertEquals(join(CON_COMMA, S_LEVEL0, S_LEVEL1), patternFromString.getSerialized(false));
    }


}
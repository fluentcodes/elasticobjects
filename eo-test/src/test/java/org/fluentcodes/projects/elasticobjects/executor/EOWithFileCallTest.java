package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.calls.FileCallRead;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 18.04.2017.
 */
public class EOWithFileCallTest {
    private static final String METHOD_SOURCE_TXT = ".read(source.txt)";

    @Test
    public void addFileReadAction()  {
        final EO root = TestProviderRootTest.createEo();
        root.setPathValue(Path.ofs(S_LEVEL0, S_LEVEL1),S_STRING);
        final EO child = root.getEo(S_LEVEL0);

        final CallExecutorResource executor = new CallExecutorResource(new FileCallRead(FILE_SOURCE_TXT));
        executor.setTargetPath(S_LEVEL0);
        child.addCall(executor);

        Assert.assertEquals(1, root.getCalls().getExecutorList().size());
        CallExecutorResource callExecutor = (CallExecutorResource) root.getCalls().getExecutorList().get(0);
        Assert.assertEquals(Path.DELIMITER + S_LEVEL0, callExecutor.getTargetPath());

        root.execute();
        Assert.assertEquals(S_STRING, root.get(Path.ofs(S_LEVEL0, SAMPLE_CONTENT)));
    }

    @Test
    public void givenJsonWithSource_ok() {
        final String json = "{" +
                "\"(CallExecutor)content\":" +
                "{" +
                "\"(FileCallRead)call\":" +
                "{" +
                "\"configKey\":\"" + FILE_SOURCE_TXT + "\"" +
                "}" +
                "}" +
                "}";
        EO eo = TestProviderRootTest.createEo(json);
        Assert.assertEquals(FileCallRead.class, eo.getEo("content/call").getModelClass());
        Assert.assertEquals(FILE_SOURCE_TXT, eo.get("content/call/configKey"));
        eo.execute();
    }
}

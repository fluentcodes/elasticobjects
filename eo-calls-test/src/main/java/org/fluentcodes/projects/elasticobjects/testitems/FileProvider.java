package org.fluentcodes.projects.elasticobjects.testitems;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;

import static org.fluentcodes.projects.elasticobjects.PathElement.V_SAME;

public class FileProvider {
    public static final String TEST_DATA = "testData";
    public static final String FILE_TEST_TXT = "FileTest.txt";
    public static final String FILE_TEST_CACHED_TXT = "FileTestCached.txt";
    public static final String ANOBJECT_ALL_TYPED_JSON = "AnObjectAllTyped.json";
    public static final String ANOBJECT_ALL_STANDARD_JSON = "AnObjectAllStandard.json";

    private FileProvider() {

    }

    public static EoChild readDirectory(final String directoryName, final String fileName) {
        final DirectoryReadCall call =
                new DirectoryReadCall(directoryName, fileName);
        final EoRoot root =
                ObjectProvider.createEo();
        call.setTargetPath("/test");
        call.execute(root);
        return (EoChild)root.getEo("/test");
    }

    public static EoChild readFromDataDirectory(final String fileName) {
        final DirectoryReadCall call =
                new DirectoryReadCall(TEST_DATA, fileName);
        final EoRoot root =
                ObjectProvider.createEo();
        call.setTargetPath("/data");
        call.execute(root);
        return (EoChild)root.getEo("/data");
    }

    public static void addFileFromDataDirectory(final EoChild eo, final String fileName) {
        final DirectoryReadCall call =
                new DirectoryReadCall(TEST_DATA, fileName);
        call.setTargetPath(V_SAME);
        call.execute(eo);
    }

}

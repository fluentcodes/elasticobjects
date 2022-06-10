package org.fluentcodes.projects.elasticobjects.testitems;

import static org.fluentcodes.projects.elasticobjects.PathElement.V_SAME;

import java.util.List;
import java.util.Map;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;

public class FileProvider {
    public static final String TEST_RESOURCES_DATA_DIR = "TestResourcesDataDir";
    public static final String TEST_DATA = "testData";
    public static final String FILE_TEST_TXT = "FileTest.txt";
    public static final String FILE_TEST_CACHED_TXT = "FileTestCached.txt";
    public static final String ANOBJECT_ALL_TYPED_JSON = "AnObjectAllTyped.json";
    public static final String ANOBJECT_ALL_STANDARD_JSON = "AnObjectAllStandard.json";
    public static final String AN_OBJECT_JSON = "AnObject.json";

    private FileProvider() {

    }

    public static final EO readAnObjectJson() {
        return read(AN_OBJECT_JSON, List.class, AnObject.class);
    }

    public static final EO readAnObjectAllTypedJson() {
        return read(ANOBJECT_ALL_TYPED_JSON, Map.class);
    }

    public static final EO readAnObjectAllStandardJson() {
        return read(ANOBJECT_ALL_STANDARD_JSON, Map.class);
    }

    public static final EO read(String fileConfigKey, Class... classes) {
        final EoRoot root = ObjectProvider.createEoWithClasses(classes);
        FileReadCall call = new FileReadCall(fileConfigKey);
        call.setTargetPath(Path.DELIMITER);
        call.execute(root);
        return root;
    }

    public static EoChild readDirectory(final String directoryName, final String fileName) {
        final DirectoryReadCall call =
            new DirectoryReadCall(directoryName, fileName);
        final EoRoot root =
            ObjectProvider.createEo();
        call.setTargetPath("/test");
        call.execute(root);
        return (EoChild) root.getEo("/test");
    }

    public static EoChild readFromDataDirectory(final String fileName) {
        final DirectoryReadCall call =
            new DirectoryReadCall(TEST_DATA, fileName);
        final EoRoot root =
            ObjectProvider.createEo();
        call.setTargetPath("/data");
        call.execute(root);
        return (EoChild) root.getEo("/data");
    }

    public static void addFileFromDataDirectory(final EoChild eo, final String fileName) {
        final DirectoryReadCall call =
            new DirectoryReadCall(TEST_DATA, fileName);
        call.setTargetPath(V_SAME);
        call.execute(eo);
    }

}

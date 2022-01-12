package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_ANONYM;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_GUEST;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * @author Werner Diwischek
 * @since 11.10.2016.
 */
public class FileReadCallTest implements IModelConfigCreateTests {
    public static final String FILE_TEST_TXT = "FileTest.txt";
    public static final String FILE_TEST_CACHED_TXT = "FileTestCached.txt";

    @Override
    public Class<?> getModelConfigClass() {
        return FileReadCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void call_FileTestCachedTxt__execute__content_returned() {
        final FileReadCall call = new FileReadCall(FILE_TEST_CACHED_TXT);
        final String content = (String) call.execute(ObjectProvider.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void call_SourceTxt__execute__content_returned() {
        final FileReadCall call = new FileReadCall(FileConfigTest.FILE_TEST_TXT);
        final String content = (String) call.execute(ObjectProvider.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void call_SourceTxt_role_Guest__execute__content_returned() {
        final Call call = new FileReadCall(FileConfigTest.FILE_TEST_TXT);
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles(R_GUEST);
        String content = (String) call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(content).isEqualTo(S_STRING);
    }

    @Test
    public void eo_SourceTxt_targetPath_test_role_Guest__execute__content_set() {
        final Call call = new FileReadCall(FileConfigTest.FILE_TEST_TXT)
                .setTargetPath(AnObject.F_MY_STRING);
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(AnObject.F_MY_STRING)).isEqualTo(S_STRING);
    }

    @Test
    public void call_SourceTxt_role_Anonym__execute__exception_thrown() {
        final FileReadCall call = new FileReadCall(FileConfigTest.FILE_TEST_TXT);
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(() -> {
                    call.execute(eo);
                })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No READ right for [anonym] and resource FileTest.txt.");
    }

    @Test
    public void eo_SourceTxt_role_Anonym__execute__has_log() {
        final FileReadCall call = new FileReadCall(FileConfigTest.FILE_TEST_TXT);
        EoRoot eo = ObjectProvider.createEo();
        eo.addCall(call);
        eo.setRoles(R_ANONYM);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void eo_SourceTxt_child_level0_targetPath_level2___execute__eo_set_level2() {
        final EoRoot root = ObjectProvider.createEo();
        root.set(S_STRING, S_LEVEL0, S_LEVEL1);
        final IEOScalar child = root.getEo(S_LEVEL0);

        final Call call = new FileReadCall(FileConfigTest.FILE_TEST_TXT)
                .setTargetPath(S_LEVEL2);
        Assert.assertEquals(S_LEVEL2, call.getTargetPath());
        child.addCall(call);

        child.execute();
        Assertions.assertThat(child.getLog()).isEmpty();
        Assertions.assertThat(root.get(S_LEVEL2)).isEqualTo(S_STRING);
    }

    @Test
    public void json_SourceTxt___execute__eo_set() {
        final String json = "{" +
                "\"(List)content\":" +
                "{" +
                "\"(FileReadCall)call\":" +
                "{" +
                "\"fileConfigKey\":\"" + FILE_TEST_TXT + "\"" +
                "}" +
                "}" +
                "}";
        EoRoot eo = ObjectProvider.createEo(json);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(FileReadCall.class, eo.getEo("_calls", "0").getModelClass());
        Assert.assertEquals(FILE_TEST_TXT, eo.get("_calls", "0", "fileConfigKey"));
        eo.execute();

    }

}

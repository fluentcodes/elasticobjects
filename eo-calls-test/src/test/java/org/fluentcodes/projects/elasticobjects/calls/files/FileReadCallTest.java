package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.FileProvider;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_ANONYM;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_GUEST;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.junit.Assert.assertEquals;

/**
 * @author Werner Diwischek
 * @since 11.10.2016.
 */
public class FileReadCallTest implements IModelConfigCreateTests {

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
    public void call_FileTestCachedTxt() {
        final FileReadCall call = new FileReadCall(FileProvider.FILE_TEST_CACHED_TXT);
        final String content = (String) call.execute(ObjectProvider.createEo());
        assertEquals(S_STRING, content);
    }

    @Test
    public void call_FileTestTxt() {
        final FileReadCall call = new FileReadCall(FileConfigTest.FILE_TEST_TXT);
        final String content = (String) call.execute(ObjectProvider.createEo());
        assertEquals(S_STRING, content);
    }

    @Test
    public void call_FileTestTxt_roleGuest() {
        final Call call = new FileReadCall(FileConfigTest.FILE_TEST_TXT);
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles(R_GUEST);
        String content = (String) call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(content).isEqualTo(S_STRING);
    }

    @Test
    public void eo_FileTestTxt_roleGuest() {
        final Call call = new FileReadCall(FileConfigTest.FILE_TEST_TXT)
                .setTargetPath(AnObject.F_MY_STRING);
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(AnObject.F_MY_STRING)).isEqualTo(S_STRING);
    }

    @Test(expected = EoException.class)
    public void call_FileTestTxt_roleAnonym_EoException() {
        final FileReadCall call = new FileReadCall(FileConfigTest.FILE_TEST_TXT);
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles(R_ANONYM);
        call.execute(eo);
    }

    @Test
    public void eo_FileTestTxt_roleAnonym_hasLog() {
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
        final EOInterfaceScalar child = root.getEo(S_LEVEL0);

        final Call call = new FileReadCall(FileConfigTest.FILE_TEST_TXT)
                .setTargetPath(S_LEVEL2);
        assertEquals(S_LEVEL2, call.getTargetPath());
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
                "\"fileConfigKey\":\"" + FileProvider.FILE_TEST_TXT + "\"" +
                "}" +
                "}" +
                "}";
        EoRoot eo = ObjectProvider.createEo(json);
        Assertions.assertThat(eo.getLog()).isEmpty();
        assertEquals(FileReadCall.class, eo.getEo("_calls", "0").getModelClass());
        assertEquals(FileProvider.FILE_TEST_TXT, eo.get("_calls", "0", "fileConfigKey"));
        eo.execute();
    }

}

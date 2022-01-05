package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_ANONYM;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_GUEST;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_VALUE11;

/**
 * Created 12.6.2018
 */
public class FileReadCallJsonTest {
    private final static String LIST_SIMPLE_JSON = "ListSimple.json";


    @Test
    public void givenCallRoleGuestAndFileTmp_whenExecuteCall_thenThrowException() {
        final FileReadCall call = new FileReadCall(JsonWriteCallTest.FILE_TMP_JSON);
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.setRoles(R_GUEST);
        call.execute(eo);
        Assert.assertTrue(eo.getLog().isEmpty());
    }

    @Test
    public void givenCallRoleAnonymAndFileSource_whenExecuteCall_thenThrowException() {
        final FileReadCall call = new FileReadCall(LIST_SIMPLE_JSON);
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(() -> {
                    call.execute(eo);
                })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No READ right for [anonym] and resource ListSimple.json.");
    }

    @Test
    public void givenCallRoleGuestAndFileSource_whenExecuteCall_ok() {
        final FileReadCall call = new FileReadCall(LIST_SIMPLE_JSON);
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.setRoles(R_GUEST);
        String content = (String) call.execute(eo);
        Assertions.assertThat(content).isEqualTo("[\n" +
                "  {\n" +
                "    \"key1\": \"value11\",\n" +
                "    \"key2\": \"value12\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"key1\": \"value21\",\n" +
                "    \"key2\": \"value22\"\n" +
                "  }\n" +
                "]");
        //Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getEo(S0));
        //Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getEo(S0).get(S_KEY1));
    }

    @Test
    public void givenEoRoleGuestAndFileSource_whenExecute_thenValuesAreMapped() {
        final FileReadCall call = new FileReadCall(LIST_SIMPLE_JSON);
        EoRoot eo = ProviderConfigMaps.createEo();
        call.setTargetPath(".");
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S0)).isNotNull();
        Assertions.assertThat(eo.get(S0, S_KEY1)).isEqualTo(S_VALUE11);
    }

    @Test
    public void eo__fileConfigKey_AnObjectJson_targetPath_level0_models_List_AnObject__execute__$() {
        final Call call = new FileReadCall("AnObject.json")
                .setTargetPath("(List," + AnObject.class.getSimpleName() + ")level0");
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.get("level0", "0", AnObject.MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.getEo("level0", "0").getModelClass()).isEqualTo(AnObject.class);
    }

}

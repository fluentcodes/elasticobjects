package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_VALUE11;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_VALUE12;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_VALUE21;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_VALUE22;
import static org.fluentcodes.projects.elasticobjects.calls.PermissionsForConfig.SUPER_ADMIN;

/**
 * Created 23.8.2020
 */
public class JsonWriteCallTest {

    public static final String FILE_TMP_JSON = "target.json";

    @Test
    public void call__Json__role_SuperAdmin__execute__logEmpty() {
        final JsonWriteCall callWrite = new JsonWriteCall(FILE_TMP_JSON);
        EoRoot eo = ObjectProvider.createEo();
        eo.setRoles(SUPER_ADMIN);
        eo.set(S_VALUE11, S0, S_KEY1);
        eo.set(S_VALUE12, S0, S_KEY2);
        eo.set(S_VALUE21, S1, S_KEY1);
        eo.set(S_VALUE22, S1, S_KEY2);
        eo.setRoles(SUPER_ADMIN);
        String result = callWrite.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("target.json");
    }
}

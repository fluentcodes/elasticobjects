package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 23.8.2020
 */
public class JsonWriteCallTest {

    public static final String FILE_TMP_JSON = "target.json";

    @Test
    public void call__Json__role_SuperAdmin__execute__logEmpty()  {
        final JsonWriteCall callWrite = new JsonWriteCall(FILE_TMP_JSON);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_SUPER_ADMIN);
        eo.set(S_VALUE11, S0,S_KEY1);
        eo.set(S_VALUE12, S0,S_KEY2);
        eo.set(S_VALUE21, S1,S_KEY1);
        eo.set(S_VALUE22, S1,S_KEY2);
        eo.setRoles(R_SUPER_ADMIN);
        String result = callWrite.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("target.json");
    }
}

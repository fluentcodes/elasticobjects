package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

/**
 * Created 7.9.2020
 */
@Ignore
public class ConfigOpenApiCallTest {
    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(ConfigOpenApiCall.class);
    }


    @Test
    public void callConfigFilter_eqConfigCall__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigOpenApiCall call = new ConfigOpenApiCall(ConfigCall.class.getSimpleName());
        Map result = (Map) call.execute(eo);
        new XpectEo().compareAsString(result);
    }

    @Test
    public void eoConfigFilter_eqConfigCall__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final Call call = new ConfigOpenApiCall(ConfigCall.class.getSimpleName())
                .setTargetPath("result");
        eo.addCall(call);
        eo.execute();
        Object resultValue = eo.get("result");
        new XpectEo<>().compareAsString(resultValue);
    }
}

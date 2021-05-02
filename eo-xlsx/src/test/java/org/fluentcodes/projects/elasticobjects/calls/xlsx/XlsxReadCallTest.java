package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.XlsxConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 08.10.2016.
 */
public class XlsxReadCallTest {
    private static final String LIST_SIMPLE_XLSX = "ListSimple.xlsx:test";

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(XlsxReadCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(XlsxReadCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ModelConfigChecks.resolve(XlsxConfig.class);
    }

    @Test
    public void call_ListSimpleXlsx__execute__listReturned()  {
        final Call call = new XlsxReadCall(LIST_SIMPLE_XLSX);
        EO eo = ProviderRootTestScope.createEo(new ArrayList<>());
        call.execute(eo);
        List value = (List)eo.get();
                Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Map firstRow = (Map) value.get(0);
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void eo_ListSimpleXlx__execute__2rows()  {
        final Call call = new XlsxReadCall(LIST_SIMPLE_XLSX);

        EO eo = ProviderRootTestScope.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isEqualTo(2);
        Map firstRow = (Map) eo.get("0");
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }
}

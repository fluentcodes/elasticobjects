package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 08.10.2016.
 */
public class XlsxReadCallTest implements IModelConfigCreateTests {
    private static final String LIST_SIMPLE_XLSX = "ListSimple.xlsx:test";

    @Override
    public Class<?> getModelConfigClass() {
        return XlsxReadCall.class;
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
    public void call_ListSimpleXlsx__execute__listReturned() {
        final Call call = new XlsxReadCall(LIST_SIMPLE_XLSX);
        IEOScalar eo = ObjectProvider.createEo(new ArrayList<>());
        call.execute(eo);
        List value = (List) eo.get();
        Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Map firstRow = (Map) value.get(0);
        Assert.assertEquals(2, firstRow.size());
        Assert.assertEquals("value11", firstRow.get("key1"));
    }

    @Test
    public void eo_ListSimpleXlx__execute__2rows() {
        final Call call = new XlsxReadCall(LIST_SIMPLE_XLSX);
        call.setTargetPath(".");
        IEOScalar eo = ObjectProvider.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(((EoChild) eo).size()).isEqualTo(2);
        Map firstRow = (Map) eo.get("0");
        Assert.assertEquals(2, firstRow.size());
        Assert.assertEquals("value11", firstRow.get("key1"));
    }
}

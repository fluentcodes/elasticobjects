package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class XlsxReadCallTest implements IModelConfigCreateTests {

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
    public void call_AnObjectXlsxTest__execute__notEmpty() {
        final Call call = new XlsxReadCall("AnObject.xlsx:test");
        EoRoot eo = ObjectProvider.createEo(new ArrayList<>());
        call.execute(eo);
        List value = (List) eo.get();
        Assertions.assertThat(value).isNotEmpty();
    }

    @Test
    public void template_AnObjectXlsx_tableTpl__execute__xpected() {
        final TemplateCall call = new TemplateCall("START " +
                "#{XlsxReadCall->AnObject.xlsx:test, xyz}.\n" +
                "#{TemplateResourceCall->table.tpl, xyz}." +
                "END");
        EoRoot eo = ObjectProvider.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        XpectStringJunit4.assertStatic(result);
    }
}

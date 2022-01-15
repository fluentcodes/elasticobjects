package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

public class ListTest {

    @Test
    public void eo_AnObjectCsv_target_list_AnObject__execute__mapped_to_object() {
        final Call call = new CsvSimpleReadCall("AnObject.csv")
                .setTargetPath("(List,AnObject)level0");
        EoRoot eo = ObjectProvider.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("level0/0").get("myString")).isEqualTo("value1");
        Assertions.assertThat(eo.getEo("level0/0").getModelClass()).isEqualTo(AnObject.class);
    }

    @Test
    public void template_AnObjectCsv_tableTpl__execute__xpected() {
        final TemplateCall call = new TemplateCall("START " +
                "#{CsvSimpleReadCall->AnObject.csv, xyz}.\n" +
                "#{TemplateResourceCall->table.tpl, xyz}." +
                "END");
        EoRoot eo = ObjectProvider.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        XpectStringJunit4.assertStatic(result);
    }

}

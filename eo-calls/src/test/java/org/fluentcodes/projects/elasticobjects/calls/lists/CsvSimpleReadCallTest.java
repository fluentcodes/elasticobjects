package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEoJunit4;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_ANONYM;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_GUEST;

/**
 * @author Werner Diwischek
 * @since 28.10.2018.
 */
public class CsvSimpleReadCallTest implements IModelConfigCreateTests {

    private static final String LIST_SIMPLE_CSV = "ListSimple.csv";

    @Override
    public Class<?> getModelConfigClass() {
        return CsvSimpleReadCall.class;
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
    public void call_ListSimpleCsv__compare__xpected() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        EoRoot eo = ProviderConfigMaps.createEo();
        call.execute(eo);
        eo.addCall(call);
        XpectEoJunit4.assertStaticEO(eo);
    }

    @Test
    public void call_ListSimpleCsv__execute__set_2rows() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        EoRoot eo = ProviderConfigMaps.createEo(new ArrayList<>());
        call.execute(eo);
        List value = (List) eo.get();
        Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Map firstRow = (Map) value.get(0);
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void eo_ListSimpleCsv_rowStart_2__execute__set_2rows() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        call.setTargetPath(".");
        EoRoot eo = ProviderConfigMaps.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isEqualTo(2);
        Map firstRow = (Map) eo.get("0");
        Assertions.assertThat(firstRow.size()).isEqualTo(2);
        Assertions.assertThat(firstRow.get("key1")).isEqualTo("value11");
    }

    @Test
    public void eo_ListSimpleCsv_rowStart_2__execute__setOnlyLastRow() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        call.getListParams().setRowStart(2);
        call.setTargetPath(".");
        EoRoot eo = ProviderConfigMaps.createEoWithClasses(List.class);
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.execute();
        Assertions.assertThat((eo.getEo("0")).get("key1")).isEqualTo("value21");
        Assertions.assertThatThrownBy(() -> {
            eo.getEo("1");
        })
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Could not move to path '1' because key '1' does not exist on '/'.");
    }

    @Test
    public void eo_ListSimpleCsv_rowEnd_2__execute__setOnlyFirstRow() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        call.getListParams().setRowEnd(2);
        call.setTargetPath(".");
        EoRoot eo = ProviderConfigMaps.createEoWithClasses(List.class);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.get("0/key1")).isEqualTo("value11");
        Assertions.assertThatThrownBy(() -> {
            eo.getEo("1");
        })
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Could not move to path '1' because key '1' does not exist on '/'.");
    }

    @Test
    public void eo_ListSourceCsv_rowHead_off__execute__setListValues() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        call.getListParams().setRowHead(-1);
        call.setTargetPath(".");
        EoRoot eo = ProviderConfigMaps.createEoWithClasses(List.class);
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.execute();
        Assertions.assertThat(eo.get("0/0")).isEqualTo("value11");
    }


    @Test
    public void eo_ListSimpleCsv_role_anonym__execute__has_log() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        call.setTargetPath(".");
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.setRoles(R_ANONYM);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isNotEmpty();
    }

    @Test
    public void eo_ListSimpleCsv_role_guest__execute__noLogEntry() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(LIST_SIMPLE_CSV);
        call.setTargetPath(".");
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
    }

    @Test
    public void eo_fileConfigKey_AnObjectCsv__execute__listMap() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall("AnObject.csv");
        call.setTargetPath(".");
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.get("0/" + AnObject.MY_STRING)).isEqualTo("value1");
        Assertions.assertThat(eo.getEo("0").getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void eo_fileConfigKey_AnObjectCsv_targetPath_level0_List_AnObject__execute__$() {
        final CsvSimpleReadCall call = new CsvSimpleReadCall(AnObject.class.getSimpleName() + ".csv");
        call.setTargetPath("(List," + AnObject.class.getSimpleName() + ")level0");
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.get("level0/0/" + AnObject.MY_STRING)).isEqualTo("value1");
        Assertions.assertThat(eo.getEo("level0/0").getModelClass()).isEqualTo(AnObject.class);
    }

    @Test
    public void TemplateCall_AnObjectCsv_Parameter__execute__$() {
        final TemplateCall call = new TemplateCall("START -\n#{CsvSimpleReadCall->AnObject.csv, xyz}. - END");
        EoRoot eo = ProviderConfigMaps.createEo();
        call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.get("xyz/0", AnObject.MY_STRING)).isEqualTo("value1");
    }

    @Test
    public void TemplateCall_AnObjectCsv_Json__execute__$() {
        final TemplateCall call = new TemplateCall("START -\n@{\"(CsvSimpleReadCall)xyz\":{" +
                "\"fileConfigKey\":\"AnObject.csv\"}}. - END");
        EoRoot eo = ProviderConfigMaps.createEo();
        call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.get("xyz/0", AnObject.MY_STRING)).isEqualTo("value1");
    }
}

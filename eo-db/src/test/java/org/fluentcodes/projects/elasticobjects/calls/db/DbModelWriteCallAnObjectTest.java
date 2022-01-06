package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Before;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.DbConfig.H2_BASIC;


public class DbModelWriteCallAnObjectTest {
    @Before
    public void init() {
        EoRoot eo = ProviderConfigMaps.createEo();
        Call call = new DbSqlExecuteCall(H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_AnObject_1L_value1New__execute__updated() {
        DbModelWriteCall call = new DbModelWriteCall();
        Assertions.assertThat(call).isNotNull();
        call.setConfigKey("h2:mem:AnObject");
        call.setTargetPath("/result");
        EoRoot eo = ProviderConfigMaps.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value1New");
        anObject.setId(1L);
        IEOScalar child = eo.set(anObject, "test");
        call.execute(child);
        Assertions.assertThat(((IEOObject) eo.getEo("/result")).size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/result/0/myString")).isEqualTo("value1New");
        Assertions.assertThat(eo.get("/result/0/id")).isEqualTo(1L);
    }

    @Test
    public void eo_AnObject_1L_value1New_guest__execute__hasLogs() {
        AnObject anObject = new AnObject();
        anObject.setMyString("value1New");
        anObject.setId(1L);
        EoRoot eo = ProviderConfigMaps.createEo();
        eo.set(anObject, "test");
        eo.setRoles("guest");

        DbModelWriteCall call = new DbModelWriteCall();
        call.setSourcePath("test");
        call.setTargetPath("/result");
        eo.addCall(call);

        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();

    }

    @Test
    public void call_AnObject_4L_value4__execute__inserted() {
        DbModelWriteCall call = new DbModelWriteCall();
        Assertions.assertThat(call).isNotNull();
        call.setConfigKey("h2:mem:AnObject");
        call.setTargetPath("/result");
        EoRoot eo = ProviderConfigMaps.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value4");
        anObject.setId(4L);
        IEOScalar child = eo.set(anObject, "test");
        call.execute(child);
        Assertions.assertThat(((IEOObject) eo.getEo("/result")).size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/result/0/myString")).isEqualTo("value4");
        Assertions.assertThat(eo.get("/result/0/id")).isEqualTo(4L);
    }

}

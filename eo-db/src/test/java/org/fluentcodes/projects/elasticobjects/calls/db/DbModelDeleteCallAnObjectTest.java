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


public class DbModelDeleteCallAnObjectTest {
    @Before
    public void init() {
        EoRoot eo = ProviderConfigMaps.createEo();
        Call call = new DbSqlExecuteCall(H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_AnObject_3L__execute__deleted() {
        DbModelDeleteCall call = new DbModelDeleteCall();
        call.setConfigKey("h2:mem:AnObject");
        call.setTargetPath("/result");
        Assertions.assertThat(call).isNotNull();
        EoRoot eo = ProviderConfigMaps.createEo();
        AnObject anObject = new AnObject();
        anObject.setId(3L);
        IEOScalar child = eo.set(anObject, "test");
        Object value = call.execute(child);
        Assertions.assertThat(((IEOObject) eo.getEo("/result")).size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/result/0/myString")).isEqualTo("value3");
        Assertions.assertThat(eo.get("/result/0/id")).isEqualTo(3L);
    }

    @Test
    public void eo_AnObject_3L_guest__execute__hasLogs() {
        AnObject anObject = new AnObject();
        anObject.setId(3L);

        EoRoot eo = ProviderConfigMaps.createEo();
        eo.set(anObject, "test");
        eo.setRoles("guest");

        DbModelDeleteCall call = new DbModelDeleteCall(H2_BASIC);
        call.setTargetPath("/result");
        call.setSourcePath("/test");

        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

}

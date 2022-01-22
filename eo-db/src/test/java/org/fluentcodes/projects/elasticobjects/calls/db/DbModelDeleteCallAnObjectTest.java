package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Before;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.DbConfig.H2_BASIC;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelReadCallAnObjectTest.H_2_MEM;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelReadCallAnObjectTest.RESULT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_ID;


public class DbModelDeleteCallAnObjectTest {
    @Before
    public void init() {
        EoRoot eo = ObjectProvider.createEo();
        Call call = new DbSqlExecuteCall(H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void direct_id_3L() {
        DbModelDeleteCall call = new DbModelDeleteCall(H_2_MEM, RESULT);
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_ID, 3L);
        Object value = call.execute(child);
        Assertions.assertThat(((EO) child.getEo("/result")).size()).isEqualTo(1);
        Assertions.assertThat(child.get("/result/0/myString")).isEqualTo("value3");
        Assertions.assertThat(child.get("/result/0/id")).isEqualTo(3L);
    }

    @Test
    public void eo_id_3L() {
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_ID, 3L);

        DbModelDeleteCall call = new DbModelDeleteCall(H_2_MEM, "/" + RESULT);
        call.setSourcePath("test");
        child.addCall(call);
        child.execute();
        Assertions.assertThat(child.getLog()).isEmpty();
    }

}

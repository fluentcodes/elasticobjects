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
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;


public class DbModelReadCallAnObjectTest {

    public static final String RESULT = "result";
    public static final String TEST = "test";
    public static final String H_2_MEM = "h2:mem";

    @Before
    public void init() {
        EoRoot eo = ObjectProvider.createEo();
        Call call = new DbSqlExecuteCall(H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void direct_myString_value1() {
        DbModelReadCall call = new DbModelReadCall(H_2_MEM, "/" + RESULT);
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, "value1");
        call.execute(child);
        Assertions.assertThat(((EO) child.getEo("/", RESULT)).size()).isEqualTo(1);
        Assertions.assertThat(child.get("/result/0/myString")).isEqualTo("value1");
        Assertions.assertThat(child.get("/result/0/id")).isEqualTo(1L);
    }

    @Test
    public void direct_empty() {
        DbModelReadCall call = new DbModelReadCall(H_2_MEM, RESULT);
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(TEST);
        call.execute(child);
        Assertions.assertThat(((EO) child.getEo("/", RESULT)).size()).isEqualTo(3);
    }

    @Test
    public void eo_myString_value1() {
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, "value1");

        DbModelReadCall call = new DbModelReadCall(H_2_MEM, RESULT);
        call.setSourcePath("/test");
        child.addCall(call);

        child.execute();
        Assertions.assertThat(((EO) child.getEo("/result")).size()).isEqualTo(1);
        Assertions.assertThat(child.get("/result/0/myString")).isEqualTo("value1");
        Assertions.assertThat(child.get("/result/0/id")).isEqualTo(1L);
    }

    @Test
    public void json_id_1() {
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "   \"(AnObject)abc\":{\n" +
                "        \"id\":1\n" +
                "   },\n" +
                "   \"(DbModelReadCall)/xyz\":{\n" +
                "       \"configKey\":\"h2:db\",\n" +
                "       \"sourcePath\":\"abc\"\n" +
                "   }\n" +
                "}");

        eo.execute();
        Assertions.assertThat(((EO) eo.getEo("/xyz")).size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/xyz/0/myString")).isEqualTo("value1");
        Assertions.assertThat(eo.get("/xyz/0/id")).isEqualTo(1L);
    }


}

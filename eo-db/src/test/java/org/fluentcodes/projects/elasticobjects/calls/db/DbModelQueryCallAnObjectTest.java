package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Before;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.ID_1;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.MY_NATURAL_ID_1;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.MY_STRING_1;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.storeEntry1;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.junit.Assert.assertEquals;


public class DbModelQueryCallAnObjectTest {

    public static final String RESULT = "result";
    public static final String TEST = "test";
    public static final String H_2_MEM = "h2:mem";

    @Before
    public void init() {
        EoRoot eo = ObjectProvider.createEo();
        DbModelsCreateCall call = new DbModelsCreateCall(H_2_MEM);
        call.execute(eo);
    }

    @Test
    public void storeEntryDirect_myString_myString1() {
        storeEntry1();

        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, MY_STRING_1);

        DbModelQueryCall call = new DbModelQueryCall(H_2_MEM, "/" + RESULT);
        call.execute(child);

        assertEquals(1, ((EO) child.getEo("/", RESULT)).size());
        assertEquals(AnObject.class, child.getEo("/result/0").getModelClass());
        assertEquals(MY_STRING_1, child.get("/result/0/myString"));
        assertEquals(ID_1, child.get("/result/0/id"));
    }

    @Test
    public void storeEntryDirect_naturalId_naturalId1() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_NATURAL_ID, MY_NATURAL_ID_1);

        DbModelQueryCall call = new DbModelQueryCall(H_2_MEM, "/" + RESULT);
        call.execute(child);

        assertEquals(1, ((EO) child.getEo("/", RESULT)).size());
    }

    @Test
    public void storeEntryDirect_myString_value_empty() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, "value");

        DbModelQueryCall call = new DbModelQueryCall(H_2_MEM, "/" + RESULT);
        call.execute(child);

        assertEquals(0, ((EO) child.getEo("/", RESULT)).size());
    }

    @Test
    public void storeEntryDirect_empty_AllSelected() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(TEST);

        DbModelQueryCall call = new DbModelQueryCall(H_2_MEM, "/" + RESULT);
        call.execute(child);

        assertEquals(1, ((EO) child.getEo("/", RESULT)).size());
    }

    @Test
    public void storeEntryEo_myString_myString1() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, MY_STRING_1);

        DbModelQueryCall call = new DbModelQueryCall(H_2_MEM, RESULT);
        call.setSourcePath("/test");
        call.setTargetPath("/result");
        child.addCall(call);

        child.execute();
        assertEquals(1, ((EO) child.getEo("/result")).size());
    }

    @Test
    public void json_id_1() {
        storeEntry1();
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "   \"(AnObject)abc\":{\n" +
                "        \"id\":1\n" +
                "   },\n" +
                "   \"(" + DbModelQueryCall.class.getSimpleName() + ")/xyz\":{\n" +
                "       \"configKey\":\"" + H_2_MEM + "\",\n" +
                "       \"sourcePath\":\"abc\"\n" +
                "   }\n" +
                "}");

        eo.execute();
        Assertions.assertThat(((EO) eo.getEo("/xyz")).size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/xyz/0/myString")).isEqualTo(MY_STRING_1);
        Assertions.assertThat(eo.get("/xyz/0/id")).isEqualTo(ID_1);
    }
}

package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Before;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.ID_1;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.MY_NATURAL_ID_1;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.MY_STRING_1;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.storeEntry1;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCallAnObjectTest.storeEntry2;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.junit.Assert.assertEquals;


public class DbModelReadCallAnObjectTest {

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
    public void storeEntryDirect_naturalId_naturalId1() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_NATURAL_ID, MY_NATURAL_ID_1);

        DbModelReadCall call = new DbModelReadCall(H_2_MEM, "/" + RESULT);
        call.execute(child);

        assertEquals(ID_1, child.get(F_ID));
    }

    @Test
    public void storeEntryDirect_myString_myString1() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, MY_STRING_1);

        DbModelReadCall call = new DbModelReadCall(H_2_MEM);
        call.execute(child);

        assertEquals(AnObject.class, child.getModelClass());
        assertEquals(MY_STRING_1, child.get(F_MY_STRING));
        assertEquals(ID_1, child.get(F_ID));
        assertEquals(MY_NATURAL_ID_1, child.get(F_NATURAL_ID));
    }


    @Test(expected = EoException.class)
    public void storeEntryDirect_myString_notExists_EoException() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, "notExists");

        DbModelReadCall call = new DbModelReadCall(H_2_MEM);
        call.execute(child);
    }

    @Test(expected = EoException.class)
    public void storeEntryDirect_with2Entries_empty_EoException() {
        storeEntry1();
        storeEntry2();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(TEST);
        DbModelReadCall call = new DbModelReadCall(H_2_MEM);
        call.execute(child);
    }

    @Test
    public void storeEntryEo_myString_myString1() {
        storeEntry1();
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_MY_STRING, MY_STRING_1);

        DbModelReadCall call = new DbModelReadCall(H_2_MEM, RESULT);
        call.setSourcePath("/test");
        child.addCall(call);

        child.execute();
        assertEquals(MY_NATURAL_ID_1, child.get(F_NATURAL_ID));
    }

    @Test
    public void json_id_1() {
        storeEntry1();
        EoRoot eo = ObjectProvider.createEo("{\n" +
                "   \"(AnObject)abc\":{\n" +
                "        \"id\":1\n" +
                "   },\n" +
                "   \"(" + DbModelReadCall.class.getSimpleName() + ")/abc\":{\n" +
                "       \"configKey\":\"" + H_2_MEM + "\",\n" +
                "       \"sourcePath\":\"abc\"\n" +
                "   }\n" +
                "}");

        eo.execute();
        Assertions.assertThat(((EO) eo.getEo()).size()).isEqualTo(1);
        Assertions.assertThat(eo.get("abc", "myString")).isEqualTo(MY_STRING_1);
    }
}

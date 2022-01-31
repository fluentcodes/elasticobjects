package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelQueryCallAnObjectTest.H_2_MEM;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelQueryCallAnObjectTest.RESULT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_ID;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_AN_OBJECT_ID;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_ASUB_OBJECT_ID;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_ASUB_OBJECT;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.testitems.FileProvider.ANOBJECT_ALL_TYPED_JSON;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.createAnObjectEo;
import static org.fluentcodes.projects.elasticobjects.testitems.FileProvider.ANOBJECT_ALL_STANDARD_JSON;
import static org.fluentcodes.projects.elasticobjects.testitems.FileProvider.addFileFromDataDirectory;
import static org.junit.Assert.assertEquals;


public class DbModelWriteCallAnObjectTest {

    public static final String MY_STRING_1 = "myString1";
    public static final String MY_NATURAL_ID_1 = "naturalId1";
    public static final Long ID_1 = 1L;

    @Before
    public void init() {
        EoRoot eo = ObjectProvider.createEo();
        DbModelsCreateCall call = new DbModelsCreateCall(H_2_MEM);
        call.execute(eo);
    }

    public static EOInterfaceScalar createEo1() {
        EOInterfaceScalar child = createEo(1L, F_NATURAL_ID, F_MY_STRING);
        child.set(MY_NATURAL_ID_1, F_NATURAL_ID);
        child.set(MY_STRING_1, F_MY_STRING);
        return child;
    }

    public static EOInterfaceScalar createEo(final Long id, final String naturalId, final String myString) {
        EOInterfaceScalar child = createAnObjectEo(F_ID, id);
        child.set(naturalId, F_NATURAL_ID);
        child.set(myString, F_MY_STRING);
        return child;
    }

    public static void storeEntry1() {
        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM, "/" + RESULT);
        EOInterfaceScalar child = createEo1();
        call.execute(child);
    }

    public static void storeEntry2() {
        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM, "/" + RESULT);
        EOInterfaceScalar child = createAnObjectEo(F_ID, 2L);
        child.set("naturalId2", F_NATURAL_ID);
        child.set("myString2", F_MY_STRING);
        call.execute(child);
    }

    @Test
    public void direct_id_1L_inserted() {
        storeEntry1();
        final String targetPath = "/read";
        DbModelQueryCall readCall = new DbModelQueryCall(H_2_MEM, targetPath);
        EOInterfaceScalar filterEo = createAnObjectEo(F_ID, 1l);
        readCall.execute(filterEo);
        assertEquals(AnObject.class, filterEo.getEo(targetPath, "0").getModelClass());
        assertEquals("myString1", filterEo.get(targetPath, "0/myString"));
    }

    @Test
    public void direct_id_6L_insert() {
        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM);
        EOInterfaceScalar child = createEo(6L, "value6", "value6");
        call.execute(child);
        assertEquals(AnObject.class, child.getModelClass());
        assertEquals(6L, child.get("id"));
        assertEquals("value6", child.get(F_MY_STRING));
        /*assertEquals("{\n" +
                "  \"(Long)id\": 6,\n" +
                "  \"naturalId\": \"value6\",\n" +
                "  \"myString\": \"value6\",\n" +
                "  \"(Long)myASubObject_id\": 0,\n" +
                "  \"(Long)myAnObject_id\": 0,\n" +
                "  \"myBoolean\": false,\n" +
                "  \"(Double)myDouble\": 0.0,\n" +
                "  \"(Float)myFloat\": 0.0,\n" +
                "  \"myInt\": 0,\n" +
                "  \"(Long)myLong\": 0\n" +
                "}", ((EoChild)child).toJson());*/
    }

    @Test
    public void direct_role_guest_hasLogs() {
        EOInterfaceScalar child = createEo1();
        child.setRoles("guest");

        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM);
        call.setSourcePath("/test");
        child.addCall(call);

        child.execute();
        Assertions.assertThat(child.getLog()).contains("No WRITE right for [guest] and resource h2:mem.");
    }

    @Test
    public void direct_id_4L_inserted() {
        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM);
        EOInterfaceScalar child = createEo(4L, "value4", "value4");
        call.execute(child);
        assertEquals(10, ((EO) child).size());
        assertEquals("value4", child.get("myString"));
        assertEquals(4L, child.get("id"));
    }


    @Test
    public void call_AnObjectAllTyped() {
        EoChild child = (EoChild) createAnObjectEo("test");
        addFileFromDataDirectory(child, ANOBJECT_ALL_TYPED_JSON);
        assertEquals("test", child.get(F_NATURAL_ID));
        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM);
        call.execute(child);
        assertEquals(1L, child.get(F_MY_ASUB_OBJECT_ID));
        assertEquals(2L, child.get(F_MY_AN_OBJECT_ID));
    }


}

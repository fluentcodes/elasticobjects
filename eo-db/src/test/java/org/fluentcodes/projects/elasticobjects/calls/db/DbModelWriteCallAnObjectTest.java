package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Before;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.DbConfig.H2_BASIC;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelReadCallAnObjectTest.H_2_MEM;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelReadCallAnObjectTest.RESULT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_ID;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.junit.Assert.assertEquals;


public class DbModelWriteCallAnObjectTest {
    @Before
    public void init() {
        EoRoot eo = ObjectProvider.createEo();
        DbModelsCreateCall call = new DbModelsCreateCall(H_2_MEM);
        call.execute(eo);
    }

    public static void storeEntry() {
        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM, "/" + RESULT);
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_ID, 1l);
        child.set("id1", F_NATURAL_ID);
        child.set("myString1", F_MY_STRING);
        call.execute(child);
    }

    @Test
    public void direct_id_1L_inserted() {
        storeEntry();
        final String targetPath = "/read";
        DbModelReadCall readCall = new DbModelReadCall(H_2_MEM, targetPath);
        EOInterfaceScalar filterEo = ObjectProvider.createAnObjectEo(F_ID, 1l);
        readCall.execute(filterEo);
        assertEquals(AnObject.class, filterEo.getEo(targetPath, "0").getModelClass());
        assertEquals("myString1", filterEo.get(targetPath, "0/myString"));
    }

    @Test
    public void direct_id_6L_insert() {
        DbModelWriteCall call = new DbModelWriteCall(H_2_MEM, "/" + RESULT);
        EOInterfaceScalar child = ObjectProvider.createAnObjectEo(F_ID, 6l);
        child.set("value6", F_MY_STRING);
        child.set("value6", F_NATURAL_ID);
        call.execute(child);
        EOInterfaceScalar result = child.getEo("/result");
        assertEquals(AnObject.class, result.getModelClass());
        assertEquals("value6", result.get("myString"));
        assertEquals(6L, result.get("id"));
        assertEquals("{\n" +
                "  \"(Long)id\": 6,\n" +
                "  \"(Long)myASubObject_id\": 0,\n" +
                "  \"(Long)myAnObject_id\": 0,\n" +
                "  \"myBoolean\": false,\n" +
                "  \"(Double)myDouble\": 0.0,\n" +
                "  \"(Float)myFloat\": 0.0,\n" +
                "  \"myInt\": 0,\n" +
                "  \"(Long)myLong\": 0,\n" +
                "  \"myString\": \"value6\"\n" +
                "}", ((EoChild)result).toJson());
    }

    @Test
    public void eo_1L_hasLogs() {
        AnObject anObject = new AnObject();
        anObject.setMyString("value1New");
        anObject.setId(1L);
        EoRoot eo = ObjectProvider.createEo();
        eo.set(anObject, "test");
        eo.setRoles("guest");

        DbModelWriteCall call = new DbModelWriteCall();
        call.setSourcePath("test");
        call.setTargetPath("/result");
        eo.addCall(call);

        eo.execute();
        Assertions.assertThat(eo.getLog()).contains("Config key is empty within 'DbModelsConfig'");
    }

    @Test
    public void direct_id_4L_inserted() {
        DbModelWriteCall call = new DbModelWriteCall();
        Assertions.assertThat(call).isNotNull();
        call.setConfigKey("AnObjectDb");
        call.setTargetPath("/result");
        EoRoot eo = ObjectProvider.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value4");
        anObject.setNaturalId("value4");
        anObject.setId(4L);
        EOInterfaceScalar child = eo.set(anObject, "test");
        call.execute(child);
        Assertions.assertThat(((EO) eo.getEo("/result")).size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/result/0/myString")).isEqualTo("value4");
        Assertions.assertThat(eo.get("/result/0/id")).isEqualTo(4L);
    }

}

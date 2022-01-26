package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelQueryCallAnObjectTest.H_2_MEM;
import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelQueryCallAnObjectTest.RESULT;
import static org.junit.Assert.assertEquals;

public class DbModelsCreateCallTest {

    @Test
    public void H2Mem_create() {
        DbModelsCreateCall call = new DbModelsCreateCall(H_2_MEM, RESULT);
        EOInterfaceScalar child = ObjectProvider.createEo();
        Object value = call.execute(child);
        assertEquals("CREATE DATABASE: \n" +
                        "Drop table AnObject if exists: 0\n" +
                        "Drop table ASubObject if exists: 0\n" +
                        "Create table AnObject (id identity primary key not null, myASubObject_id bigint, myAnObject_id bigint, myBoolean boolean, myDate date, myDouble double, myFloat float, myInt integer, myLong bigint, myString varchar(20), naturalId varchar(80) not null): 0\n" +
                        "Alter table AnObject add unique   (id): 0\n" +
                        "Alter table AnObject add unique   (naturalId): 0\n" +
                        "Create table ASubObject (id identity primary key not null, myASubObject_id bigint, myString varchar(20), name varchar(80), naturalId varchar(80) not null): 0\n" +
                        "Alter table ASubObject add unique   (id): 0\n" +
                        "Alter table ASubObject add unique   (naturalId): 0\n" +
                        "Constraint Alter table AnObject add constraint AnObject_myASubObject_id_FK foreign key (myASubObject_id) references ASubObject(id): 0\n" +
                        "Constraint Alter table AnObject add constraint AnObject_myAnObject_id_FK foreign key (myAnObject_id) references AnObject(id): 0\n" +
                        "Constraint Alter table ASubObject add constraint ASubObject_myASubObject_id_FK foreign key (myASubObject_id) references ASubObject(id): 0\n",
                child.get("/result"));
    }

}

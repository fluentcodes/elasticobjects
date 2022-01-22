package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_ID;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_AN_OBJECT;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.CONFIG_MAPS;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class DbModelConfigTest implements IModelConfigNoCreateTests {

    public static final String AN_OBJECT = "AnObjectDb";

    @Override
    public Class<?> getModelConfigClass() {
        return DbModelConfig.class;
    }

    @Override
    @Test
    public void createThrowsEoException() {
        assertCreateThrowingException();
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

    public DbModelConfig get(String dbModelKey) {
        return (DbModelConfig)CONFIG_MAPS.find(DbModelConfig.class, dbModelKey);
    }

    @Test
    public void anObject_notNull() {
        DbModelConfig config = get(AN_OBJECT);
        Assert.assertNotNull(config);
    }

    @Test
    public void anObject_queryInsert() {
        EoChild child = (EoChild)ObjectProvider.createAnObjectEo(F_ID, 1L);
        DbModelConfig config = get(AN_OBJECT);
        assertEquals("Insert into AnObject " +
                        "(id) " +
                        "values(?)"
                ,config.createInsertStatement(child).getStatement());
    }

    @Test
    public void anObject_queryUpdate() {
        EoChild child = (EoChild)ObjectProvider.createAnObjectEo(F_ID, 1L);
        DbModelConfig config = get(AN_OBJECT);
        assertEquals("Update AnObject " +
                        "set id = ? " +
                        "where id = ? ",
                config.createUpdateStatement(child).getStatement());
    }

    @Test
    public void anObject_createForeignConstraint_myAnObject() {
        ModelConfig anObject = ObjectProvider.findModel(AnObject.class);
        String value = DbModelConfig.createForeignConstraint(AnObject.class.getSimpleName(), anObject.getField(MY_AN_OBJECT));
        assertEquals("Add AnObject_myAnObject_id_FK (myAnObject_id) references AnObject(id)", value);
    }

    @Test
    public void anObject_createFindStatement_id() {
        EoRoot root = ObjectProvider.createEo(new AnObject().setId(1L));

        DbModelConfig config = get(AN_OBJECT);
        StatementFind statement = config.createFindStatement(root);
        assertEquals(1,statement.getValues().size());
        assertEquals("Select * from AnObject where id = ? ",statement.getStatement());
    }

    @Test
    public void anObject_createFindStatement_naturalId() {
        EoRoot root = ObjectProvider.createEo(new AnObject().setNaturalId("id"));

        DbModelConfig config = get(AN_OBJECT);
        StatementFind statement = config.createFindStatement(root);
        assertEquals(1,statement.getValues().size());
        assertEquals("Select * from AnObject where naturalId = ?",statement.getStatement());
    }

    @Test
    public void anObject_createUpdateStatement_id() {
        EoRoot root = ObjectProvider.createEo(new AnObject().setId(1L));

        DbModelConfig config = get(AN_OBJECT);
        StatementPreparedValues statement = config.createUpdateStatement(root);
        assertEquals(13,statement.getValues().size());
        assertEquals("Update AnObject " +
                "set id = ?, myASubObject_id = ?, myAnObject_id = ?, myBoolean = ?, myDate = ?, myDouble = ?, myFloat = ?, myInt = ?, myLong = ?, myObject = ?, myString = ?, naturalId = ? " +
                "where id = ? ",statement.getStatement());
    }

    @Test
    public void anObject_createInsertStatement() {
        EoRoot root = ObjectProvider.createEo(new AnObject().setId(1L));

        DbModelConfig config = get(AN_OBJECT);
        StatementPreparedValues statement = config.createInsertStatement(root);
        assertEquals(12,statement.getValues().size());
        assertEquals("Insert into AnObject " +
                "(id, myASubObject_id, myAnObject_id, myBoolean, myDate, myDouble, myFloat, myInt, myLong, myObject, myString, naturalId) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", statement.getStatement());
    }

    @Test
    public void anObject_createStatement() {
        DbModelConfig config = get(AN_OBJECT);
        String create = config.getCreateStatement();
        assertEquals("Create table AnObject " +
                "(id bigint, myASubObject_id bigint, myAnObject_id bigint, " +
                        "myBoolean boolean, myDate date, myDouble double, " +
                        "myFloat float, myInt integer, myLong bigint, myObject bigint, myString varchar(20), " +
                        "naturalId varchar(80))",
                create);
    }

    @Test
    public void anObject_dropStatement() {
        DbModelConfig config = get(AN_OBJECT);
        assertEquals("Drop AnObject if exists",
                config.getDropStatement());
    }





}


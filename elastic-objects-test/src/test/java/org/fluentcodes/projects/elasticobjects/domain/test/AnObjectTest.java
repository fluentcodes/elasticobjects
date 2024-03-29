package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldConfigProperties;
import org.fluentcodes.projects.elasticobjects.models.FieldInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigProperties;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_INT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_AN_OBJECT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_ASUB_OBJECT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.F_NATURAL_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AnObjectTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return AnObject.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
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

    @Test
    public void createModelBean_ShapeType() {
        ModelBean modelBean = ObjectProvider.createModelBean(AnObject.class);
        assertEquals(ShapeTypes.BEAN, modelBean.getShapeType());
        assertFalse(modelBean.getProperties().getFinal());
    }

    @Test
    public void createModelBean_toString() {
        ModelBean modelBean = ObjectProvider.createModelBean(AnObject.class);
        assertEquals("(BEAN)AnObject", modelBean.toString());
    }

    @Test
    public void findModel_myString_xpect() {
        FieldInterface field = ObjectProvider.findModel(AnObject.class).getField(F_MY_STRING);
        XpectStringJunit4.assertStatic(field.toString());
    }

    @Test
    public void createObject_myString() {
        AnObject object = (AnObject) ObjectProvider.createObject(AnObject.class,"test", F_MY_STRING);
        assertEquals("test", object.getMyString());
    }

    @Test(expected = EoException.class)
    public void createObject_myString_tooLong() {
        ObjectProvider.createObject(AnObject.class,"test01234567890123456789", F_MY_STRING);
    }

    @Test
    public void createObject_myInt() {
        AnObject object = (AnObject) ObjectProvider.createObject(AnObject.class, 1, F_MY_INT);
        assertEquals(Integer.valueOf(1), object.getMyInt());
    }

    @Test
    public void createObject_myAnObject_myInt() {
        AnObject object =  (AnObject) ObjectProvider.createObject(AnObject.class, 1, MY_AN_OBJECT, F_MY_INT);
        assertEquals(Integer.valueOf(1), object.getMyAnObject().getMyInt());
    }

    @Test
    public void createObject_myAnObject_myAnObject_myString() {
        AnObject object = (AnObject) ObjectProvider.createObject(AnObject.class, "test", MY_AN_OBJECT, MY_AN_OBJECT, F_MY_STRING);
        assertEquals("test", object.getMyAnObject().getMyAnObject().getMyString());
    }

    @Test
    public void createObject_myAsubObject_myAsubObject_myAsubObject_myString() {
        AnObject object = (AnObject) ObjectProvider.createObject(AnObject.class, "test", MY_ASUB_OBJECT, MY_ASUB_OBJECT, MY_ASUB_OBJECT, F_MY_STRING);
        assertEquals("test", object.getMyASubObject().getMyASubObject().getMyASubObject().getMyString());
    }

    @Test
    public void findModel_set_NaturalId() {
        ModelConfig config = ObjectProvider.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(F_NATURAL_ID, object, "test");
        Assertions.assertThat(((AnObject) object).getNaturalId()).isEqualTo("test");
        Assertions.assertThat(config.get(F_NATURAL_ID, object)).isEqualTo("test");
    }

    @Test
    public void findModel_set_myString() {
        ModelConfig config = ObjectProvider.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(F_MY_STRING, object, "test");
        Assertions.assertThat(((AnObject) object).getMyString()).isEqualTo("test");
        Assertions.assertThat(config.get(F_MY_STRING, object)).isEqualTo("test");
    }

    @Test
    public void findModel_properties_create() {
        ModelConfig config = ObjectProvider.CONFIG_MAPS
                .findModel(AnObject.class);
        ModelConfigProperties propertiesConfig = config.getProperties();
        assertTrue(propertiesConfig.getCreate());
    }

    @Test
    public void findModel_field_myAnObject_idKey() {
        ModelConfig config = ObjectProvider.CONFIG_MAPS
                .findModel(AnObject.class);
        FieldConfig fieldConfig = config.getField(MY_AN_OBJECT);
        FieldConfigProperties properties = fieldConfig.getProperties();
        assertEquals("myAnObject_id", properties.getIdKey());
    }
}

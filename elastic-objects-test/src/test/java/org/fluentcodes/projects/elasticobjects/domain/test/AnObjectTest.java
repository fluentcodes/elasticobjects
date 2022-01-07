package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.FieldInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_AN_OBJECT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_INT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.NATURAL_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AnObjectTest implements IModelConfigCreateTests {
    static AnObject createAnObject(String fieldKey, Object value) {
        String json = ObjectProviderDev.createKeyValueJson(fieldKey, value);
        EoRoot root = ObjectProvider.createEoWithClasses(AnObject.class);
        root.map(json);
        assertEquals(value, root.get(fieldKey));
        return (AnObject) root.get();
    }

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
    public void TEST__get_ShapeType__BEAN() {
        ModelBean modelBean = ObjectProvider.createModelBean(AnObject.class);
        assertEquals(ShapeTypes.BEAN, modelBean.getShapeType());
        assertFalse(modelBean.getProperties().getFinal());
    }

    @Test
    public void TEST__toString__BEAN_AnObject() {
        ModelBean modelBean = ObjectProvider.createModelBean(AnObject.class);
        assertEquals("(BEAN)AnObject", modelBean.toString());
    }

    @Test
    public void fieldConfigMString_xpected() {
        FieldInterface field = ObjectProvider.findModel(AnObject.class).getField(MY_STRING);
        XpectStringJunit4.assertStatic(field.toString());
    }

    @Test
    public void createAnObject_myString() {
        AnObject object = createAnObject(MY_STRING, "test");
        assertEquals("test", object.getMyString());
    }

    @Test
    public void createAnObject_myInt() {
        AnObject object = createAnObject(MY_INT, 1);
        assertEquals(Integer.valueOf(1), object.getMyInt());
    }

    @Test
    public void TEST__setNaturalIdTest__getNaturalIdTest() {
        ModelConfig config = ObjectProvider.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(NATURAL_ID, object, "test");
        Assertions.assertThat(((AnObject) object).getNaturalId()).isEqualTo("test");
        Assertions.assertThat(config.get(NATURAL_ID, object)).isEqualTo("test");
    }

    @Test
    public void TEST__setMyStringTest__getMyStringTest() {
        ModelConfig config = ObjectProvider.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(MY_STRING, object, "test");
        Assertions.assertThat(((AnObject) object).getMyString()).isEqualTo("test");
        Assertions.assertThat(config.get(MY_STRING, object)).isEqualTo("test");
    }
}

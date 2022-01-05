package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.FieldInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_AN_OBJECT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.NATURAL_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
    public void TEST__get_ShapeType__BEAN() {
        ModelBean modelBean = ProviderConfigMaps.createModelBean(AnObject.class);
        assertEquals(ShapeTypes.BEAN, modelBean.getShapeType());
        assertFalse(modelBean.getProperties().getFinal());
    }

    @Test
    public void TEST__toString__BEAN_AnObject() {
        ModelBean modelBean = ProviderConfigMaps.createModelBean(AnObject.class);
        assertEquals("(BEAN)AnObject", modelBean.toString());
    }

    @Test
    public void myString__toString__equalsPersisted() {
        FieldInterface field = ProviderConfigMaps.findModel(AnObject.class).getField("myString");
        XpectStringJunit4.assertStatic(field.toString());
    }

    @Test
    public void TEST__setNaturalIdTest__getNaturalIdTest() {
        ModelConfig config = ProviderConfigMaps.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(NATURAL_ID, object, "test");
        Assertions.assertThat(((AnObject) object).getNaturalId()).isEqualTo("test");
        Assertions.assertThat(config.get(NATURAL_ID, object)).isEqualTo("test");
    }

    @Test
    public void TEST__setMyStringTest__getMyStringTest() {
        ModelConfig config = ProviderConfigMaps.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(MY_STRING, object, "test");
        Assertions.assertThat(((AnObject) object).getMyString()).isEqualTo("test");
        Assertions.assertThat(config.get(MY_STRING, object)).isEqualTo("test");
    }

    @Test
    public void createByElement() {
        final String serialized = "{\n" +
                "  \"AnObject\": {\n" +
                "    \"configModelKey\": \"ModelConfigObject\",\n" +
                "    \"module\": \"elastic-objects-test\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": [\n" +
                "      \"id\",\n" +
                "      \"naturalId\",\n" +
                "      \"myAnObject\",\n" +
                "      \"myASubObject\",\n" +
                "      \"myASubObjectList\",\n" +
                "      \"myASubObjectMap\",\n" +
                "      \"myList\",\n" +
                "      \"myMap\",\n" +
                "      \"myInt\",\n" +
                "      \"myString\",\n" +
                "      \"myLong\",\n" +
                "      \"myDate\",\n" +
                "      \"myBoolean\",\n" +
                "      \"myFloat\",\n" +
                "      \"myDouble\",\n" +
                "      \"myObject\"\n" +
                "    ],\n" +
                "    \"expose\": \"WEB\",\n" +
                "    \"description\": \"Model class with different types of fields including generic collections, maps and @ASubObject objects.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.domain.test\",\n" +
                "    \"modelKey\": \"AnObject\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\"" +
                "  },\n" +
                "  \"ASubObject\": {\n" +
                "    \"configModelKey\": \"ModelConfigObject\",\n" +
                "    \"module\": \"elastic-objects-test\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": {\n" +
                "      \"id\": {\n" +
                "        \"final\": false,\n" +
                "        \"staticName\": false\n" +
                "      },\n" +
                "      \"naturalId\": {\n" +
                "        \"final\": false\n" +
                "      },\n" +
                "      \"myASubObject\": {\n" +
                "        \"final\": false\n" +
                "      },\n" +
                "      \"name\": {\n" +
                "        \"final\": false\n" +
                "      },\n" +
                "      \"myString\": {\n" +
                "        \"final\": false\n" +
                "      }\n" +
                "    },\n" +
                "    \"expose\": \"WEB\",\n" +
                "    \"description\": \"A sub object as an example.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.domain.test\",\n" +
                "    \"modelKey\": \"ASubObject\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1605826800000\n" +
                "  }\n" +
                "}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(AnObject.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(AnObject.class.getSimpleName(), bean.getModelKey());
        assertEquals(AnObject.class.getSimpleName(), bean.getNaturalId());
        assertEquals(MY_AN_OBJECT, bean.getFieldBean(MY_AN_OBJECT).getFieldKey());
        assertEquals(MY_AN_OBJECT, bean.getFieldBean(MY_AN_OBJECT).getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }
}

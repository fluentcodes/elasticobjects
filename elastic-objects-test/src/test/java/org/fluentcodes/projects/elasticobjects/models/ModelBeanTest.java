package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface.SHAPE_TYPE;

public class ModelBeanTest {
    @Test
    public void empty__toString__BEAN() {
        ModelBean model = new ModelBean();
        Assertions.assertThat(model.toString()).isEqualTo("(BEAN)");
    }

    @Test
    public void Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(BEAN)Model");
    }

    @Test
    public void modelKey_Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean();
        model.setModelKey("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(BEAN)Model");
    }

    @Test
    public void naturalId_Model__toString__BEAN_Model() {
        ModelBean model = new ModelBean();
        model.setNaturalId("Model");
        Assertions.assertThat(model.toString()).isEqualTo("(BEAN)Model");
    }

    @Test
    public void set_ShapeTypes_LIST__getShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.LIST);
     }

    @Test
    public void set_ShapeTypes_LIST__properties_get_ShapeType__LIST() {
        ModelBean modelBean = new ModelBean();
        modelBean.setShapeType(ShapeTypes.LIST);
        Assertions.assertThat(modelBean.getProperties().get(SHAPE_TYPE)).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void eo_set_ShapeTypes_LIST__get_ShapeType__LIST() {
        EO eo = ProviderRootTestScope.createEo(new ModelBean());
        eo.set(ShapeTypes.LIST, SHAPE_TYPE);
        Assertions.assertThat(eo.get(SHAPE_TYPE)).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void empty__getJavascriptType__EoInternalExcection() {
        ModelBean modelBean = new ModelBean();
        Assertions.assertThatThrownBy(()->{modelBean.getJavascriptType();})
                .isInstanceOf(EoInternalException.class);
    }

    @Test
    public void empty__modelKey_String__getJavascriptType__string() {
        ModelBean modelBean = new ModelBean();
        modelBean.setModelKey(String.class.getSimpleName());
        Assertions.assertThat(modelBean.getJavascriptType())
                .isEqualTo("string");
    }

    @Test
    public void empty__modelKey_Float__getJavascriptType__number() {
        ModelBean modelBean = new ModelBean();
        modelBean.setModelKey(Float.class.getSimpleName());
        Assertions.assertThat(modelBean.getJavascriptType())
                .isEqualTo("number");
    }

    @Test
    public void empty__setFinal_true__isFinal_true() {
        ModelBean modelBean = new ModelBean();
        modelBean.setFinal(true);
        Assertions.assertThat(modelBean.isFinal()).isTrue();
    }

    @Test
    public void empty__addField_test__getFieldBean_not_null() {
        ModelBean modelBean = new ModelBean();
        modelBean.addField("test");
        Assertions.assertThat(modelBean.getFieldBean("test")).isNotNull();
    }

}

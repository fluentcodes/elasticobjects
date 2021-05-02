package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface.SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.GENERATED;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.NOT_NULL;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.OVERRIDE;

public class FieldBeanTest {
    @Test
    public void empty__toString__empty() {
        FieldBean fieldBean = new FieldBean();
        Assertions.assertThat(fieldBean.toString()).isEqualTo("");
    }

    @Test
    public void naturalId_test__toString__test() {
        FieldBean fieldBean = new FieldBean("test");
        Assertions.assertThat(fieldBean.toString()).isEqualTo("test");
    }

    @Test
    public void naturalId_field_modelKeys_String__toString__String_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setModelKeys("String");
        Assertions.assertThat(fieldBean.toString()).isEqualTo("(String)field");
    }

    @Test
    public void naturalId_field_modelKeys_String_modelBean_Model__toString__String_Model_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setParentModel(new ModelBean("Model"));
        fieldBean.setModelKeys("String");
        Assertions.assertThat(fieldBean.toString()).isEqualTo("(String)Model.field");
    }

    @Test
    public void naturalId_field_modelBean_Model__toString__Model_field() {
        FieldBean fieldBean = new FieldBean("field");
        fieldBean.setParentModel(new ModelBean("Model"));
        Assertions.assertThat(fieldBean.toString()).isEqualTo("Model.field");
    }

    @Test
    public void fieldKey_field_modelBean_Model__toString__Model_field() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setFieldKey("field");
        Assertions.assertThat(fieldBean.toString()).isEqualTo("field");
    }


    @Test
    public void set_notNull_true__get__true() {
        EO eo = ProviderRootTestScope.createEo();
        FieldBean fieldBean = new FieldBean();
        fieldBean.setNotNull(true);
        Assertions.assertThat(fieldBean.getNotNull()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(NOT_NULL)).isTrue();
        eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(NOT_NULL)).isTrue();
    }

    @Test
    public void set_override_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setOverride(true);
        Assertions.assertThat(fieldBean.getOverride()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(OVERRIDE)).isTrue();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(OVERRIDE)).isTrue();
    }

    @Test
    public void set_generated_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setGenerated(true);
        Assertions.assertThat(fieldBean.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(GENERATED)).isTrue();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(GENERATED)).isTrue();
    }

    @Test
    public void set_generated_true__merge__true() {
        FieldBean fieldBeanOverwritten = new FieldBean();
        FieldBean fieldBean = new FieldBean();
        fieldBean.setGenerated(true);
        fieldBeanOverwritten.merge(fieldBean);
        Assertions.assertThat(fieldBean.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(GENERATED)).isTrue();
        Assertions.assertThat(fieldBeanOverwritten.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBeanOverwritten.getProperties().get(GENERATED)).isTrue();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(GENERATED)).isTrue();
    }

    @Test
    public void set_scope_DEV__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setScope(Arrays.asList(new Scope[]{Scope.DEV}));
        Assertions.assertThat(fieldBean.getScope()).isNotEmpty();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((List)eo.get(SCOPE)).isNotEmpty();
    }
}

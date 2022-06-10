package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_PROPERTIES;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_GENERATED;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_NOT_NULL;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_OVERRIDE;

public class FieldBeanTest {
    @Test
    public void set_notNull_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.getProperties().setNotNull(true);
        Assertions.assertThat(fieldBean.getProperties().getNotNull()).isTrue();
        EoRoot eo = ObjectProvider.createEo(fieldBean);
        Assertions.assertThat((Boolean) eo.get(F_PROPERTIES, F_NOT_NULL)).isTrue();
    }

    @Test
    public void set_override_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.getProperties().setOverride(true);
        Assertions.assertThat(fieldBean.getProperties().getOverride()).isTrue();
        EoRoot eo = ObjectProvider.createEo(fieldBean);
        Assertions.assertThat((Boolean) eo.get(F_PROPERTIES, F_OVERRIDE)).isTrue();
    }

    @Test
    public void set_generated_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.getProperties().setGenerated(true);
        Assertions.assertThat(fieldBean.getProperties().getGenerated()).isTrue();
        EoRoot eo = ObjectProvider.createEo(fieldBean);
        Assertions.assertThat((Boolean) eo.get(F_PROPERTIES, F_GENERATED)).isTrue();
    }


    @Test
    public void set_scope_DEV__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setScope(Arrays.asList(new Scope[]{Scope.DEV}));
        Assertions.assertThat(fieldBean.getScope()).isNotEmpty();
        EoRoot eo = ObjectProvider.createEo(fieldBean);
        Assertions.assertThat((List) eo.get(F_SCOPE)).isNotEmpty();
    }
}

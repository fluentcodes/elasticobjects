package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_PACKAGE_PATH;

public class ModelFactoryAllTest {
    public static Map<String, ModelBean> BEAN_MAP = new ModelFactoryAll(ObjectProvider.CONFIG_MAPS).createBeanMap();
    public static Map<String, ModelConfig> CONFIG_MAP = new ModelFactoryAll(ObjectProvider.CONFIG_MAPS).createConfigMap();

    @Test
    public void createBeanMap__get_Map__notNull() {
        ModelBean bean = BEAN_MAP
                .get(Map.class.getSimpleName());
        Assertions.assertThat(bean).isNotNull();
    }


    @Test
    public void TEST_modelBeanMapResolved__find_ModelBean__notNull() {
        ModelBean bean = BEAN_MAP
                .get(ModelBean.class.getSimpleName());
        Assertions.assertThat(bean.getField(F_PACKAGE_PATH)).isNotNull();
        Assertions.assertThat(bean.getField(F_MODULE_SCOPE)).isNull();
    }

    @Test
    public void TEST_modelConfigMapResolved__find_ModelBean__notNull() {
        ModelConfig config = CONFIG_MAP
                .get(ModelBean.class.getSimpleName());
        FieldConfig packagePathBean = config.getField(F_PACKAGE_PATH);
        Assertions.assertThat(packagePathBean).isNotNull();
    }



}

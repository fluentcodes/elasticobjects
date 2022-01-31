package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_MODEL_KEYS;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.CONFIG_MAPS;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.toStringWithMap;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProviderDev.createFieldBean;
import static org.junit.Assert.assertEquals;

/**
 * Created by Werner on 17.11.2021.
 */
public class FieldConfigTest implements IModelConfigCreateTests {

    public static FieldConfig createFieldConfig(final String fieldName, final Object value) {
        FieldBean bean = createFieldBean(fieldName, value);
        return new FieldConfigObject(bean, ObjectProviderDev.CONFIG_MAPS_DEV);
    }


    @Override
    public Class<?> getModelConfigClass() {
        return FieldConfig.class;
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
    public void find_description() {
        FieldConfig config = (FieldConfig) CONFIG_MAPS.find(FieldConfig.class, "description");
        assertThat(config).isNotNull();
    }

    @Test
    public void createFieldConfig_fieldKey() {
        FieldConfig config = createFieldConfig(F_FIELD_KEY, "NAME");
        assertEquals("NAME", config.getFieldKey());
    }

    @Test
    public void createFieldConfig_modelKeys() {
        FieldConfig config = createFieldConfig(F_MODEL_KEYS, "Map,String");
        assertEquals("Map,String", config.getModelKeys());
        assertEquals("{\"modelKeys\": \"Map,String\"}", toStringWithMap(config));
    }

    @Override
    public void create_noEoException() {
        assertCreateNoException();
    }
}

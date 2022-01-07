package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldInterface.F_MODEL_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ABSTRACT;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_SUPER_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.toStringWithMap;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelConfig;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelConfigWithFieldKey;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelConfigWithFieldProperty;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev.createModelConfigWithProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ModelConfigDevTest {

    @Test
    public void readModelBean() {
        ModelBean bean = ObjectProviderDev.readModelBean(ModelConfig.class.getSimpleName());
        assertEquals("Config", bean.getSuperKey());
        assertEquals(6, bean.getFieldKeys().size());
    }

    @Test
    public void readModelConfig() {
        ModelConfig config = ObjectProviderDev.readModelConfig(ModelConfig.class.getSimpleName());
        assertEquals(false, config.getProperties().getCreate());
        assertEquals(6, config.getFields().size());
    }

    @Test
    public void checkConfigMap_findModel_all() {
        ConfigMaps configMaps = ObjectProviderDev.CONFIG_MAPS_DEV;
        TreeSet<String> keys = new TreeSet<>(configMaps.getConfigKeys(ModelConfig.class));
        for (String key : keys) {
            ModelConfig model = configMaps.findModel(key);
            Assertions.assertThat(model).isNotNull();
        }
    }

    @Test
    public void createModelConfigWithProperty_abstract() {
        ModelConfig config = createModelConfigWithProperty( F_ABSTRACT, true);
        assertNotNull(config.getProperties());
        assertTrue(config.getProperties().getAbstract());
        assertEquals("{\"properties\": {\"abstract\": true}}",
                toStringWithMap(config));
    }

    @Test
    public void createModelConfigWithFieldProperty_final() {
        ModelConfig config = createModelConfigWithFieldProperty(F_MODEL_KEY, F_FINAL, true);
        assertNotNull(config.getFields());
        assertNotNull(config.getField(F_MODEL_KEY));
        assertTrue(config.getField(F_MODEL_KEY).getProperties().getFinal());
        assertEquals("{\"fields\": {\"modelKey\": {\"fieldKey\": \"modelKey\",\"naturalId\": \"modelKey\",\"properties\": {\"final\": true}}}}",
                toStringWithMap(config));
    }

    @Test
    public void createModelConfigWithField_fieldKey() {
        ModelConfig config = createModelConfigWithFieldKey(F_MODEL_KEY, F_MODEL_KEYS, "Map,String");
        assertNotNull(config.getFields());
        assertNotNull(config.getField(F_MODEL_KEY));
        assertEquals("Map,String", config.getField(F_MODEL_KEY).getModelKeys());
        assertEquals("{\"fields\": {\"modelKey\": {\"fieldKey\": \"modelKey\",\"modelKeys\": \"Map,String\",\"naturalId\": \"modelKey\"}}}",
                toStringWithMap(config));
    }

    @Test
    public void createModelConfig_interfaces() {
        ModelConfig  config = createModelConfig(F_INTERFACES, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces",  config.getInterfaces());
        assertEquals("{\"interfaces\": \"FieldBeanInterfaces\"}",
                toStringWithMap(config));
    }

    @Test
    public void createModelConfig_modelKey() {
        ModelConfig  config = createModelConfig(F_MODEL_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces",  config.getModelKey());
    }

    @Test
    public void createModelConfig_superKey() {
        ModelConfig  config = createModelConfig(F_SUPER_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces",  config.getSuperKey());
    }

    @Test
    public void createModelConfig_packagePath() {
        ModelConfig  config = createModelConfig(PACKAGE_PATH, "org.fluentcodes.projects.exlasticobjects");
        assertEquals("org.fluentcodes.projects.exlasticobjects",  config.getPackagePath());
    }

    @Test
    public void createModelConfig_expose() {
        ModelConfig  config = createModelConfig(F_EXPOSE, "WEB");
        assertEquals(Expose.WEB,  config.getExpose());
    }

    @Test
    public void createModelConfig_moduleScope() {
        ModelConfig  config = createModelConfig(F_MODULE_SCOPE, "MODULE_SCOPE");
        assertEquals("MODULE_SCOPE",  config.getModuleScope());
    }

    @Test
    public void createModelConfig_module() {
        ModelConfig  config = createModelConfig(F_MODULE, "MODULE");
        assertEquals("MODULE",  config.getModule());
    }

    @Test
    public void createModelConfig_scope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        ModelConfig  config = createModelConfig(F_SCOPE, "DEV,PROD");
        assertEquals(result,  config.getScope());
    }

    @Test
    public void createModelConfig_author() {
        ModelConfig  config = createModelConfig(F_AUTHOR, "Author");
        assertEquals("Author",  config.getAuthor());
    }

    @Test
    public void createModelConfig_creationDate() {
        ModelConfig  config = createModelConfig(F_CREATION_DATE, 1);
        assertEquals(new Date(1L),  config.getCreationDate());
    }

    @Test
    public void createModelConfig_id() {
        ModelConfig  config = createModelConfig(F_ID, 1);
        assertEquals(new Long(1L),  config.getId());
    }

    @Test
    public void createModelConfig_naturalId() {
        ModelConfig  config = createModelConfig(F_NATURAL_ID, "natural");
        assertEquals("natural",  config.getNaturalId());
    }
}

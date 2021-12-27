package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_SCOPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Werner on 13.4.2017.
 */
public class ConfigConfigTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return ConfigConfig.class;
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

    @Test
    public void checkConfigConfig_FieldExpose_FinalTrue() {
        ModelConfig config = ProviderConfigMaps.findModel(ConfigConfig.class);
        FieldConfig fieldConfig = config.getField(F_EXPOSE);
        assertTrue(fieldConfig.getFinal());
        Boolean property = fieldConfig.getProperty();
        assertFalse(property);
        XpectStringJunit4.assertStatic(fieldConfig.toString());
    }

    @Test
    public void createByElement() {
        final String serialized = "{ \"ConfigConfig\": {\n" +
                "    \"module\": \"elastic-objects\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": {\n" +
                "      \"expose\": {\n" +
                "        \"final\": true\n" +
                "      },\n" +
                "      \"module\": {\n" +
                "        \"final\": true\n" +
                "      },\n" +
                "      \"moduleScope\": {\n" +
                "        \"final\": true\n" +
                "      },\n" +
                "      \"scope\": {\n" +
                "        \"final\": true\n" +
                "      }\n" +
                "    },\n" +
                "    \"interfaces\": \"ConfigInterface\",\n" +
                "    \"superKey\": \"BaseConfig\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Basic cache as super object for other cached items. \",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.models\",\n" +
                "    \"modelKey\": \"ConfigConfig\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false,\n" +
                "      \"final\": true,\n" +
                "      \"override\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1608073200000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(ConfigConfig.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(ConfigConfig.class.getSimpleName(), bean.getModelKey());
        assertEquals(ConfigConfig.class.getSimpleName(), bean.getNaturalId());
        assertEquals(F_SCOPE, bean.getFieldBean(F_SCOPE).getFieldKey());
        assertEquals(F_SCOPE, bean.getFieldBean(F_SCOPE).getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }

}

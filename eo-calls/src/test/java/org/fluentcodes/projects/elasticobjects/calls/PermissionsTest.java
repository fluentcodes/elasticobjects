package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionsTest {
    @Test
    public void createByElement() {
        final String serialized = "{   \"Permissions\": {\n" +
                "    \"module\": \"eo-calls\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": {\n" +
                "      \"Permissions.create\": {\"final\": true},\n" +
                "      \"delete\": {\"final\": true},\n" +
                "      \"execute\": {\"final\": true},\n" +
                "      \"nothing\": {\"final\": true},\n" +
                "      \"read\": {\"final\": true},\n" +
                "      \"write\": {\"final\": true}\n" +
                "    },\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Config wrapper for permissions. \",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.calls\",\n" +
                "    \"modelKey\": \"Permissions\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false,\n" +
                "      \"final\": true,\n" +
                "      \"override\": true,\n" +
                "      \"abstract\": true\n" +
                "    },\n" +
                "    \"shapeType\": \"CONFIG\",\n" +
                "    \"creationDate\": 1608073200000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(Permissions.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(Permissions.class.getSimpleName(), bean.getModelKey());
        assertEquals(Permissions.class.getSimpleName(), bean.getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.CONFIG, bean.getShapeType());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }
}

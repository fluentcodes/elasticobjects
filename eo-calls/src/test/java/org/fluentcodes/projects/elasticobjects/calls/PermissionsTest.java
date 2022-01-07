package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionsTest {
    @Test
    public void createByElement() {
        final String serialized = "{   \"PermissionsForConfig\": {\n" +
                "    \"module\": \"eo-calls\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": {\n" +
                "      \"createRoles\": {\"final\": true},\n" +
                "      \"deleteRoles\": {\"final\": true},\n" +
                "      \"executeRoles\": {\"final\": true},\n" +
                "      \"nothingRoles\": {\"final\": true},\n" +
                "      \"readRoles\": {\"final\": true},\n" +
                "      \"writeRoles\": {\"final\": true}\n" +
                "    },\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Config wrapper for permission role lists.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.calls\",\n" +
                "    \"modelKey\": \"PermissionsForConfig\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false\n" +
                "    },\n" +
                "    \"shapeType\": \"CONFIG\",\n" +
                "    \"creationDate\": 1608073200000\n" +
                "  }}";
        EoRoot root = ObjectProviderDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(PermissionsForConfig.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(PermissionsForConfig.class.getSimpleName(), bean.getModelKey());
        assertEquals(PermissionsForConfig.class.getSimpleName(), bean.getNaturalId());

        bean.mergeFieldBeanMap(ObjectProviderDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.CONFIG, bean.getShapeType());
        bean.resolveSuper(ObjectProviderDev.assertCreateModelBeanMap(), true);

        ModelConfig modelConfig = ObjectProviderDev.createModelConfig(bean);
        modelConfig.resolve(ObjectProviderDev.createModelConfigMap());
    }
}

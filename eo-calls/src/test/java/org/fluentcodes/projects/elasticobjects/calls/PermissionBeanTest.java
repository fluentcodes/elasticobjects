package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PermissionBeanTest {
    @Test
    public void create() {
        PermissionBean bean = new PermissionBean();
        bean.setCreate(Arrays.asList("1", "2"));
        assertEquals(Arrays.asList("1", "2"), bean.getCreate());
    }

    @Test
    public void createByElement() {
        final String serialized = "{  \"PermissionBean\": {\n" +
                "    \"module\": \"eo-calls\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": \"Permissions.create, delete, execute, nothing, read, write\",\n" +
                "    \"superKey\": \"ConfigBean\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Container Bean for setting permission config.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.calls\",\n" +
                "    \"modelKey\": \"PermissionBean\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false,\n" +
                "      \"final\": true,\n" +
                "      \"override\": true,\n" +
                "      \"abstract\": true\n" +
                "    },\n" +
                "    \"shapeType\": \"BEAN\",\n" +
                "    \"creationDate\": 1608073200000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(PermissionBean.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(PermissionBean.class.getSimpleName(), bean.getModelKey());
        assertEquals(PermissionBean.class.getSimpleName(), bean.getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }
}

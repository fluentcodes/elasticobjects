package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_AUTHOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BaseInterfaceTest {

    @Test
    public void createByElement() {
        final String serialized = "{\"BaseInterface\": {\n" +
                "    \"module\": \"elastic-objects\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": \"id, description, naturalId, creationDate, author\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Basic bean as super interface for other cached items. \",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.domain\",\n" +
                "    \"modelKey\": \"BaseInterface\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false,\n" +
                "      \"final\": true\n" +
                "    },\n" +
                "    \"shapeType\": \"INTERFACE\",\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1608505200000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(BaseInterface.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(BaseInterface.class.getSimpleName(), bean.getModelKey());
        assertEquals(BaseInterface.class.getSimpleName(), bean.getNaturalId());
        assertEquals(ShapeTypes.INTERFACE, bean.getShapeType());
        assertEquals(F_AUTHOR, bean.getFieldBean(F_AUTHOR).getFieldKey());
        assertEquals(F_AUTHOR, bean.getFieldBean(F_AUTHOR).getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        assertEquals(ShapeTypes.INTERFACE, modelConfig.getShapeType());
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }
}

package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_NATURAL_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class BaseBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return BaseBean.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
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
    public void createByElement() {
        final String serialized = "{\"BaseBean\": {\n" +
                "    \"module\": \"elastic-objects\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": \"id, description, naturalId, creationDate, author\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Base bean as super object for model beans with id, naturalId and description but no annotations. \",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.domain\",\n" +
                "    \"modelKey\": \"BaseBean\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": true,\n" +
                "      \"override\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"creationDate\": 1482274800000\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(BaseBean.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(BaseBean.class.getSimpleName(), bean.getModelKey());
        assertEquals(BaseBean.class.getSimpleName(), bean.getNaturalId());
        assertEquals(F_AUTHOR, bean.getFieldBean(F_AUTHOR).getFieldKey());
        assertEquals(F_AUTHOR, bean.getFieldBean(F_AUTHOR).getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        assertEquals(new Integer(80), bean.getFieldBean(F_NATURAL_ID).getLength());
        assertEquals(true, bean.getFieldBean(F_NATURAL_ID).getUnique());
        assertEquals(true, bean.getFieldBean(F_NATURAL_ID).getNotNull());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);
        bean.setDefault();

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }

}

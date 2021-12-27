package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.CsvConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigInterface.F_SCOPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Werner on 11.10.2016.
 */
public class CsvConfigTest implements IModelConfigNoCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return CsvConfig.class;
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
    public void checkTargetCsv() {
        CsvConfig config = (CsvConfig) ProviderConfigMaps.CONFIG_MAPS
                .find(FileConfig.class, "target.csv");
        Assert.assertNotNull(config);
        Assert.assertTrue(config.getListParamsConfig().hasRowHead());
        Assert.assertFalse(config.getListParamsConfig().hasRowStart());
        Assert.assertFalse(config.getListParamsConfig().hasRowEnd());
        Assert.assertFalse(config.getListParamsConfig().hasLength());
        Assert.assertFalse(config.getListParamsConfig().hasFilter());
        Assert.assertFalse(config.getListParamsConfig().hasColKeys());
        Assert.assertEquals(new Integer(0), config.getListParamsConfig().getRowHead());
        Assert.assertNull(config.getListParamsConfig().getRowStart());
        Assert.assertNull(config.getListParamsConfig().getRowEnd());
        Assert.assertNull(config.getListParamsConfig().getLength());
        Assert.assertNull(config.getListParamsConfig().getFilter());
        Assert.assertEquals(new ArrayList<String>(), config.getListParamsConfig().getColKeys());
    }

    @Test
    public void createByElement() {
        final String serialized = "{  \"CsvConfig\": {\n" +
                "    \"module\": \"eo-calls\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"interfaces\": \"CsvConfigInterface,ListParamsConfigInterface\",\n" +
                "    \"superKey\": \"FileConfig\",\n" +
                "    \"expose\": \"NONE\",\n" +
                "    \"description\": \"Configuration for csv files with fieldDelimiter and rowDelimiter fields. \",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.calls.files\",\n" +
                "    \"modelKey\": \"CsvConfig\",\n" +
                "    \"properties\": {\n" +
                "      \"create\": false,\n" +
                "      \"final\": true\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\"\n" +
                "  }}";
        EoRoot root = ProviderConfigMapsDev.createEo(serialized);
        Map<String, Object> beanMap = (Map<String, Object>)root.get();
        Map<String, Object> modelConfigMap =  (Map<String, Object>)beanMap.get(CsvConfig.class.getSimpleName());
        ModelBean bean = new ModelBean(modelConfigMap);
        assertNotNull(bean);
        assertEquals(CsvConfig.class.getSimpleName(), bean.getModelKey());
        assertEquals(CsvConfig.class.getSimpleName(), bean.getNaturalId());

        bean.mergeFieldBeanMap(ProviderConfigMapsDev.createFieldBeanMap());
        bean.setDefault();
        assertEquals(ShapeTypes.BEAN, bean.getShapeType());
        bean.resolveSuper(ProviderConfigMapsDev.createModelBeanMap(), true);

        ModelConfig modelConfig = ProviderConfigMapsDev.createModelConfig(bean);
        modelConfig.resolve(ProviderConfigMapsDev.createModelConfigMap());
    }

}

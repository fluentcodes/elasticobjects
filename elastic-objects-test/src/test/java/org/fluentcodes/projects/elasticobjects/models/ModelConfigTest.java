package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_KEY_UNKNOW;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanTest.createModelBean;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;
import static org.junit.Assert.assertEquals;

/**
 * Created by Werner on 04.11.2016.
 */
public class ModelConfigTest implements IConfigurationTests {
    private static final Logger LOG = LogManager.getLogger(ModelConfigTest.class);

    public static ModelConfigObject createModelConfig(final String fieldName, final Object value){
        ModelBean bean = createModelBean(fieldName, value);
        return new ModelConfigObject(bean, ProviderConfigMapsDev.CONFIG_MAPS_DEV);
    }

    @Override
    public Class<?> getModelConfigClass() {
        return ModelConfig.class;
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
    public void scopeTest__findModel_Unknown__exception() {
        Assertions.assertThatThrownBy(() -> {
            ProviderConfigMaps.CONFIG_MAPS.findModel(SAMPLE_KEY_UNKNOW);
        })
                .isInstanceOf(EoException.class);
    }

    @Test
    public void checkDependentModels() {
        // Check if basic Models are available
        ModelConfig model = ProviderConfigMaps.CONFIG_MAPS.findModel(AnObject.class.getSimpleName());
        Assert.assertEquals(AnObject.class.getSimpleName(), model.getModelKey());
        model = ProviderConfigMaps.CONFIG_MAPS.findModel(ASubObject.class);
        Assert.assertEquals(ASubObject.class.getSimpleName(), model.getModelKey());
    }

    @Test
    public void testModelKey() {
        ModelConfig modelConfig = createModelConfig(MODEL_KEY, "\"FieldBeanInterfaces\"");
        assertEquals("FieldBeanInterfaces", modelConfig.getModelKey());
    }

    @Test
    public void testSuperKey() {
        ModelConfig modelConfig = createModelConfig(SUPER_KEY, "\"FieldBeanInterfaces\"");
        assertEquals("FieldBeanInterfaces", modelConfig.getSuperKey());
    }

    @Test
    public void testSuperKeyNull() {
        ModelConfig modelConfig = createModelConfig(SUPER_KEY, null);
        assertEquals(null, modelConfig.getSuperKey());
    }

    @Test
    public void testPackagePath() {
        ModelConfig modelConfig = createModelConfig(PACKAGE_PATH, "\"org.fluentcodes.projects.exlasticobjects\"");
        assertEquals("org.fluentcodes.projects.exlasticobjects", modelConfig.getPackagePath());
    }

    @Test
    public void testExpose() {
        ModelConfig modelConfig = createModelConfig(F_EXPOSE, "\"WEB\"");
        assertEquals(Expose.WEB, modelConfig.getExpose());
    }

    @Test
    public void testModuleScope() {
        ModelConfig modelConfig = createModelConfig(F_MODULE_SCOPE, "\"MODULE\"");
        assertEquals("MODULE", modelConfig.getModuleScope());
    }

    @Test
    public void testModule() {
        ModelConfig modelConfig = createModelConfig(F_MODULE, "\"MODULE\"");
        assertEquals("MODULE", modelConfig.getModule());
    }

    @Test
    public void testScope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        ModelConfig modelConfig = createModelConfig(F_SCOPE, "\"DEV,PROD\"");
        assertEquals(result, modelConfig.getScope());
    }

    @Test
    public void testAuthor() {
        ModelConfig modelConfig = createModelConfig(F_AUTHOR, "\"Author\"");
        assertEquals("Author", modelConfig.getAuthor());
    }

    @Test
    public void testCreationDate() {
        ModelConfig modelConfig = createModelConfig(F_CREATION_DATE, 1);
        assertEquals(new Date(1L), modelConfig.getCreationDate());
    }

    @Test
    public void testId() {
        ModelConfig modelConfig = createModelConfig(F_ID, 1);
        assertEquals(new Long(1L), modelConfig.getId());
    }

    @Test
    public void testNaturalId() {
        ModelConfig modelConfig = createModelConfig(F_NATURAL_ID, "\"natural\"");
        assertEquals("natural", modelConfig.getNaturalId());
    }
}

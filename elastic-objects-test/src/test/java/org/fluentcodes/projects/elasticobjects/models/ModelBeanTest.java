package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CONFIG_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanProperties.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.ModelBeanProperties.F_ABSTRACT;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.F_SUPER_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.SHAPE_TYPE;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.createModelBean;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.createModelBeanWithFieldKey;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.createModelBeanWithFieldProperty;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.createModelBeanWithProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ModelBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ModelBean.class;
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
    public void createModelBeanWithFieldKey_module() {
        ModelBean bean = createModelBeanWithFieldKey("fieldKey", F_MODULE, "moduleValue");
        assertNotNull(bean.getFields());
        assertNotNull(bean.getFieldBean("fieldKey"));
        assertEquals("moduleValue", bean.getFieldBean("fieldKey").getModule());
    }

    @Test
    public void createModelBeanWithFieldProperty_final() {
        ModelBean bean = createModelBeanWithFieldProperty(F_MODEL_KEY, F_FINAL, true);
        assertNotNull(bean.getFields());
        assertNotNull(bean.getFieldBean(F_MODEL_KEY));
        assertTrue(bean.getFieldBean(F_MODEL_KEY).getProperties().getFinal());
    }

    @Test
    public void createModelBeanWithProperty_abstract() {
        ModelBean bean = createModelBeanWithProperty(F_ABSTRACT, true);
        assertNotNull(bean.getProperties());
        assertTrue(bean.getProperties().getAbstract());
    }

    @Test
    public void eo_set_ShapeTypes_LIST() {
        EoRoot eo = ObjectProvider.createEo(new ModelBean());
        eo.set(ShapeTypes.LIST, SHAPE_TYPE);
        Assertions.assertThat(eo.get(SHAPE_TYPE)).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void createModelBean_modelKey() {
        ModelBean modelBean = createModelBean(F_MODEL_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces", modelBean.getModelKey());
    }

    @Test
    public void createModelBean_shapeType() {
        ModelBean modelBean = createModelBean(SHAPE_TYPE, "STRING");
        assertEquals(ShapeTypes.STRING, modelBean.getShapeType());
    }

    @Test
    public void createModelBean_superKey() {
        ModelBean modelBean = createModelBean(F_SUPER_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces", modelBean.getSuperKey());
    }

    @Test(expected = EoException.class)
    public void createModelBean_superKeyNull() {
        ModelBean modelBean = createModelBean(F_SUPER_KEY, null);
    }

    @Test
    public void createModelBean_packagePath() {
        ModelBean modelBean = createModelBean(PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        assertEquals("org.fluentcodes.projects.elasticobjects", modelBean.getPackagePath());
    }

    @Test
    public void createModelBean_configModelKey() {
        ModelBean modelBean = createModelBean(F_CONFIG_MODEL_KEY, "CONFIG");
        assertEquals("CONFIG", modelBean.getConfigModelKey());
    }

    @Test
    public void createModelBean_expose() {
        ModelBean modelBean = createModelBean(F_EXPOSE, "WEB");
        assertEquals(Expose.WEB, modelBean.getExpose());
    }

    @Test
    public void createModelBean_moduleScope() {
        ModelBean modelBean = createModelBean(F_MODULE_SCOPE, "MODULE_SCOPE");
        assertEquals("MODULE_SCOPE", modelBean.getModuleScope());
    }

    @Test
    public void createModelBean_module() {
        ModelBean modelBean = createModelBean(F_MODULE, "MODULE");
        assertEquals("MODULE", modelBean.getModule());
    }

    @Test
    public void createModelBean_scope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        ModelBean modelBean = createModelBean(F_SCOPE, "[\\\"DEV\\\",\\\"PROD\\\"]");
        assertEquals(result, modelBean.getScope());
    }

    @Test
    public void createModelBean_author() {
        ModelBean modelBean = createModelBean(F_AUTHOR, "Author");
        assertEquals("Author", modelBean.getAuthor());
    }

    @Test
    public void createModelBean_creationDate() {
        ModelBean modelBean = createModelBean(F_CREATION_DATE, 1L);
        assertEquals(new Date(1L), modelBean.getCreationDate());
    }

    @Test
    public void createModelBean_id() {
        ModelBean modelBean = createModelBean(F_ID, Long.valueOf(1L));
        assertEquals(Long.valueOf(1L), modelBean.getId());
    }

    @Test
    public void createModelBean_naturalId() {
        ModelBean modelBean = createModelBean(F_NATURAL_ID, "natural");
        assertEquals("natural", modelBean.getNaturalId());
    }

}

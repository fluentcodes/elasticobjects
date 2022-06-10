package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
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
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_SUPER_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelBean.F_PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelInterface.F_SHAPE_TYPE;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.createModelBean;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.createModelBeanWithFieldKey;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.createModelBeanWithFieldProperty;
import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.createModelBeanWithProperty;
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
        assertNotNull(bean.getField("fieldKey"));
        assertEquals("moduleValue", bean.getField("fieldKey").getModule());
    }

    @Test
    public void createModelBeanWithFieldProperty_final() {
        ModelBean bean = createModelBeanWithFieldProperty(F_MODEL_KEY, F_FINAL, true);
        assertNotNull(bean.getFields());
        assertNotNull(bean.getField(F_MODEL_KEY));
        assertTrue(bean.getField(F_MODEL_KEY).getProperties().getFinal());
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
        eo.set(ShapeTypes.LIST, F_SHAPE_TYPE);
        Assertions.assertThat(eo.get(F_SHAPE_TYPE)).isEqualTo(ShapeTypes.LIST);
    }

    @Test
    public void createModelBean_modelKey() {
        ModelBean bean = createModelBean(F_MODEL_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces", bean.getModelKey());
    }

    @Test
    public void createModelBean_shapeType() {
        ModelBean bean = createModelBean(F_SHAPE_TYPE, "STRING");
        assertEquals(ShapeTypes.STRING, bean.getShapeType());
    }

    @Test
    public void createModelBean_superKey() {
        ModelBean bean = createModelBean(F_SUPER_KEY, "FieldBeanInterfaces");
        assertEquals("FieldBeanInterfaces", bean.getSuperKey());
    }

    @Test
    public void createModelBean_superKeyNull() {
        ModelBean bean = createModelBean(F_SUPER_KEY, null);
        assertNull(bean.getSuperKey());
    }

    @Test
    public void createModelBean_packagePath() {
        ModelBean bean = createModelBean(F_PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        assertEquals("org.fluentcodes.projects.elasticobjects", bean.getPackagePath());
    }

    @Test
    public void createModelBean_configModelKey() {
        ModelBean bean = createModelBean(F_CONFIG_MODEL_KEY, "CONFIG");
        assertEquals("CONFIG", bean.getConfigModelKey());
    }

    @Test
    public void createModelBean_expose() {
        ModelBean bean = createModelBean(F_EXPOSE, "WEB");
        assertEquals(Expose.WEB, bean.getExpose());
    }

    @Test
    public void createModelBean_moduleScope() {
        ModelBean bean = createModelBean(F_MODULE_SCOPE, "MODULE_SCOPE");
        assertEquals("MODULE_SCOPE", bean.getModuleScope());
    }

    @Test
    public void createModelBean_module() {
        ModelBean bean = createModelBean(F_MODULE, "MODULE");
        assertEquals("MODULE", bean.getModule());
    }

    @Test
    public void createModelBean_scope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        ModelBean bean = createModelBean(F_SCOPE, "[\\\"DEV\\\",\\\"PROD\\\"]");
        assertEquals(result, bean.getScope());
    }

    @Test
    public void createModelBean_author() {
        ModelBean bean = createModelBean(F_AUTHOR, "Author");
        assertEquals("Author", bean.getAuthor());
    }

    @Test
    public void createModelBean_creationDate() {
        ModelBean bean = createModelBean(F_CREATION_DATE, 1L);
        assertEquals(new Date(1L), bean.getCreationDate());
    }

    @Test
    public void createModelBean_id() {
        ModelBean bean = createModelBean(F_ID, Long.valueOf(1L));
        assertEquals(Long.valueOf(1L), bean.getId());
    }

    @Test
    public void createModelBean_naturalId() {
        ModelBean bean = createModelBean(F_NATURAL_ID, "natural");
        assertEquals("natural", bean.getNaturalId());
    }

}

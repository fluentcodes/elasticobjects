package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
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
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider.createObject;
import static org.junit.Assert.assertEquals;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ConfigBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ConfigBean.class;
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
    public void createBean_ModelKey() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_CONFIG_MODEL_KEY, "CONFIG");
        assertEquals("CONFIG", fieldBean.getConfigModelKey());
    }

    @Test
    public void createBean_Expose() {
        FieldBean fieldBean = (FieldBean) createObject(FieldBean.class, "WEB", F_EXPOSE);
        assertEquals(Expose.WEB, fieldBean.getExpose());
    }

    @Test
    public void createBean_ModuleScope() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_MODULE_SCOPE, "MODULE_SCOPE");
        assertEquals("MODULE_SCOPE", fieldBean.getModuleScope());
    }

    @Test
    public void createBean_Module() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_MODULE, "MODULE");
        assertEquals("MODULE", fieldBean.getModule());
    }

    @Test
    public void createBean_Scope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_SCOPE, "DEV,PROD");
        assertEquals(result, fieldBean.getScope());
    }

    @Test
    public void createBean_Author() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_AUTHOR, "Author");
        assertEquals("Author", fieldBean.getAuthor());
    }

    @Test
    public void createBean_CreationDate() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_CREATION_DATE, 1);
        assertEquals(new Date(1L), fieldBean.getCreationDate());
    }

    @Test
    public void createBean_Id() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_ID, 1);
        assertEquals(new Long(1L), fieldBean.getId());
    }

    @Test
    public void createBean_NaturalId() {
        FieldBean fieldBean = ObjectProviderDev.createFieldBean(F_NATURAL_ID, "natural");
        assertEquals("natural", fieldBean.getNaturalId());
    }
}

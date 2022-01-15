package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProviderDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigBean.F_SCOPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Werner on 13.4.2017.
 */
public class ConfigTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return Config.class;
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
    public void fieldConfig_expose() {
        ModelConfig config = ObjectProvider.findModel(Config.class);
        FieldConfig fieldConfig = config.getField(F_EXPOSE);
        assertTrue(fieldConfig.getProperties().getFinal());
        Boolean property = fieldConfig.getProperties().getProperty();
        assertFalse(property);
        XpectStringJunit4.assertStatic(fieldConfig.toString());
    }

    @Test
    public void createConfig_Expose() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_EXPOSE, "WEB");
        assertEquals(Expose.WEB, config.getExpose());
    }

    @Test
    public void createConfig_ModuleScope() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_MODULE_SCOPE, "MODULE_SCOPE");
        assertEquals("MODULE_SCOPE", config.getModuleScope());
    }

    @Test
    public void createConfig_Module() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_MODULE, "MODULE");
        assertEquals("MODULE", config.getModule());
    }

    @Test
    public void createConfig_Scope() {
        List result = new ArrayList();
        result.add(Scope.DEV);
        result.add(Scope.PROD);
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_SCOPE, "DEV,PROD");
        assertEquals(result, config.getScope());
    }

    @Test
    public void createConfig_Author() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_AUTHOR, "Author");
        assertEquals("Author", config.getAuthor());
    }

    @Test
    public void createConfig_CreationDate() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_CREATION_DATE, 1);
        assertEquals(new Date(1L), config.getCreationDate());
    }

    @Test
    public void createConfig_Id() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_ID, 1);
        assertEquals(new Long(1L), config.getId());
    }

    @Test
    public void createConfig_NaturalId() {
        FieldConfig config = ObjectProviderDev.createFieldConfig(F_NATURAL_ID, "natural");
        assertEquals("natural", config.getNaturalId());
    }

}

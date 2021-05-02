package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 27.8.2018.
 */
public class FieldConfigTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(FieldConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(FieldConfig.class);
    }

    @Test
    public void TEST__findModel_FieldConfig__$()  {
        final ModelConfig fieldModel = ProviderRootTestScope.EO_CONFIGS.findModel(FieldConfig.class);
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }

    @Test
    public void TEST__findModel_FieldConfigInterface__getProperty_()  {
        final ModelConfig fieldConfigInterfaceModel = ProviderRootTestScope.EO_CONFIGS.findModel(FieldConfigInterface.class);
        Assert.assertEquals(FieldConfigInterface.class.getSimpleName(), fieldConfigInterfaceModel.getModelKey());
        Assert.assertEquals(FieldConfigInterface.class, fieldConfigInterfaceModel.getModelClass());
    }

    @Test
    public void TEST__findModel_FieldBeanInterface__getProperty_()  {
        final ModelConfig fieldBeanInterfaceModel = ProviderRootTestScope.EO_CONFIGS.findModel(FieldBeanInterface.class);
        Assert.assertEquals(FieldBeanInterface.class.getSimpleName(), fieldBeanInterfaceModel.getModelKey());
        Assert.assertEquals(FieldBeanInterface.class, fieldBeanInterfaceModel.getModelClass());
    }

}

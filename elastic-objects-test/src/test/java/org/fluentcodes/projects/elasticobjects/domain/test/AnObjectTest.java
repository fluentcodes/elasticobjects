package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

public class AnObjectTest {

    @Test
    public void DEV__findModel__exception()  {
        try {
            ModelConfigInterface model = ProviderRootDevScope.EO_CONFIGS.findModel(AnObject.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        }
        catch(EoException e) {

        }
    }

    @Test
    public void TEST__findModel__$()  {
         Assertions.assertThat(ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class)).isNotNull();
    }

    @Test
    public void TEST__findField_myObject__$()  {
        FieldConfig field = ProviderRootTestScope.EO_CONFIGS.findField(AnObject.MY_OBJECT);
        Assert.assertEquals(AnObject.MY_OBJECT, field.getFieldKey());
        Assert.assertEquals(false, field.getUnique());
        Assert.assertEquals(false, field.getNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Object.class, field.getModelClass());
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(AnObject.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(AnObject.class);
    }

}
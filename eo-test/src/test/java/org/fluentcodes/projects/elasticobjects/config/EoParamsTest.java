package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderConfig;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class EoParamsTest {

    @Test
    public void initDbParams() {
        EOParams eoParams = new EOParams(TestProviderConfig.createEoParams());
        Assert.assertEquals(F_MODEL_CONFIG_KEY, eoParams.getModelConfigKey());
        Assert.assertEquals(F_DEFAULT_IMPLEMENTATION, eoParams.getDefaultImplementation());
        Assert.assertEquals(ShapeTypes.MAP, eoParams.getShapeType());
        Assert.assertTrue(eoParams.isCreate());
        Assert.assertEquals(F_METHODS, eoParams.getMethods().get(0));
        Assert.assertEquals(F_ATTRIBUTE_LIST, eoParams.getAttributeList().get(0));
        Assert.assertEquals(Scope.ALL, eoParams.getScope().get(0));
        Assert.assertEquals(Path.DELIMITER + F_PATH_PATTERN, eoParams.getPathPatternAsString());
    }

}

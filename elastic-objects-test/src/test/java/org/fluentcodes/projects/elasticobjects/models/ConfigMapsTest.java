package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ObjectProvider;
import org.junit.Test;

public class ConfigMapsTest {

    @Test(expected = EoException.class)
    public void ModelNotExisting_EoException() {
        ObjectProvider.CONFIG_MAPS.findModel("Nonsense");
    }
}

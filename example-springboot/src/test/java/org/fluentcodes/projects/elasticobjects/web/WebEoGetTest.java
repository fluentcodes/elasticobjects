package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class WebEoGetTest {

    @Test
    public void createConfigStartPage__ModelConfig__xpected() {
        WebEoGet webEoGet = new WebEoGet(ProviderRootTestScope.EO_CONFIGS);
        String value = webEoGet.createConfigStartPage("ModelConfig");
    }
}

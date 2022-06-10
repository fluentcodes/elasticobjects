package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

public class WebEoGetTest {

    @Test
    public void createConfigStartPage__ModelConfig__xpected() {
        WebEoGet webEoGet = new WebEoGet(ObjectProvider.CONFIG_MAPS);
        String value = webEoGet.createConfigStartPage("ModelConfig");
    }
}

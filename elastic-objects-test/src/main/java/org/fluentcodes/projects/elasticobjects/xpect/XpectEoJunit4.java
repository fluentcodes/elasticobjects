package org.fluentcodes.projects.elasticobjects.xpect;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider.CONFIG_MAPS;

public class XpectEoJunit4 extends XpectEo {
    public XpectEoJunit4(Class<?>... classes) {
        super(CONFIG_MAPS, Test.class, classes);
    }

    public static File assertStatic(final Object toCompare) {
        XpectEoJunit4 xpect = new XpectEoJunit4(toCompare.getClass());
        return xpect.assertEquals(toCompare);
    }

    public static File assertStaticEO(final Object toCompare) {
        XpectEoJunit4 xpect = new XpectEoJunit4(toCompare.getClass());
        xpect.setSerializationType(JSONSerializationType.EO);
        return xpect.assertEquals(toCompare);
    }

    @Override
    public File assertEquals(final Object toCompare) {
        Assert.assertEquals(load(toCompare), getIo().asString(toCompare));
        return new File(getIo().getFileName());
    }
}

package org.fluentcodes.projects.elasticobjects.xpect;

import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class XpectStringJunit4 extends XpectString {
    public XpectStringJunit4() {
        super(Test.class);
    }

    public static File assertStatic(final String toCompare) {
        XpectStringJunit4 xpect = new XpectStringJunit4();
        return xpect.assertEquals(toCompare);
    }

    @Override
    public File assertEquals(final Object toCompare) {
        Assert.assertEquals(load(toCompare), toCompare);
        return new File(getIo().getFileName());
    }
}

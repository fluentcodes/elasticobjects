package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.BEO_STATIC;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.XEO_STATIC;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created 7.6.2018
 */
public class CreatorCallsListTest extends CreatorConfigs {

    @Test
    public void xlsxXEOTest()  {
        String result = createJsonConfig(BEO_STATIC.XLSX_XLSX, XEO_STATIC.MODULE_NAME, EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

    // Not used yet
    @Ignore
    @Test
    public void xlsxXEOMain()  {
        String result = createJsonConfig(BEO_STATIC.XLSX_XLSX, XEO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void xlsxBEOMain()  {
        String result = createJsonConfig(BEO_STATIC.XLSX_XLSX, BEO_STATIC.MODULE_NAME, EO_STATIC.MAIN);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void csvCEOTest()  {
        String result = createJsonConfig(BEO_STATIC.CSV_XLSX, "actions-csv", EO_STATIC.TEST);
        Assert.assertTrue(result.isEmpty());
    }

}
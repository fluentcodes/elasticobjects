package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.H_LOCALHOST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.CONFIG_XLSX_TEST;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC_TEST.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class XlsxConfigTest {

    @Test
    public void findConfigInCache()  {
        final XlsxConfig config = (XlsxConfig) ProviderRootTestScope.EO_CONFIGS.find(XlsxConfig.class, X_SOURCE_XLSX_TEST);
        Assert.assertNotNull(config.getDescription());
        Assert.assertEquals(FILE_SOURCE_XLSX, config.getFileConfig().getFileName());
    }

    @Test
    public void readMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_XLSX_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
    }

    @Test
    public void readConfigTest()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_XLSX_TEST, XlsxConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
    }

    @Test
    public void readSourceXlsx()  {
        final XlsxConfig config = (XlsxConfig) ProviderRootTestScope.EO_CONFIGS.find(XlsxConfig.class, FILE_SOURCE_XLSX);
        Assert.assertEquals(FILE_SOURCE_XLSX, config.getXlsxKey());
        Assert.assertEquals(X_SHEET_TEST, config.getSheetName());
        Assert.assertEquals(FILE_SOURCE_XLSX, config.getFileKey());
        Assert.assertEquals(FILE_SOURCE_XLSX, config.getFileConfig().getFileName());
        Assert.assertEquals(H_LOCALHOST, config.getFileConfig().getHostConfig().getHostName());
        //List list = config.createIO().read(new ListParams());
        //Assert.assertEquals(2, list.size());
        //Assert.assertEquals(2, ((List) list.get(0)).size());
    }


    @Test
    public void checkTarget()  {
        
        final XlsxConfig config = (XlsxConfig) ProviderRootTestScope.EO_CONFIGS
                .find(XlsxConfig.class, FILE_TARGET_XLSX);
        Assert.assertEquals(FILE_TARGET_XLSX, config.getXlsxKey());
        Assert.assertEquals(FILE_TARGET_XLSX, config.getFileKey());
        Assert.assertNull(config.getSheetName());

        FileConfig fileConfig = config.getFileConfig();
        Assert.assertEquals(FILE_TARGET_XLSX, fileConfig.getFileKey());
        Assert.assertEquals(FILE_TARGET_XLSX, fileConfig.getFileName());
        Assert.assertEquals(PATH_TMP, fileConfig.getFilePath());

        HostConfig hostConfig = fileConfig.getHostConfig();
        Assert.assertEquals(H_LOCALHOST, hostConfig.getHostKey());

    }

}

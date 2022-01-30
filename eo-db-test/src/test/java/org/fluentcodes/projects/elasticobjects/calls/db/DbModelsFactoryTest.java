package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.db.DbModelQueryCallAnObjectTest.H_2_MEM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DbModelsFactoryTest {
    @Test
    public void beanMap_h2Mem() {
        Map<String, DbModelsBean> dbModelsBeanMap = new DbModelsFactory(ObjectProvider.CONFIG_MAPS).createBeanMap();
        DbModelsBean bean = dbModelsBeanMap.get(H_2_MEM);
        assertNull(bean.getDbModelKeys());
    }

    @Test
    public void newConfig_h2Mem() {
        DbModelsBean bean = new DbModelsFactory(ObjectProvider.CONFIG_MAPS).createBeanMap().get(H_2_MEM);
        DbModelsConfig config = new DbModelsConfig(bean, ObjectProvider.CONFIG_MAPS);
        assertEquals(2, config.getDbModelKeys().size());
    }

    @Test
    public void createConfig_h2Mem() {
        DbModelsBean bean = new DbModelsFactory(ObjectProvider.CONFIG_MAPS).createBeanMap().get(H_2_MEM);
        DbModelsConfig config = (DbModelsConfig)bean.createConfig(ObjectProvider.CONFIG_MAPS);
        assertEquals(2, config.getDbModelKeys().size());
    }
}

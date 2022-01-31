package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DbSqlFactoryTest {

    @Test
    public void createBeanMap() {
        DbSqlFactory beanMap = new DbSqlFactory(ObjectProvider.CONFIG_MAPS);
        List<DbSqlBean> list = beanMap.createBeanList();
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    public void createConfigMap() {
        DbSqlFactory beanMap = new DbSqlFactory(ObjectProvider.CONFIG_MAPS);
        Map<String, DbSqlConfig> map = beanMap.createConfigMap();
        assertThat(map.size()).isEqualTo(4);
    }

    @Test
    public void getDbFromConfigMap() {
        DbSqlConfig config = (DbSqlConfig) ObjectProvider.CONFIG_MAPS.find(DbSqlConfig.class, "h2:mem:basic:AnObject");
        assertThat(config).isNotNull();
    }

}

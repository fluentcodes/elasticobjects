package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlExecuteCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParamsBean;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class FindStatementTest {
    @Before
    public void init() {
        EoRoot eo = ProviderConfigMaps.createEo();
        Call call = new DbSqlExecuteCall(DbConfig.H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void anObject__readFirst__1() {
        EoRoot eo = ProviderConfigMaps.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigMaps().find(HostConfig.class, DbConfig.H2_BASIC);
        List values = findStatement.readFirst(dbConfig.getConnection(), eo.getConfigMaps());//, new ListParams().setRowHead(0).setRowStart(0));
        Assertions.assertThat(values).isNotEmpty();
        Assertions.assertThat(values.size()).isEqualTo(1);
    }

    @Test
    public void anObject__read__3() {
        EoRoot eo = ProviderConfigMaps.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigMaps().find(HostConfig.class, DbConfig.H2_BASIC);
        List values = findStatement.read(dbConfig.getConnection(), eo.getConfigMaps(), new ListParamsBean().setRowHead(0).setRowStart(0).setRowEnd(10));
        Assertions.assertThat(values).isNotEmpty();
        Assertions.assertThat(values.size()).isEqualTo(3);
    }

    @Test
    public void anObject__execute__1() {
        EoRoot eo = ProviderConfigMaps.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigMaps().find(HostConfig.class, DbConfig.H2_BASIC);
        int value = findStatement.execute(dbConfig.getConnection());
        Assertions.assertThat(value).isEqualTo(1);
    }

    @Test
    public void anObject_myString_notExist__execute__0() {
        EoRoot eo = ProviderConfigMaps.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject where myString='notExist'", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigMaps().find(HostConfig.class, DbConfig.H2_BASIC);
        int value = findStatement.execute(dbConfig.getConnection());
        Assertions.assertThat(value).isEqualTo(0);
    }

    @Test
    public void anObject_myString_value1__execute__1() {
        EoRoot eo = ProviderConfigMaps.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject where myString='value1'", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigMaps().find(HostConfig.class, DbConfig.H2_BASIC);
        int value = findStatement.execute(dbConfig.getConnection());
        Assertions.assertThat(value).isEqualTo(1);
    }
}

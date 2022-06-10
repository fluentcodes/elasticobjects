package org.fluentcodes.projects.elasticobjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlExecuteCall;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * Created by werner.diwischek on 11.12.17.
 */

//https://stackoverflow.com/questions/24661289/spring-boot-not-serving-static-content
@SpringBootApplication
public class Start {
    @Autowired
    private ConfigMaps configsCache;

    private static final Logger LOG = LogManager.getLogger(Start.class);
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }


    @PostConstruct
    public void init() {
        LOG.info("Init Database");
        System.out.println("Init Database");
        EoRoot eo = EoRoot.of(configsCache);
        Call call = new DbSqlExecuteCall("h2:mem:basic", "h2:mem:basic:Create");
        call.execute(eo);
    }
}


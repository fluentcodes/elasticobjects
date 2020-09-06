package org.fluentcodes.projects.elasticobjects.config;

import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.BEO_STATIC.X_CALLS_DB;
import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.MAIN;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.TEST;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.*;

public class CompareCallsDbTest extends CompareConfigs {
    /**
     * Xlsx
     */

    public static final String SHEET = X_CALLS_DB;

    @Test
    public void dbDBMain()  {
        compare(SHEET, DB, ACTIONS_DB, MAIN);
    }

    @Test
    public void dbDBTest()  {
        compare(SHEET, DB, ACTIONS_DB, TEST);
    }

    @Test
    public void dbHDBTest()  {
        compare(SHEET, DB, ACTIONS_HDB, TEST);
    }

    @Test
    public void dbDBQueryTest()  {
        compare(SHEET, DB_QUERY, ACTIONS_DB, TEST);
    }

    @Test
    public void dbDBSqlTest()  {
        compare(SHEET, DB_SQL, ACTIONS_DB, TEST);
    }

    @Test
    public void dbHTest()  {
        compare(SHEET, H, ACTIONS_HDB, TEST);
    }

    @Test
    public void dbHQueryTest()  {
        compare(SHEET, H_QUERY, ACTIONS_HDB, TEST);
    }

    @Test
    public void dbHSqlTest()  {
        compare(SHEET, H_SQL, ACTIONS_HDB, TEST);
    }


}
package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestCsvProvider;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.ListProviderJSON;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.CSV_TARGET_CSV;

/**
 * Created by Werner on 08.10.2016.
 */
public class CsvCallWithExecutorWriteTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(CsvCallWithExecutorWriteTest.class);

    @Test
    public void direct() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = TestEOProvider.createEOBuilder()
                .map(ListProviderJSON.createJsonArray());
        final CallExecutor executor = TestCsvProvider.createCallExecutorWrite(CSV_TARGET_CSV);
        executor.execute(adapter);
        //TODO AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, executor);
    }

}

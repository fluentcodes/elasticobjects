package org.fluentcodes.projects.stadtbook.calls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitems.ObjectProvider;
import org.fluentcodes.projects.stadtbook.calls.postaladdress.ParserPostalAddressCall;
import org.fluentcodes.projects.stadtbook.calls.postaladdress.ParserPostalAddressInMunchenCall;
import org.fluentcodes.projects.stadtbook.calls.postaladdress.PostalAddressReadAndPersistCall;
import org.fluentcodes.projects.stadtbook.parser.DateConvert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostalAddressReadAndPersistIT {
  private static final Logger LOGGER = LoggerFactory.getLogger(PostalAddressReadAndPersistIT.class);

  @Test
  public void loadMonth() {
    loadMonth(new ParserPostalAddressInMunchenCall(), Month.JULY);
  }

  private void loadMonth(ParserPostalAddressCall parserCall, Month month)  {
    LocalDate startDate = LocalDate.of(2022,month,1);
    LocalDate stopDate = startDate.plusMonths(1);
    parseSimple(parserCall, ParserAction.READ_PERSIST, startDate, stopDate, true);
  }

  private void parseSimple(ParserPostalAddressCall parserCall, ParserAction action, LocalDate startDate, LocalDate stopDate, boolean test)  {
    LOGGER.info(parserCall.getClass().getSimpleName() + ": " + action);
    PostalAddressReadAndPersistCall call = new PostalAddressReadAndPersistCall()
        .setParserCall(parserCall);
    if (action == ParserAction.READ_PERSIST) {
      call
          .setReadXlsx(true)
          .setPersistDb(true);
    }
    call
        .setStartDate(startDate)
        .setStopDate(stopDate)
        .setTest(test);
    EoRoot eo = ObjectProvider.createEo();
    call.execute(eo);
  }
}

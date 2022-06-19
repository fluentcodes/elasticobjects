package org.fluentcodes.projects.stadtbook.calls.postaladdress;

import static org.fluentcodes.projects.stadtbook.calls.CreateDayCall.DB_H2_FILE;
import static org.fluentcodes.projects.stadtbook.calls.EventCall.INPUT_DIR;

import io.netty.channel.local.LocalAddress;
import java.util.List;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.db.DbModelWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlReadCall;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.stadtbook.domain.PostalAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostalAddressReadAndPersistCall extends PostalAddressCall {
  private static final Logger LOGGER = LoggerFactory.getLogger(PostalAddressReadAndPersistCall.class);
  public static final String DB_MODELS_H2_FILE = "h2:file";
  public static final String EVENTS = "events";
  public static final String LIST_POSTAL_ADDRESS = "/(List,PostalAddress)address";
  public static final String PATH = "/(List,PostalAddress)postalAddress";
  public static final String POSTAL_ADDRESS_XLSX = "PostalAddress.xlsx";
  public static final String LOCATIONS = "locations";
  private ParserPostalAddressCall parserCall;
  private Boolean persistXlsx = false;
  private Boolean persistDb = false;
  private boolean readXlsx = false;
  public PostalAddressReadAndPersistCall() {
    super();
  }

  public Object execute(EOInterfaceScalar eo) {
    if (parserCall == null) {
      throw new EoException("No parser defined");
    }
    check();
    DbSqlReadCall call = new DbSqlReadCall(DB_H2_FILE, "AllPostalAddress");
    call.getListParams().setRowEnd(100000);
    call.setTargetPath(PATH);
    call.execute(eo);
    List<PostalAddress> events = (List<PostalAddress>)eo.get(PATH);
    parserCall.setPersistedPostalAddresses(events);
    parserCall.setStartDate(getStartDate());
    parserCall.setStopDate(getStopDate());
    parserCall.setTest(isTest());
    parserCall.setTargetPath(LIST_POSTAL_ADDRESS);

    String fileName = parserCall.getFileName() + ".xlsx";

    XlsxReadCall readCall = new XlsxReadCall(INPUT_DIR, fileName);
    readCall.setTargetPath(getTargetPath());
    readCall.getListParams().setRowHead(0);
    readCall.execute(eo);
    LOGGER.info("Readed " + ((EoChild)eo.getEo(EVENTS)).size() + " from " + fileName);

    parserCall.execute(eo);
    if (persistDb) {
      EOInterfaceScalar eoDb =  eo.getEo(LIST_POSTAL_ADDRESS);
      int counter = 0;
      Call writeCall = new DbModelWriteCall(DB_MODELS_H2_FILE);
      //writeCall.setCondition("persist eq true");
      writeCall.execute(eoDb);
      LOGGER.info("Written " + counter + " entries from totally " + ((EoChild)eo.getEo(EVENTS)).size() + " entries to DB");
    }
    return "";
  }

  public PostalAddressCall getParserCall() {
    return parserCall;
  }

  public PostalAddressReadAndPersistCall setParserCall(ParserPostalAddressCall parserCall) {
    this.parserCall = parserCall;
    return this;
  }
  public static final void readXlsx(EOInterfaceScalar eo) {
    XlsxReadCall readCall = new XlsxReadCall(INPUT_DIR, POSTAL_ADDRESS_XLSX);
    readCall.setTargetPath("(List,PostalAddress)" + LOCATIONS);
    readCall.getListParams().setRowHead(0);
    readCall.execute(eo);
    LOGGER.info("Readed " + ((EoChild) eo.getEo(LOCATIONS)).size() + " from " + POSTAL_ADDRESS_XLSX + " mapped to " + LOCATIONS);
  }
  public Boolean getPersistXlsx() {
    return persistXlsx;
  }

  public PostalAddressReadAndPersistCall setPersistXlsx(Boolean persistXlsx) {
    this.persistXlsx = persistXlsx;
    return this;
  }

  public Boolean getPersistDb() {
    return persistDb;
  }

  public PostalAddressReadAndPersistCall setPersistDb(Boolean persistDb) {
    this.persistDb = persistDb;
    return this;
  }

  public boolean isReadXlsx() {
    return readXlsx;
  }

  public PostalAddressReadAndPersistCall setReadXlsx(boolean readXlsx) {
    this.readXlsx = readXlsx;
    return this;
  }

  @Override
  public void setByParameter(String values) {

  }

}

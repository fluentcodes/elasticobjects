package org.fluentcodes.projects.stadtbook.calls.postaladdress;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.stadtbook.domain.EventParsed;
import org.fluentcodes.projects.stadtbook.domain.PostalAddress;
import org.fluentcodes.projects.stadtbook.domain.Thing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserPostalAddressInMunchenCall extends ParserPostalAddressCall {
  private static final Logger LOGGER = LoggerFactory.getLogger(ParserPostalAddressInMunchenCall.class);
  private static Pattern PATTERN_DATE = Pattern.compile("(\\d\\d)\\. (.*) (\\d\\d\\d\\d)");
  private static Pattern PATTERN_PLZ = Pattern.compile("(.*) (\\d\\d\\d\\d\\d) (.*)");
  private List<LocalDate> localDates = new ArrayList<>();
  private int changeDatePosition = 0;
  private int startPosition = 0;
  static Pattern TITLE_PATTERN = Pattern.compile("^»(.*?)«(.*)");
  static List<String> permanent = Arrays.asList(
      "Digitales Kinderangebot"
  );


  public ParserPostalAddressInMunchenCall() {
    super();
  }

  List<String> parsedLocations = new ArrayList<>();
  public Object execute(EOInterfaceScalar eo) {
    check();
    initDriver();
    List<EventParsed> events = (List<EventParsed>)eo.get("events");
    int counter = 0;
    int allCounter = 0;
    for (EventParsed event:events) {
      allCounter++;
      if (!event.hasSourceEventUrl()) {
        continue;
      }
      String locationKey = Thing.upper(event.getLocation());
      if (getPersistedPostalAddresses().contains(locationKey)) {
        System.out.println("Skip " + event.getLocation());
        continue;
      }
      PostalAddress address = new PostalAddress();
      address.setName(event.getLocation());
      address.setNaturalId(locationKey);
      try {
        addDetails(address, event);
          address.setNaturalId();
          addPostalAddress(address);
          addPersistedPostalAddress(locationKey);
          counter++;
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ex) {
            ex.printStackTrace();
          }
        }

      catch (Exception e) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        System.out.println(e.getMessage());
        if (e.getMessage().contains("Sorry")) {
          continue;
        }
        address.setUrl(event.getSourceEventUrl());
        address.setDescription(e.getMessage());
        addPostalAddress(address);
        addPersistedPostalAddress(locationKey);
        counter++;

      }
      if (isTest() && counter > 50) {
        break;
      }
    }

    closeDriver();
    mapToResult(eo);
    LOGGER.info("Found " + getPostalAddressList().size() + " between " + getStartDate() + " and " + getStopDate());
    return "";
  }

  @Override
  public void setByParameter(String values) {

  }

  @Override
  public String fetchParseType() {
    return hasParseType()? getParseType():"inmuenchen";
  }


  public void addDetails(PostalAddress address, EventParsed event) {
    getDriver().get(event.getSourceEventUrl());
    String bodyText = getDriver()
        .findElement(new By.ByTagName("body"))
        .getAttribute("innerHTML")
        .replaceAll("<", "\n<");
    if (bodyText.contains("Sorry, dieser Event ist bereits abgelaufen")) {
      throw new EoException("Sorry, dieser Event ist bereits abgelaufen");
    }
    WebElement locationElement = getDriver().findElement(new By.ByClassName("location-box"));
    String streetPlz = locationElement.findElement(new By.ByClassName("street-box-inner")).getText();
    Matcher streetPlzMatcher = PATTERN_PLZ.matcher(streetPlz.replaceAll("\\n", " "));
    if (streetPlzMatcher.find()) {
      address.setStreetAddress(streetPlzMatcher.group(1));
      address.setPostalCode(streetPlzMatcher.group(2));
      address.setAddressRegion(streetPlzMatcher.group(3));
    } else {
      System.out.println("Could not match " + streetPlz);
    }

    List<WebElement> comElements = getDriver().findElements(new By.ByClassName("locinforow"));
    if (comElements.size() == 0) {
      throw new EoException("Could not find 'locinforow' ");
    }
    for (WebElement comElement : comElements) {
      String inner = comElement.getText();
      String innerType = comElement.findElement(new By.ByTagName("i")).getAttribute("class");
      if (inner == null) {
        continue;
      }
      if (innerType.contains("fa-fax")) {
        address.setFaxNumber(inner);
      } else if (innerType.contains("fa-phone")) {
        address.setTelephone(inner);

      } else if (innerType.contains("fa-tablet")) {
        try {

          String urlLocation = comElements.get(1).findElement(new By.ByTagName("a")).getAttribute("href");
          address.setUrl(urlLocation);
        } catch (Exception e) {
          System.out.println(e + inner);
        }
      }
    }
  }

}

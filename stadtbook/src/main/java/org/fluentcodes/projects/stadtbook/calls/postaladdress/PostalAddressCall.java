package org.fluentcodes.projects.stadtbook.calls.postaladdress;

import static org.fluentcodes.projects.stadtbook.domain.DateHelper.deriveDateEnglish;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.stadtbook.domain.PostalAddress;

public abstract class PostalAddressCall extends CallImpl {
  public static final String EVENTS_PATH = "events";
  private boolean test;
  private LocalDate currentDate;
  private LocalDate startDate;
  private LocalDate stopDate;
  private String parseType;
  private List<PostalAddress> postalAddressList;
  private Set<String> persistedPostalAddresses;
  public PostalAddressCall() {
    super();
  }

  void mapToResult(EOInterfaceScalar eo) {
    eo.set(postalAddressList, getTargetPath());
  }

  EOInterfaceScalar moveToResult(EOInterfaceScalar eo) {
    return eo.getEo( getTargetPath());
  }

  void check() {
    if (!hasStartDate()) {
      throw new EoException("Null startDate");
    }
    if (!hasStopDate()) {
      throw new EoException("Null stopDate");
    }
    if (!hasTargetPath()) {
      setTargetPath("/(List,EventParsed)" + EVENTS_PATH);
    }
    currentDate = startDate;
    postalAddressList = new ArrayList<>();
  }

  public Set<String> getPersistedPostalAddresses() {
    return persistedPostalAddresses;
  }

  public void setPersistedPostalAddresses(Set<String> persistedPostalAddresses) {
    this.persistedPostalAddresses = persistedPostalAddresses;
  }

  public void addPersistedPostalAddress(String naturalId) {
    this.persistedPostalAddresses.add(naturalId);
  }

  public void setPersistedPostalAddresses(List<PostalAddress> postalAddresses) {
    this.persistedPostalAddresses = new HashSet<>();
    for (PostalAddress postalAddress: postalAddresses) {
      persistedPostalAddresses.add(postalAddress.getNaturalId());
    }
  }

  String getFileName() {
    return fetchParseType() + ":" + deriveDateEnglish(startDate) + "-" + deriveDateEnglish(stopDate);
  }


  public String getParseType() {
    return parseType;
  }

  public String fetchParseType() {
    return parseType;
  }

  void setParseType(String parseType) {
    this.parseType = parseType;
  }

  public boolean hasParseType(){
    return parseType!=null && !parseType.isEmpty();
  }

  public boolean isTest() {
    return test;
  }

  public PostalAddressCall setTest(boolean test) {
    this.test = test;
    return this;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public PostalAddressCall setStartDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  public boolean hasStartDate() {
    return startDate!=null;
  }

  public LocalDate getStopDate() {
    return stopDate;
  }

  public PostalAddressCall setStopDate(LocalDate stopDate) {
    this.stopDate = stopDate;
    return this;
  }

  public boolean hasStopDate() {
    return stopDate!=null;
  }

  public LocalDate getCurrentDate() {
    return currentDate;
  }

  public void setCurrentDate(LocalDate currentDate) {
    this.currentDate = currentDate;
  }

  public List<PostalAddress> getPostalAddressList() {
    return postalAddressList;
  }

  public void setPostalAddressList(List<PostalAddress> postalAddressList) {
    this.postalAddressList = postalAddressList;
  }

  public void addPostalAddress(PostalAddress event) {
    postalAddressList.add(event);
  }
}

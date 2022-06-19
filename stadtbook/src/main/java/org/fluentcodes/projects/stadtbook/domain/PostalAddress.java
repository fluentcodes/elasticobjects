package org.fluentcodes.projects.stadtbook.domain;

import java.util.Locale;

/**
 * https://schema.org/PostalAddress
 */
public class PostalAddress extends ContactPoint{
  private String addressCountry;
  private String addressRegion;
  private String addressLocality;
  private String streetAddress;
  private String postalCode;
  private String naturalId;
  private String type;

  public PostalAddress() {

  }
  public String getNaturalId() {
    return naturalId;
  }
  public void setNaturalId(String naturalId) {
    this.naturalId = naturalId;
  }
  public void setNaturalId() {
    this.naturalId =
        getName().replaceAll("[^\\w]", "")
            .toUpperCase(Locale.ROOT);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean hasType() {
    return this.type !=null && !this.type.isEmpty();
  }

  public boolean filterType(String filter) {
    if (filter==null || filter.isEmpty()) {
      return false;
    }
    if (!hasType()) {
      return false;
    }
    if (this.type.matches(filter)) {
      return true;
    }
    return false;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getAddressCountry() {
    return addressCountry;
  }

  public void setAddressCountry(String addressCountry) {
    this.addressCountry = addressCountry;
  }

  public String getAddressRegion() {
    return addressRegion;
  }

  public void setAddressRegion(String addressRegion) {
    this.addressRegion = addressRegion;
  }

  public String getAddressLocality() {
    return addressLocality;
  }

  public void setAddressLocality(String addressLocality) {
    this.addressLocality = addressLocality;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String toString() {

    return
        super.toString() +
        postalCode + ";" +
        streetAddress + ";" +
        addressRegion + ";" +
        addressCountry;
  }
}

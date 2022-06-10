package org.fluentcodes.projects.stadtbook.domain;

public class ContactPoint extends Thing {
  private String email;
  private String faxNumber;
  private String telephone;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFaxNumber() {
    return faxNumber;
  }

  public void setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @Override
  public String toString() {
      return
          super.toString() +
          telephone + ";" +
          email + ";" +
          faxNumber + ";";
        }
}

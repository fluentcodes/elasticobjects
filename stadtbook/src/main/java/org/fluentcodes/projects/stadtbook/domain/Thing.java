package org.fluentcodes.projects.stadtbook.domain;

import java.util.Locale;

public class Thing {
  private String description;
  private String alternateName;
  private String name;
  private String url;
  private String identifier;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAlternateName() {
    return alternateName;
  }

  public void setAlternateName(String alternateName) {
    this.alternateName = alternateName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static String upper(String name) {
     return strip(name).
         replaceAll("[^\\w]", "")
         .toUpperCase(Locale.ROOT);
  }

  public static String strip(String entry) {
    if (entry == null) {
      return null;
    }
    entry = entry.replaceAll("^\\s+", "");
    entry = entry.replaceAll("\\s+$", "");
    return entry;
  }

  @Override
  public String toString() {
    return
        name + ";" +
            alternateName + ";" +
            identifier + ";" +
            url + ";" +
            description + ";";
  }
}

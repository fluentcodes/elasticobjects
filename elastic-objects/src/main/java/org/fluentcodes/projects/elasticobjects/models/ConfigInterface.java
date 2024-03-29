package org.fluentcodes.projects.elasticobjects.models;


import java.util.Date;
import java.util.List;
/*.{javaHeader}|*/

/**
 * Basic config interface as super interface for other cached items.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 17:46:47 CET 2020
 */
public interface ConfigInterface {

  default boolean hasScope(final Scope scope) {
    return scope == Scope.ALL ||
            getScope() == null ||
            getScope().isEmpty() ||
            getScope().contains(scope);
  }

  /**
   * expose
   */
  Expose getExpose();

  default boolean hasExpose() {
    return getExpose() != null;
  }

  /**
   * Defines a target module where generating occurs.
   */
  String getModule();

  default boolean hasModule() {
    return getModule() != null && !getModule().isEmpty();
  }

  /**
   * Defines scope of the configuration within module, eg 'test' or 'main' .
   */
  String getModuleScope();

  default boolean hasModuleScope() {
    return getModuleScope() != null && !getModuleScope().isEmpty();
  }

  /**
   * A scope for the cache value.
   */
  List<Scope> getScope();

  default boolean hasScope() {
    return getScope() != null && !getScope().isEmpty();
  }

  public String getAuthor();

  default boolean hasAuthor() {
    return getAuthor() != null && !getAuthor().isEmpty();
  }

  public Date getCreationDate();

  default boolean hasCreationDate() {
    return getCreationDate() != null;
  }

  public String getDescription();

  default boolean hasDescription() {
    return getDescription() != null && !getDescription().isEmpty();
  }

  public Long getId();

  default boolean hasId() {
    return getId() != null;
  }

  public String getNaturalId();

  default boolean hasNaturalId() {
    return getNaturalId() != null && !getNaturalId().isEmpty();
  }

  /*.{}.*/

  default Date getModificationDate() {
    return new Date();
  }

}

package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;

import java.util.List;
import java.util.Map;
/*.{javaHeader}|*/

/**
 * Basic config interface as super interface for other cached items.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 17:46:47 CET 2020
 */
public interface ConfigInterface extends BaseInterface {
  /*.{}.*/

  /*.{javaStaticNames}|*/
  String F_CONFIG_MODEL_KEY = "configModelKey";
  String F_EXPOSE = "expose";
  String F_MODULE = "module";
  String F_MODULE_SCOPE = "moduleScope";
  String F_SCOPE = "scope";
  /*.{}.*/

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
  /*.{}.*/
}

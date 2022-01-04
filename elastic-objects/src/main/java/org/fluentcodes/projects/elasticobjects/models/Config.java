package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Basic cache as super object for other cached items.
 *
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 10 11:32:02 CET 2021
 */
public class Config {
  /*.{}.*/
  private final ConfigMaps configMaps;
  /*.{javaInstanceVars}|*/
  /* expose */
  private final Expose expose;
  /* Defines a target module where generating occurs.  */
  private final String module;
  /* Defines scope of the configuration within module, eg 'test' or 'main' . */
  private final String moduleScope;
  /* A scope for the cache value. */
  private final List<Scope> scope;

  /* The author of the class. */
  private final String author;
  /* Used to define the creation of an item. */
  private final Date creationDate;
  /* A description of the model used by every model extending BaseClassImpl.  */
  private final String description;
  /* The numeric id of an instance of a class. */
  private final Long id;
  /* The natural key in @Base */
  private final String naturalId;

  /*.{}.*/

  public Config(ConfigBean bean, final ConfigMaps configMaps) {
    this.module = bean.getModule();
    this.moduleScope = bean.getModuleScope();
    this.scope = bean.getScope();
    this.expose = bean.getExpose();
    this.configMaps = configMaps;
    this.author = bean.getAuthor();
    this.creationDate = bean.getCreationDate();
    this.description = bean.getDescription();
    this.id = bean.getId();
    this.naturalId = bean.getNaturalId();
  }

  public ConfigMaps getConfigMaps() {
    return configMaps;
  }

  /*.{javaAccessors}|*/
  public Expose getExpose() {
    return this.expose;
  }

  public String getModule() {
    return this.module;
  }

  public String getModuleScope() {
    return this.moduleScope;
  }

  public List<Scope> getScope() {
    return this.scope;
  }

  public String getAuthor() {
    return this.author;
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public String getDescription() {
    return this.description;
  }

  public Long getId() {
    return this.id;
  }

  public String getNaturalId() {
    return this.naturalId;
  }


  /*.{}.*/

  @Override
  public String toString() {
    EoRoot cloneMap = EoRoot.ofClass(getConfigMaps(), Map.class);
    cloneMap.setSerializationType(JSONSerializationType.STANDARD);
    cloneMap.map(this);
    return new EOToJSON().toJson(cloneMap);
  }

}

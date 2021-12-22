package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.domain.BaseConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.List;
import java.util.Map;

/**
 * Basic cache as super object for other cached items.
 *
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 10 11:32:02 CET 2021
 */
public class ConfigConfig extends BaseConfig implements ConfigInterface {
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

  /*.{}.*/

  public ConfigConfig(ConfigBean configBean, final ConfigMaps configMaps) {
    super(configBean);
    this.module = configBean.getModule();
    this.moduleScope = configBean.getModuleScope();
    this.scope = configBean.getScope();
    this.expose = configBean.getExpose();
    this.configMaps = configMaps;
    if (configBean.getProperties() == null) {
      throw new EoInternalException("Null properties not allowed creating configs.");
    }
  }

  public ConfigMaps getConfigMaps() {
    return configMaps;
  }

  /*.{javaAccessors}|*/
  @Override
  public Expose getExpose() {
    return this.expose;
  }

  @Override
  public String getModule() {
    return this.module;
  }

  @Override
  public String getModuleScope() {
    return this.moduleScope;
  }

  @Override
  public List<Scope> getScope() {
    return this.scope;
  }

  /*.{}.*/

  /**
   * Set the values from config to {@link ConfigBean}
   *
   * @param bean basebean
   */
  public void populateBean(ConfigBean bean) {
    super.populateBean(bean);
    bean.setConfigModelKey(this.getClass().getSimpleName());
    bean.setExpose(getExpose());
    bean.setModule(getModule());
    bean.setModuleScope(getModuleScope());
    bean.setScope(getScope());
  }

  @Override
  public String toString() {
    EoRoot cloneMap = EoRoot.ofClass(getConfigMaps(), Map.class);
    cloneMap.setSerializationType(JSONSerializationType.STANDARD);
    cloneMap.map(this);
    return new EOToJSON().toJson(cloneMap);
  }
}

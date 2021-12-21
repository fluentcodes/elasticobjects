package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
/*.{javaHeader}|*/

/**
 * Basic host definition for file or db cache.
 *
 * @author null
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 12:17:41 CET 2021
 */
public class HostConfig extends PermissionConfig implements HostInterface {
  /*.{}.*/
  public static final String LOCALHOST = "localhost";


  /*.{javaAccessors}|*/
  /*.{}.*/
  private String urlCache;
  private final String hostName;
  private final String password;
  private final Integer port;
  private final String protocol;
  private final String url;
  private final String user;

  public HostConfig(ConfigBean bean, final ConfigMaps configMaps) {
    this((HostBean) bean, configMaps);
  }

  @Override
  public String getPassword() {
    return password;
  }

  public HostConfig(final HostBean hostBean, final ConfigMaps configMaps) {
    super(hostBean, configMaps);
    this.hostName = hostBean.getHostName();
    this.password = hostBean.getPassword();
    this.port = hostBean.getPort();
    this.protocol = hostBean.getProtocol();
    this.url = hostBean.getUrl();
    this.user = hostBean.getUser();
  }

  @Override
  public String getHostName() {
    return hostName;
  }

  @Override
  public Integer getPort() {
    return port;
  }

  @Override
  public String getProtocol() {
    return protocol;
  }

  @Override
  public String getUrl() {
    return url;
  }

  @Override
  public String getUser() {
    return user;
  }

  protected boolean hasUrlCache() {
    return urlCache != null && !urlCache.isEmpty();
  }

  public String getUrlCache() {
    return urlCache;
  }

  protected void setUrlCache(final String urlCache) {
    if (hasUrlCache()) {
      return;
    }
    this.urlCache = urlCache;
  }

  protected String createUrl() {
    if (!hasHostName()) {
      return getProtocol();
    } else if (LOCALHOST.equals(getHostName())) {
      urlCache = "file:";
    } else {
      if (hasPort()) {
        urlCache = getProtocol() + "://" + getHostName() + ":" + getPort();
      } else {
        urlCache = getProtocol() + "://" + getHostName();
      }
    }
    return urlCache;
  }

  public String getUrlPath() {
    if (hasUrlCache()) {
      return urlCache;
    }
    if (hasUrl()) {
      this.urlCache = getUrl();
      return urlCache;
    }
    if (getHostName() == null) {
      throw new EoException("Incomplete host exception: host name not add!");
    }
    return createUrl();
  }

  /**
   * Set the values from config to {@link HostBean}
   *
   * @param bean hostbean
   */
  public void populateBean(HostBean bean) {
    super.populateBean(bean);
    bean.setHostName(getHostName());
    bean.setPassword(getPassword());
    bean.setPort(getPort());
    bean.setUrl(getUser());
    bean.setUser(getUser());
    bean.setUrl(getUrl());
    bean.setProtocol(getProtocol());
  }

}

package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/**
 * Basic host definition for file or db cache.
 *
 * @author null
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 12:17:41 CET 2021
 */
public class HostConfig extends PermissionConfig implements HostInterface {
  public static final String LOCALHOST = "localhost";

  private String urlCache;
  private final String hostName;
  private final HostConfigProperties properties;

  public HostConfig(ConfigBean bean, final ConfigMaps configMaps) {
    this((HostBean) bean, configMaps);
  }
  public HostConfig(final HostBean bean, final ConfigMaps configMaps) {
    super(bean, configMaps);
    this.hostName = bean.getHostName();
    this.properties = new HostConfigProperties(bean.getProperties());
  }

  public HostConfigProperties getProperties() {
    return properties;
  }

  @Override
  public String getHostName() {
    return hostName;
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
      return properties.getProtocol();
    } else if (LOCALHOST.equals(getHostName())) {
      urlCache = "file:";
    } else {
      if (properties.hasPort()) {
        urlCache = properties.getProtocol() + "://" + getHostName() + ":" + properties.getPort();
      } else {
        urlCache = properties.getProtocol() + "://" + getHostName();
      }
    }
    return urlCache;
  }

  public String getUrlPath() {
    if (hasUrlCache()) {
      return urlCache;
    }
    if (properties.hasUrl()) {
      this.urlCache = properties.getUrl();
      return urlCache;
    }
    if (getHostName() == null) {
      throw new EoException("Incomplete host exception: host name not add!");
    }
    return createUrl();
  }

  @Override
  public String toString() {
    return getNaturalId() + " -> " + properties.getUrl();
  }
}

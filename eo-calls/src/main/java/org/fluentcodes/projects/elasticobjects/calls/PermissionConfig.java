package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.util.ArrayList;
import java.util.List;

/*.{javaHeader}|*/

/**
 * Abstract implementation for resources with permissions methods
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Dec 27 12:50:34 CET 2021
 */
public class PermissionConfig extends Config implements PermissionInterface {
  /*.{}.*/

  private final PermissionsForConfig permissions;

  protected PermissionConfig(PermissionBean bean, final ConfigMaps configMaps) {
    super(bean, configMaps);
    if (bean.getPermissions() == null) {
      bean.setPermissions(new PermissionsForBean());
    }
    this.permissions = new PermissionsForConfig(bean);
  }

  @Override
  public boolean hasPermissions(PermissionType callPermission, List<String> roleKeys) {
    return permissions.hasPermissions(callPermission, roleKeys);
  }

  public PermissionsForConfig getPermissions() {
    return permissions;
  }

  public List<String> getCreate() {
    return new ArrayList<>(permissions.getCreateRoles());
  }

  public List<String> getDelete() {
    return new ArrayList<>(permissions.getCreateRoles());
  }

  public List<String> getExecute() {
    return new ArrayList<>(permissions.getExecuteRoles());
  }

  public List<String> getNothing() {
    return new ArrayList<>(permissions.getNothingRoles());
  }

  public List<String> getRead() {
    return new ArrayList<>(permissions.getReadRoles());
  }

  public List<String> getWrite() {
    return new ArrayList<>(permissions.getWriteRoles());
  }

}

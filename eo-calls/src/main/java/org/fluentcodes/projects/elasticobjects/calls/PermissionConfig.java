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

  private final Permissions permissions;

  protected PermissionConfig(PermissionBean bean, final ConfigMaps configMaps) {
    super(bean, configMaps);
    this.permissions = new Permissions(bean);
  }

  /*.{javaAccessors}|*/
  /*.{}.*/

  @Override
  public boolean hasPermissions(PermissionType callPermission, List<String> roleKeys) {
    return permissions.hasPermissions(callPermission, roleKeys);
  }

  public Permissions getPermissions() {
    return permissions;
  }

  public List<String> getCreate() {
    return new ArrayList<>(permissions.getCreate());
  }

  public List<String> getDelete() {
    return new ArrayList<>(permissions.getCreate());
  }

  public List<String> getExecute() {
    return new ArrayList<>(permissions.getExecute());
  }

  public List<String> getNothing() {
    return new ArrayList<>(permissions.getNothing());
  }

  public List<String> getRead() {
    return new ArrayList<>(permissions.getRead());
  }

  public List<String> getWrite() {
    return new ArrayList<>(permissions.getWrite());
  }

}

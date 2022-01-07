package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

/**
 * A bean container class for {@link PermissionsForBean}.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 07 13:07:13 CET 2022
 */
public class PermissionBean extends ConfigBean {
    private PermissionsForBean permissions;

    public PermissionBean() {
        super();
        permissions = new PermissionsForBean();
    }

    public PermissionBean(PermissionConfig config) {
        super(config);
        this.permissions = new PermissionsForBean(config.getPermissions());
    }

    public PermissionsForBean getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionsForBean permissions) {
        this.permissions = permissions;
    }
}

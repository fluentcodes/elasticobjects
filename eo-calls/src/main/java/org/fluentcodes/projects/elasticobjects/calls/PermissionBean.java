package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

/**
 * A bean container class for {@link PermissionsForBean}.
 */
public class PermissionBean extends ConfigBean {
    static final String F_PERMISSIONS = "permissions";
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

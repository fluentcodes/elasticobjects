package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableList;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A config container for permissions.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Fri Jan 07 00:00:00 CET 2022
 */
public class PermissionsForConfig {
    public static final String SUPER_ADMIN = "superadmin";
    final boolean hasPermissions;
    private final String naturalId;
    private final List<String> createRoles;
    private final List<String> deleteRoles;
    private final List<String> executeRoles;
    private final List<String> nothingRoles;
    private final List<String> readRoles;
    private final List<String> writeRoles;
    private final Map<PermissionType, List<String>> permissionMap;

    public PermissionsForConfig(final PermissionBean bean) {
        this.naturalId = bean.getNaturalId();
        this.createRoles = new UnmodifiableList<>(bean.getPermissions().getCreateRoles());
        this.deleteRoles = new UnmodifiableList<>(bean.getPermissions().getDeleteRoles());
        this.executeRoles = new UnmodifiableList<>(bean.getPermissions().getExecuteRoles());
        this.nothingRoles = new UnmodifiableList<>(bean.getPermissions().getNothingRoles());
        this.readRoles = new UnmodifiableList<>(bean.getPermissions().getReadRoles());
        this.writeRoles = new UnmodifiableList<>(bean.getPermissions().getWriteRoles());

        this.permissionMap = new EnumMap<>(PermissionType.class);
        permissionMap.put(PermissionType.CREATE, this.createRoles);
        permissionMap.put(PermissionType.DELETE, this.deleteRoles);
        permissionMap.put(PermissionType.EXECUTE, this.executeRoles);
        permissionMap.put(PermissionType.NOTHING, this.nothingRoles);
        permissionMap.put(PermissionType.READ, this.readRoles);
        permissionMap.put(PermissionType.WRITE, this.writeRoles);
        hasPermissions = !createRoles.isEmpty() ||
                !deleteRoles.isEmpty() ||
                !executeRoles.isEmpty() ||
                !nothingRoles.isEmpty() ||
                !readRoles.isEmpty() ||
                !writeRoles.isEmpty();
    }

    public List<String> getCreateRoles() {
        return createRoles;
    }

    public List<String> getDeleteRoles() {
        return deleteRoles;
    }

    public List<String> getExecuteRoles() {
        return executeRoles;
    }

    public List<String> getNothingRoles() {
        return nothingRoles;
    }

    public List<String> getReadRoles() {
        return readRoles;
    }

    public List<String> getWriteRoles() {
        return writeRoles;
    }

    public boolean hasPermissions(PermissionType callPermission, List<String> roleKeys) {
        if (!hasPermissions) {
            return true;
        }
        if (roleKeys == null || roleKeys.isEmpty()) {
            return false;
        }
        if (roleKeys.contains(SUPER_ADMIN)) {
            return true;
        }
        for (final String roleKey : roleKeys) {
            int required = callPermission.ordinal();
            for (int i = 0; i <= required; i++) {
                PermissionType permissionType = PermissionType.values()[i];
                if (permissionMap.get(permissionType).contains(roleKey)) {
                    return true;
                }
            }
        }
        throw new EoException("No " + callPermission + " right for " + roleKeys + " and resource " + naturalId + ".");
    }

}

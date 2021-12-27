package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*.{javaHeader}|*/

/**
 * A bean container class for Field values
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 13:07:13 CET 2021
 */
public class Permissions {
    /*.{}.*/

    final boolean hasPermissions;
    private final List<String> createRoles;
    private final List<String> deleteRoles;
    private final List<String> executeRoles;
    private final List<String> nothingRoles;
    private final List<String> readRoles;
    private final List<String> writeRoles;
    private final Map<PermissionType, List<String>> permissionMap;

    public Permissions(final PermissionBean bean) {
        this.createRoles = new UnmodifiableList<>(bean.getCreate());
        this.deleteRoles = new UnmodifiableList<>(bean.getDelete());
        this.executeRoles = new UnmodifiableList<>(bean.getExecute());
        this.nothingRoles = new UnmodifiableList<>(bean.getNothing());
        this.readRoles = new UnmodifiableList<>(bean.getRead());
        this.writeRoles =  new UnmodifiableList<>(bean.getWrite());
        this.permissionMap = new HashMap<>();
        permissionMap.put(PermissionType.CREATE, this.createRoles);
        permissionMap.put(PermissionType.DELETE, this.deleteRoles);
        permissionMap.put(PermissionType.EXECUTE, this.executeRoles);
        permissionMap.put(PermissionType.NOTHING, this.nothingRoles);
        permissionMap.put(PermissionType.READ, this.readRoles);
        permissionMap.put(PermissionType.WRITE, this.writeRoles);
        hasPermissions = createRoles.isEmpty() &&
                deleteRoles.isEmpty() &&
                executeRoles.isEmpty() &&
                nothingRoles.isEmpty() &&
                readRoles.isEmpty() &&
                writeRoles.isEmpty();
    }
    /*.{javaAccessors}|*/

    /*.{}.*/


    public List<String> getCreate() {
        return createRoles;
    }

    public List<String> getDelete() {
        return deleteRoles;
    }

    public List<String> getExecute() {
        return executeRoles;
    }

    public List<String> getNothing() {
        return nothingRoles;
    }

    public List<String> getRead() {
        return readRoles;
    }

    public List<String> getWrite() {
        return writeRoles;
    }

    public boolean hasPermissions(PermissionType callPermission, List<String> roleKeys) {
        if (!hasPermissions) {
            return true;
        }
        if (roleKeys == null || roleKeys.isEmpty()) {
            return false;
        }
        for (final String roleKey: roleKeys) {
            int required = callPermission.ordinal();
            for (int i = 0; i < required ;i++) {
                PermissionType permissionType = PermissionType.values()[i];
                if (permissionMap.get(permissionType).contains(roleKey)) {
                    return true;
                }
            }
        }
        return false;
    }

}

package org.fluentcodes.projects.elasticobjects.calls;

/*.{javaHeader}|*/

import java.util.List;

/**
 * Interface with permissions.
 *
 * @author null
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Mon Jan 03 12:52:16 CET 2022
 */
public interface PermissionInterface {
    boolean hasPermissions(PermissionType callPermission, List<String> roleKeys);
}

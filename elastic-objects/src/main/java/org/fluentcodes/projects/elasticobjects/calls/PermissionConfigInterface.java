package org.fluentcodes.projects.elasticobjects.calls;

/*=>{javaHeader}|*/
/**
 * 
 * Interface with permissions methods  
 * @author null
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:52:16 CET 2021
 */
public interface PermissionConfigInterface  {
/*=>{}.*/

/*=>{javaStaticNames}|*/
   String ROLE_PERMISSIONS = "rolePermissions";
/*=>{}.*/

/*=>{javaAccessors}|*/
   PermissionRole getRolePermissions();
   default boolean hasRolePermissions() {
      return getRolePermissions() != null;
   }
/*=>{}.*/
}

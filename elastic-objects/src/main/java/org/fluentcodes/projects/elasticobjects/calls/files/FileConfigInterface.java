package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
/*=>{javaAccessors}|*/
/**
 * 
 * Access methods for field properties map.  
 * @author Werner Diwischek
 * @creationDate Wed Sep 09 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 14:24:01 CET 2021
 */
public interface FileConfigInterface extends PermissionConfigInterface,ConfigConfigInterface  {
/*=>{}.*/

/*=>{javaStaticNames}|*/
    String FILE_NAME = "fileName";
    String FILE_PATH = "filePath";
    String CACHED = "cached";
    String TEMPLATE = "template";
/*=>{}.*/

/*=>{javaAccessors}|*/
   Boolean getCached();
   default boolean hasCached() {
      return getCached() != null;
   }
   default boolean isCached() {
      return hasCached() && getCached();
   }

   String getFileName();
   default boolean hasFileName() {
      return getFileName() != null && !getFileName().isEmpty();
   }
   String getFilePath();
   default boolean hasFilePath() {
      return getFilePath() != null && !getFilePath().isEmpty();
   }
   String getHostConfigKey();
   default boolean hasHostConfigKey() {
      return getHostConfigKey() != null && !getHostConfigKey().isEmpty();
   }
/*=>{}.*/
    default String getTemplate(){
    return (String) getProperties().get(TEMPLATE);
}
    default boolean hasTemplate() {
        return getProperties().containsKey(TEMPLATE) &&
                getProperties().get(TEMPLATE) != null &&
                !((String)getProperties().get(TEMPLATE)).isEmpty();
    }
    default boolean isTargetTemplate() {
        return true;
    }
}

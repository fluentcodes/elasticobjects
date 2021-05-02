package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBeanInterface;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;
/*=>{javaHeader}|*/
/**
 * 
 * Access methods for field properties map.  
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Fri Jan 29 12:22:35 CET 2021
 */
public interface FileBeanInterface extends FileConfigInterface,PermissionBeanInterface  {
/*=>{}.*/
/*=>{javaAccessors}|*/
   FileBeanInterface setCached(final Boolean cached);
   default void mergeCached(final Object value) {
      if (value == null) return;
      if (hasCached()) return;
      setCached(ScalarConverter.toBoolean(value));
   }

   FileBeanInterface setFileName(final String fileName);
   default void mergeFileName(final Object value) {
      if (value == null) return;
      if (hasFileName()) return;
      setFileName(ScalarConverter.toString(value));
   }

   FileBeanInterface setFilePath(final String filePath);
   default void mergeFilePath(final Object value) {
      if (value == null) return;
      if (hasFilePath()) return;
      setFilePath(ScalarConverter.toString(value));
   }

   FileBeanInterface setHostConfigKey(final String hostConfigKey);
   default void mergeHostConfigKey(final Object value) {
      if (value == null) return;
      if (hasHostConfigKey()) return;
      setHostConfigKey(ScalarConverter.toString(value));
   }

/*=>{}.*/
}

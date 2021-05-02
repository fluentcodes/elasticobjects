package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;

import java.util.Map;

/*=>{javaHeader}|*/
import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;

import static org.fluentcodes.projects.elasticobjects.calls.HostCall.HOST_CONFIG_KEY;

/**
 * 
 * The bean counterpart for {@link FileConfig}. 
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 14:48:43 CET 2021
 */
public class FileBean extends PermissionBean implements FileBeanInterface  {
/*=>{}.*/

/*=>{javaInstanceVars}|*/
   /* If true will cache the readed file within the cache object.  */
   private Boolean cached;
   /* A fileName used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
   private String fileName;
   /* A filePath used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
   private String filePath;
   /* A key for host objects. */
   private String hostConfigKey;
/*=>{}.*/


    public FileBean() {
        super();
        defaultConfigModelKey();
    }

    public FileBean(final String naturalId, final Map map) {
        super(naturalId, map);
    }

    public FileBean(final Map map) {
        super();
        merge(map);
    }

    public void merge (final Map configMap) {
        super.merge(configMap);
        mergeFileName(configMap.get(FILE_NAME));
        mergeFilePath(configMap.get(FILE_PATH));
        mergeCached(configMap.get(CACHED));
        mergeHostConfigKey(configMap.get(HOST_CONFIG_KEY));
        defaultConfigModelKey();
    }

    @Override
    public void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(FileConfig.class.getSimpleName());
    }

/*=>{javaAccessors}|*/
   @Override
   public Boolean getCached() {
      return this.cached;
   }
   @Override
   public FileBean setCached(final Boolean cached) {
      this.cached = cached;
      return this;
    }

   @Override
   public String getFileName() {
      return this.fileName;
   }
   @Override
   public FileBean setFileName(final String fileName) {
      this.fileName = fileName;
      return this;
    }

   @Override
   public String getFilePath() {
      return this.filePath;
   }
   @Override
   public FileBean setFilePath(final String filePath) {
      this.filePath = filePath;
      return this;
    }

   @Override
   public String getHostConfigKey() {
      return this.hostConfigKey;
   }
   @Override
   public FileBean setHostConfigKey(final String hostConfigKey) {
      this.hostConfigKey = hostConfigKey;
      return this;
    }

/*=>{}.*/
}

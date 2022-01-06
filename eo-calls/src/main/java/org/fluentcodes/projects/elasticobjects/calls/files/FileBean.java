package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerBoolean;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.Map;
/**
 * The bean counterpart for {@link FileConfig}.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 14:48:43 CET 2021
 */
public class FileBean extends PermissionBean implements FileInterface {
    private FileBeanProperties properties;
    /* If true will cache the readed file within the cache object.  */
    private Boolean cached;
    /* A fileName used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
    private String fileName;
    /* A filePath used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
    private String filePath;
    /* A key for host objects. */
    private String hostConfigKey;

    public FileBean() {
        super();
        this.properties = new FileBeanProperties();
        defaultConfigModelKey();
    }

    public FileBeanProperties getProperties() {
        return properties;
    }

    public void setProperties(FileBeanProperties properties) {
        this.properties = properties;
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(FileConfig.class.getSimpleName());
    }

    /*.{javaAccessors}|*/
    @Override
    public Boolean getCached() {
        return this.cached;
    }

    public FileBean setCached(final Boolean cached) {
        this.cached = cached;
        return this;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    public FileBean setFileName(final String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    public FileBean setFilePath(final String filePath) {
        this.filePath = filePath;
        return this;
    }

    @Override
    public String getHostConfigKey() {
        return this.hostConfigKey;
    }

    public FileBean setHostConfigKey(final String hostConfigKey) {
        this.hostConfigKey = hostConfigKey;
        return this;
    }
}

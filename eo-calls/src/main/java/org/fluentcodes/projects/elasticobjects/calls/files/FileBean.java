package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerBoolean;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.HostCall.HOST_CONFIG_KEY;

/*.{javaHeader}|*/

/**
 * The bean counterpart for {@link FileConfig}.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 14:48:43 CET 2021
 */
public class FileBean extends PermissionBean implements FileInterface {
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /* If true will cache the readed file within the cache object.  */
    private Boolean cached;
    /* A fileName used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
    private String fileName;
    /* A filePath used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
    private String filePath;
    /* A key for host objects. */
    private String hostConfigKey;
    /*.{}.*/


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

    public void merge(final Map configMap) {
        super.merge(configMap);
        mergeFileName(configMap.get(F_FILE_NAME));
        mergeFilePath(configMap.get(F_FILE_PATH));
        mergeCached(configMap.get(F_CACHED));
        mergeHostConfigKey(configMap.get(HOST_CONFIG_KEY));
        defaultConfigModelKey();
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

    /*.{}.*/

    private void mergeCached(final Object value) {
        if (value == null) return;
        if (hasCached()) return;
        setCached(new ShapeTypeSerializerBoolean().asObject(value));
    }

    private void mergeFileName(final Object value) {
        if (value == null) return;
        if (hasFileName()) return;
        setFileName(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeFilePath(final Object value) {
        if (value == null) return;
        if (hasFilePath()) return;
        setFilePath(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeHostConfigKey(final Object value) {
        if (value == null) return;
        if (hasHostConfigKey()) return;
        setHostConfigKey(new ShapeTypeSerializerString().asObject(value));
    }

    @Override
    public String getTemplate() {
        return (String) getProperties().get(F_TEMPLATE);
    }

    @Override
    public boolean hasTemplate() {
        return getProperties().containsKey(F_TEMPLATE) &&
                getProperties().get(F_TEMPLATE) != null &&
                !((String) getProperties().get(F_TEMPLATE)).isEmpty();
    }
}

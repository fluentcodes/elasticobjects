package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import static org.fluentcodes.projects.elasticobjects.calls.HostConfig.LOCALHOST;
/**
 * Immutable EO file configuration allow read or write access to a specific file.
 *
 * @author Werner Diwischek
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 14:42:46 CET 2021
 */
public class FileConfig extends PermissionConfig implements FileInterface {
    private FileConfigProperties properties;
    /* If true will cache the readed file within the cache object.  */
    private final Boolean cached;
    /* A fileName used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
    private final String fileName;
    /* A filePath used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
    private final String filePath;
    /* A key for host objects. */
    private final String hostConfigKey;


    private String cachedContent;

    public FileConfig(ConfigBean bean, final ConfigMaps configMaps) {
        this((FileBean) bean, configMaps);
    }

    public FileConfig(FileBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        this.fileName = bean.getFileName();
        this.filePath = bean.getFilePath();
        this.cached = bean.getCached();
        this.hostConfigKey = bean.hasHostConfigKey() ? bean.getHostConfigKey() : LOCALHOST;
        this.properties = new FileConfigProperties(bean.getProperties());
    }

    public FileConfigProperties getProperties() {
        return properties;
    }

    @Override
    public Boolean getCached() {
        return this.cached;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public String getHostConfigKey() {
        return this.hostConfigKey;
    }
    /*.{}.*/

    protected boolean hasCachedContent() {
        return cachedContent != null && !cachedContent.isEmpty();
    }

    public String getCachedContent() {
        return cachedContent;
    }

    public void setCachedContent(String cachedContent) {
        this.cachedContent = cachedContent;
    }

    public String getUrlPath(final HostConfig hostConfig) {
        String hostPath = hostConfig.getUrlPath();
        if (hostPath == null || hostPath.isEmpty()) {
            return filePath + "/" + fileName;
        }
        return new Parser(hostPath + "" + filePath + "/" + fileName).parse();
    }

    protected HostConfig resolveHostConfig(final EOInterfaceScalar eo, final String hostConfigKey) {
        if (hostConfigKey != null && !hostConfigKey.isEmpty())
            return (HostConfig) eo.getConfigMaps().find(HostConfig.class, hostConfigKey);
        return (HostConfig) eo.getConfigMaps().find(HostConfig.class, this.getHostConfigKey());
    }

    public URL findUrl(final EOInterfaceScalar eo, final String hostConfigKey) {
        return findUrl(resolveHostConfig(eo, hostConfigKey));
    }

    public URL findUrl(HostConfig hostConfig) {
        URL url = createUrl(hostConfig);
        final String localFileName = url.toString().replaceAll("^file:", "");
        if (new File(localFileName).exists()) {
            return url;
        }
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(localFileName);
            while (urls.hasMoreElements()) {
                return urls.nextElement();
            }
            throw new EoException("Could not find " + fileName.replaceAll("^/+", ""));
        } catch (Exception e) {
            throw new EoException("Could not find " + fileName.replaceAll("^/+", ""), e);
        }
    }

    public URL getUrl(HostConfig hostConfig) {
        if (!hasFileName()) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        URL url = createUrl(hostConfig);
        String urlString = getUrlPath(hostConfig);
        try {
            String replaceUrl = new Parser(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
    }

    public URL createUrl(HostConfig hostConfig) {
        if (!hasFileName()) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        if (!hasFilePath()) {
            throw new EoException("No path in file provided '" + getNaturalId() + "'!");
        }
        String urlString = getUrlPath(hostConfig);
        try {
            String replaceUrl = new Parser(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
    }
}

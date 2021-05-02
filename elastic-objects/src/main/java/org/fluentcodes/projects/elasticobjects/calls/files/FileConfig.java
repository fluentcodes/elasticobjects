package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserCurlyBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.HostConfig.LOCALHOST;

/*=>{javaHeader}|*/
/**
 * 
 * Immutable EO file configuration allow read or write access to a specific file.  
 * @author Werner Diwischek
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 14:42:46 CET 2021
 */
public class FileConfig extends PermissionConfig implements FileConfigInterfaceMethods  {
/*=>{}.*/

/*=>{javaInstanceVars}|*/
   /* If true will cache the readed file within the cache object.  */
   private final Boolean cached;
   /* A fileName used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
   private final String fileName;
   /* A filePath used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.  */
   private final String filePath;
   /* A key for host objects. */
   private final String hostConfigKey;
/*=>{}.*/
    private String cachedContent;

    public FileConfig(Map map) {
        this(new FileBean(map));
    }

    public FileConfig(ConfigBean bean) {
        this((FileBean) bean);
    }

    public FileConfig(FileBean bean) {
        super(bean);
        this.fileName = bean.getFileName();
        this.filePath = bean.getFilePath();
        this.cached = bean.getCached();
        this.hostConfigKey = bean.hasHostConfigKey() ? bean.getHostConfigKey(): LOCALHOST;
    }



/*=>{javaAccessors}|*/
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
/*=>{}.*/

    protected boolean hasCachedContent() {
        return cachedContent != null && !cachedContent.isEmpty();
    }
    @Override
    public String getCachedContent() {
        return cachedContent;
    }

    @Override
    public void setCachedContent(String cachedContent) {
        this.cachedContent = cachedContent;
    }

    @Override
    public String getUrlPath(final HostConfig hostConfig )  {
        String hostPath = hostConfig.getUrlPath();
        if (hostPath == null || hostPath.isEmpty()) {
            return filePath + "/" + fileName;
        }
        return new ParserCurlyBracket(hostPath + "" + filePath + "/" + fileName).parse();
    }

    protected HostConfig resolveHostConfig(final EO eo, final String hostConfigKey) {
        if (hostConfigKey != null && !hostConfigKey.isEmpty()) return eo.getConfigsCache().findHost(hostConfigKey);
        return eo.getConfigsCache().findHost(this.getHostConfigKey());
    }

    public URL findUrl(final EO eo, final String hostConfigKey)  {
        return findUrl(resolveHostConfig(eo, hostConfigKey));
    }

    @Override
    public URL findUrl(HostConfig hostConfig)  {
        URL url = createUrl(hostConfig);
        final String localFileName = url.toString().replaceAll("^file:","");
        if (new File(localFileName).exists()) {
            return url;
        }
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(localFileName);
            while (urls.hasMoreElements()) {
                return urls.nextElement();
            }
            throw new EoException("Could not find " + fileName.replaceAll("^/+", ""));
        }
        catch (Exception e) {
            throw new EoException("Could not find " + fileName.replaceAll("^/+", ""), e);
        }
    }
    @Override
    public URL getUrl(HostConfig hostConfig)  {
        if (!hasFileName()) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        URL url = createUrl(hostConfig);
        String urlString = getUrlPath(hostConfig);
        try {
            String replaceUrl = new ParserCurlyBracket(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
    }
    @Override
    public URL createUrl(HostConfig hostConfig)  {
        if (!hasFileName()) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        if (!hasFilePath()) {
            throw new EoException("No path in file provided '" + getNaturalId() + "'!");
        }
        String urlString = getUrlPath(hostConfig);
        try {
            String replaceUrl = new ParserCurlyBracket(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
    }
}

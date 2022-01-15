package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/*.{javaHeader}|*/

/**
 * Read a list from a directory. If it's called from an EO context the fileName of the associated FileConfig will  be used to filter the result, e.g. *.html. 
 * Offers also general access methods like read(String filePath, String fileName) for java access. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 09:39:58 CET 2020
 */
public class DirectoryListReadCall extends FileReadCall  {
/*.{}.*/

    /*.{javaStaticNames}|*/
   public static final String ABSOLUTE = "absolute";
/*.{}.*/

    /*.{javaInstanceVars}|*/
   private  Boolean absolute;
/*.{}.*/
    public DirectoryListReadCall() {
        super();
        absolute = false;
    }
    public DirectoryListReadCall(final String configKey) {
        super(configKey);
        absolute = false;
    }

    @Override
    public Object execute(final EOInterfaceScalar eo)  {
        List<String> result = listFiles(eo);
        return createReturnType(eo, result);
    }

    public List<String> listFiles(EOInterfaceScalar eo)  {
        DirectoryConfig config = (DirectoryConfig)init(PermissionType.READ, eo);
        return listFiles(config.getFilePath(), config.getFileName(), absolute);
    }

    // https://stackabuse.com/java-list-files-in-a-directory/
    public List<String> listFiles(final String filePath, final String filter, final boolean isAbsolute)  {
        if (filePath==null || filePath.isEmpty()) {
            throw new EoException("No filePath defined");
        }
        File directory = new File(filePath);
        if (!directory.exists()) {
            List<URL> urlList = getUrlListFromClassPath(filePath);
            if (urlList.isEmpty()) {
                throw new EoException("Directory '" + filePath + "' does not exist also in class path.");
            }
            if (urlList.size()>1) {
                throw new EoException("Several directories for '" + filePath + "' found in class path: " + urlList.toString());
            }
            directory = new File(urlList.get(0).getFile());
        }
        String directoryName = isAbsolute ? directory.getAbsolutePath() + Path.DELIMITER: "";
        final String[] fileNames = directory.list();
        List<String> fileNameList = new ArrayList<>();
        for(final String fileName:fileNames) {
            if (fileName.matches(filter)) {
                fileNameList.add(directoryName + fileName);
            }
        }
        if (fileNames.length>0 && fileNameList.isEmpty()) {
            throw new EoException("Empty result for filter '" + filter + "' and filePath '" + filePath + "' but size of files in directory is " + fileNames.length);
        }
        return fileNameList;
    }

    public static final List<URL> getUrlListFromClassPath(final String fileName) {
        List<URL> urlList = new ArrayList<>();
        try {
            Enumeration<URL> urlsFromClasspath =  Thread.currentThread().getContextClassLoader().getResources(fileName);
            while (urlsFromClasspath.hasMoreElements()) {
                urlList.add(urlsFromClasspath.nextElement());
            }
        }
        catch (Exception e) {
            throw new EoException("Null matching file in classpath for '" + fileName + "'.");
        }
        return urlList;
    }

    public Boolean isAbsolute() {
        return absolute;
    }

    /*.{javaAccessors}|*/
    /**
    If true will cache the readed file within the cache object. 
    */

    public DirectoryListReadCall setAbsolute(Boolean absolute) {
        this.absolute = absolute;
        return this;
    }
    
    public Boolean getAbsolute () {
       return this.absolute;
    }
    
    public boolean hasAbsolute () {
        return absolute!= null;
    }
/*.{}.*/
}

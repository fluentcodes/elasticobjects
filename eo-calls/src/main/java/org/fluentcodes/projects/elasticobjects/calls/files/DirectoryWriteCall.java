package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.io.IOString;
/*.{javaHeader}|*/

/**
 * Defines a file write operation for a directory configuration {@link DirectoryConfig} specified by fileConfigKey.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 09:43:38 CET 2020
 */
public class DirectoryWriteCall extends FileWriteCall {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String FILE_NAME = "fileName";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private String fileName;

    /*.{}.*/
    public DirectoryWriteCall() {
        super();
    }

    public DirectoryWriteCall(final String configKey) {
        super(configKey);
    }

    @Override
    public String execute(final EOInterfaceScalar eo) {
        if (!init(eo)) {
            return "";
        }
        return write(eo);
    }

    public String write(final EOInterfaceScalar eo) {
        if (!hasFileName()) {
            throw new EoException("No fileName is set for " + this.getClass().getSimpleName() + " with config '" + getFileConfigKey() + "'.");
        }
        if (fileName.contains("..")) {
            throw new EoException("FileName in call for write '" + fileName + "' has some backPropagation!");
        }
        DirectoryConfig directoryConfig = (DirectoryConfig) init(PermissionType.READ, eo);
        if (!fileName.matches(directoryConfig.getFileName())) {
            throw new EoException("FileName in call for read '" + fileName + "' does not match fileName in  DirectoryConfig '" + getFileName() + "'.");
        }

        String url = directoryConfig.getFilePath() + "/" + fileName;
        if (TemplateMarker.SQUARE.hasStartSequence(url)) {
            url = new Parser(TemplateMarker.SQUARE, url).parse(eo);
        }
        new IOString(url).write(getContent());
        return "Written content with  length " + getContent().length() + " to file '" + url + "'";
    }

    /*.{javaAccessors}|*/

    /**
     * A fileName used in different calls and configs like {@link FileConfig} or {@link DirectoryConfig}.
     */

    public DirectoryWriteCall setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileName() {
        return this.fileName;
    }

    public boolean hasFileName() {
        return fileName != null && !fileName.isEmpty();
    }
    /*.{}.*/
}

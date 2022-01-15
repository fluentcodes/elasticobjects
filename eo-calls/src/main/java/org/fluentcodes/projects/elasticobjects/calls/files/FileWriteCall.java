package org.fluentcodes.projects.elasticobjects.calls.files;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.tools.io.IOString;

/*.{javaHeader}|*/

/**
 * Write content or serialized eo to a file.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 09:48:45 CET 2020
 */
public class FileWriteCall extends FileCall implements ConfigWriteCommand, CallContent {
    /*.{}.*/
    private static final Logger LOG = LogManager.getLogger(FileWriteCall.class);

    /*.{javaStaticNames}|*/
    public static final String COMPARE = "compare";
    public static final String CONTENT = "content";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private Boolean compare;
    private String content;
    /*.{}.*/

    public FileWriteCall() {
        super();
        compare = true;
    }

    public FileWriteCall(final String configKey) {
        super(configKey);
        compare = true;
    }

    public FileWriteCall(final String configKey, final String content) {
        super(configKey);
        setContent(content);
        compare = true;
    }

    @Override
    public String execute(final EOInterfaceScalar eo) {
        return this.write(eo);
    }

    public String write(EOInterfaceScalar eo) {
        FileConfig fileConfig = super.init(PermissionType.READ, eo);
        if (!hasContent()) {
            if (eo.isScalar()) {
                content = eo.get().toString();
            } else {
                content = eo.toString();
            }
        }
        String targetFile = fileConfig.createUrl(getHostConfig()).getFile();
        if (TemplateMarker.SQUARE.hasStartSequence(targetFile)) {
            targetFile = new Parser(TemplateMarker.SQUARE, targetFile).parse(eo);
        }
        // compare with existing file to be overwritten.
        if (compare) {
            try {
                String existing = ((String) new FileReadCall(getFileConfigKey()).execute(eo))
                        .replaceAll("@.*Date.*", "");

                String compare = getContent().replaceAll("@.*Date.*", "");
                if (existing.equals(compare)) {
                    return "Skip writing file '" + targetFile + "' with same content and length " + getContent().length() + ".";
                }
            } catch (Exception e) {
                LOG.debug(e);
                eo.debug(e.getMessage());
            }
        }
        write(targetFile, content);
        return "Written content with length " + getContent().length() + " to file '" + targetFile + "'";
    }

    public static void write(String targetFile, Object content) {
        new IOString(targetFile).write((String) content);
    }

    public boolean isCompare() {
        return compare != null && compare;
    }

    public FileWriteCall setCompare(boolean compare) {
        this.compare = compare;
        return this;
    }

    /*.{javaAccessors}|*/

    /**
     * Trigger a compare before writing in @FileWriteCall
     */

    public FileWriteCall setCompare(Boolean compare) {
        this.compare = compare;
        return this;
    }

    public Boolean getCompare() {
        return this.compare;
    }

    public boolean hasCompare() {
        return compare != null;
    }

    /**
     * A content for different calls. In a template context the content of the markup.
     */

    public FileWriteCall setContent(String content) {
        this.content = content;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }
    /*.{}.*/
}

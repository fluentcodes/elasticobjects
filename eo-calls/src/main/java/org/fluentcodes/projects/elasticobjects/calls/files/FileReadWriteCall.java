package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadWriteCommand;

/*.{javaHeader}|*/

/**
 * Read content of a file specified by sourceFileConfigKey referencing a FileConfig with this key. Afterwards it will store it to the file configuration with key targetFileConfigKey.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 09:47:43 CET 2020
 */
public class FileReadWriteCall extends CallImpl implements ConfigReadWriteCommand {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String COMPARE = "compare";
    public static final String SOURCE_FILE_CONFIG_KEY = "sourceFileConfigKey";
    public static final String TARGET_FILE_CONFIG_KEY = "targetFileConfigKey";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private Boolean compare;
    private String sourceFileConfigKey;
    private String targetFileConfigKey;
    /*.{}.*/

    public FileReadWriteCall() {
        super();
        compare = true;
    }

    public FileReadWriteCall(final String sourceFileConfigKey, final String targetFileConfigKey) {
        super();
        this.sourceFileConfigKey = sourceFileConfigKey;
        this.targetFileConfigKey = targetFileConfigKey;
        compare = true;
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        return writeTarget(eo, readSource(eo));
    }

    protected String readSource(final EOInterfaceScalar eo) {
        return (String) new FileReadCall(sourceFileConfigKey).execute(eo);
    }

    protected String readTarget(final EOInterfaceScalar eo) {
        return (String) new FileReadCall(targetFileConfigKey).execute(eo);
    }

    protected String writeTarget(final EOInterfaceScalar eo, final String content) {
        return new FileWriteCall(targetFileConfigKey)
                .setContent(content)
                .setCompare(compare)
                .execute(eo);
    }

    public boolean isCompare() {
        return compare;
    }

    /*.{javaAccessors}|*/

    /**
     * Trigger a compare before writing in @FileWriteCall
     */

    public FileReadWriteCall setCompare(Boolean compare) {
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
     * Defines the key for a file configuration {@link FileConfig} where to read a file.
     */

    public FileReadWriteCall setSourceFileConfigKey(String sourceFileConfigKey) {
        this.sourceFileConfigKey = sourceFileConfigKey;
        return this;
    }

    public String getSourceFileConfigKey() {
        return this.sourceFileConfigKey;
    }

    public boolean hasSourceFileConfigKey() {
        return sourceFileConfigKey != null && !sourceFileConfigKey.isEmpty();
    }

    /**
     * A target for persisting template results in TemplateResourceStoreCall.
     */

    public FileReadWriteCall setTargetFileConfigKey(String targetFileConfigKey) {
        this.targetFileConfigKey = targetFileConfigKey;
        return this;
    }

    public String getTargetFileConfigKey() {
        return this.targetFileConfigKey;
    }

    public boolean hasTargetFileConfigKey() {
        return targetFileConfigKey != null && !targetFileConfigKey.isEmpty();
    }
    /*.{}.*/
}

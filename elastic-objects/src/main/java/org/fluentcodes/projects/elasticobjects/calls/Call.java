package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;

/**
 * Basic interface for calls
 */
public interface Call {

    static final String TARGET_AS_STRING = "_asString";
    static final String F_TARGET_PATH = "targetPath";

    static final String F_DURATION = "duration";
    static final String F_KEEP_CALL = "keepCall";


    default void initTargetPath(final Path targetPathFromCallPath) {
        if (!hasTargetPath()) {
            setTargetPath(targetPathFromCallPath.directory());
        }
    }

    Object execute(final EOInterfaceScalar eo);

    void setByParameter(final String values);

    default boolean isTargetAsString() {
        return TARGET_AS_STRING.equals(getTargetPath());
    }

    default String getKeepEndSequence() {
        if (!hasKeepCall()) {
            return "";
        }
        return getKeepCall().getEndComment();
    }

    default String getKeepStartSequence() {
        if (!hasKeepCall()) {
            return "";
        }
        return getKeepCall().getStartComment();
    }

    /**
     * A condition for calls.
     */
    String getCondition();

    Call setCondition(String condition);

    default boolean hasCondition() {
        return getCondition() != null && !getCondition().isEmpty();
    }

    /**
     * The duration of a call.
     */
    Long getDuration();

    Call setDuration(Long duration);

    default boolean hasDuration() {
        return getDuration() != null;
    }

    /**
     * Will use an existing  result file beforehand as template.
     */
    KeepCalls getKeepCall();

    Call setKeepCall(KeepCalls keepCall);

    default boolean hasKeepCall() {
        return getKeepCall() != null;
    }

    /**
     * logLevel
     */
    LogLevel getLogLevel();

    Call setLogLevel(LogLevel logLevel);

    default boolean hasLogLevel() {
        return getLogLevel() != null;
    }

    /**
     * A string representation for a list of model keys.
     */
    String getModels();

    Call setModels(String models);

    default boolean hasModels() {
        return getModels() != null && !getModels().isEmpty();
    }

    /**
     * A overwrite field flag for @Call.
     */
    Boolean getOverwrite();

    Call setOverwrite(Boolean overwrite);

    default boolean hasOverwrite() {
        return getOverwrite() != null;
    }

    /**
     * postpend String when executed by ExecutorCallImpl
     */
    String getPostpend();

    Call setPostpend(String postpend);

    default boolean hasPostpend() {
        return getPostpend() != null && !getPostpend().isEmpty();
    }

    /**
     * prepend String when executed by ExecutorCallImpl
     */
    String getPrepend();

    Call setPrepend(String prepend);

    default boolean hasPrepend() {
        return getPrepend() != null && !getPrepend().isEmpty();
    }

    /**
     * A sourcePath where EO offers it's input value when the execution starts.
     */
    String getSourcePath();

    Call setSourcePath(String sourcePath);

    default boolean hasSourcePath() {
        return getSourcePath() != null && !getSourcePath().isEmpty();
    }

    /**
     * A condition for calls.
     */
    String getStartCondition();

    Call setStartCondition(String startCondition);

    boolean evalStartCondition(EOInterfaceScalar eo);

    default boolean hasStartCondition() {
        return getStartCondition() != null && !getStartCondition().isEmpty();
    }

    /**
     * A targetPath where the result of the execution will be mapped. If value is "_asString" no mapping occured but a seralized version is returned as value to embed it in the resulting file. Path parameters could be set dynamically with =&gt;[path]. in any combination.
     */
    String getTargetPath();

    Call setTargetPath(String targetPath);

    default boolean hasTargetPath() {
        return getTargetPath() != null && !getTargetPath().isEmpty();
    }
}

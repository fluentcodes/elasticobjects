package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

/*.{javaHeader}|*/

/**
 * Basic bean implementation for calls.
 *
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Thu Jan 07 11:38:14 CET 2021
 */
public abstract class CallImpl implements Call {/*.{}.*/
    /* A condition for calls.  */
    private String condition;
    /* The duration of a call. */
    private Long duration;
    /* Will use an existing  result file beforehand as template.  */
    private KeepCalls keepCall;
    /* logLevel */
    private LogLevel logLevel;
    /* A string representation for a list of model keys. */
    private String models;
    /* A overwrite field flag for {@link Call}. */
    private Boolean overwrite;
    /* postpend String when executed by ExecutorCallImpl */
    private String postpend;
    /* prepend String when executed by ExecutorCallImpl */
    private String prepend;
    /* A sourcePath where EO offers it's input value when the execution starts. */
    private String sourcePath;
    /* A condition for calls.  */
    private String startCondition;
    /* A targetPath where the result of the execution will be mapped. If value is "_asString" no mapping occured but a seralized version is returned as value to embed it in the resulting file. Path parameters could be set dynamically with =&gt;[path]. in any combination. */
    private String targetPath;
    /*.{}.*/

    protected CallImpl() {
        prepend = "";
        postpend = "";
    }

    public void check(final IEOScalar eo) {
        if (eo == null) {
            throw new EoException("Null eo value");
        }
        if (eo.get() == null) {
            throw new EoException("empty eo value");
        }
    }

    protected boolean init(final IEOScalar eo) {
        return evalCondition(eo);
    }

    /**
     * Evaluates the startCondition field  at the beginning of ExecutorCall.
     *
     * @param eo the current wrapper in the loop.
     * @return
     */
    @Override
    public boolean evalStartCondition(IEOScalar eo) {
        if (!hasStartCondition()) {
            return true;
        }
        return new Or(new Parser(TemplateMarker.SQUARE, getStartCondition()).parse(eo)).filter(eo);
    }

    /**
     * Evaluates the condition field within ExecutorCall.
     *
     * @param eo the current wrapper in the loop.
     * @return
     */
    protected boolean evalCondition(final IEOScalar eo) {
        if (!hasCondition()) {
            return true;
        }
        return new Or(Parser.replacePathValues(getCondition(), eo)).filter(eo);
    }

    protected Object createReturnScalar(IEOScalar eo, Object result) {
        if (!hasTargetPath()) {
            return result;
        }
        if (isTargetAsString()) {
            return result.toString();
        }
        String localPath = eo.getPathAsString();
        if (targetPath.endsWith(localPath)) {
            IEOObject parent = eo.remove();
            IEOScalar newEo = parent.set(result, targetPath);
            newEo.setChanged();
        }
        else {
            eo.set(result, targetPath);
        }
        return "";
    }

    protected Object createReturnType(IEOScalar eo, Object result) {
        if (!hasTargetPath()) {
            return result;
        }
        if (isTargetAsString()) {
            return new EOToJSON()
                    .setSerializationType(JSONSerializationType.STANDARD)
                    .toJson(eo.getConfigMaps(), result);
        }
        eo.set(result, targetPath);
        return "";
    }

    protected String createReturnString(IEOScalar eo, String result) {
        if (!hasTargetPath() || isTargetAsString()) {
            return result;
        }
        eo.set(result, targetPath);
        return "";
    }

    public Boolean isOverwrite() {
        return overwrite;
    }

    /*.{javaAccessors}|*/
    @Override
    public String getCondition() {
        return this.condition;
    }

    @Override
    public CallImpl setCondition(final String condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public Long getDuration() {
        return this.duration;
    }

    @Override
    public CallImpl setDuration(final Long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public KeepCalls getKeepCall() {
        return this.keepCall;
    }

    @Override
    public CallImpl setKeepCall(final KeepCalls keepCall) {
        this.keepCall = keepCall;
        return this;
    }

    @Override
    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    @Override
    public CallImpl setLogLevel(final LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    @Override
    public String getModels() {
        return this.models;
    }

    @Override
    public CallImpl setModels(final String models) {
        this.models = models;
        return this;
    }

    @Override
    public Boolean getOverwrite() {
        return this.overwrite;
    }

    @Override
    public CallImpl setOverwrite(final Boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    @Override
    public String getPostpend() {
        return this.postpend;
    }

    @Override
    public CallImpl setPostpend(final String postpend) {
        this.postpend = postpend;
        return this;
    }

    @Override
    public String getPrepend() {
        return this.prepend;
    }

    @Override
    public CallImpl setPrepend(final String prepend) {
        this.prepend = prepend;
        return this;
    }

    @Override
    public String getSourcePath() {
        return this.sourcePath;
    }

    @Override
    public CallImpl setSourcePath(final String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    @Override
    public String getStartCondition() {
        return this.startCondition;
    }

    @Override
    public CallImpl setStartCondition(final String startCondition) {
        this.startCondition = startCondition;
        return this;
    }

    @Override
    public String getTargetPath() {
        return this.targetPath;
    }

    @Override
    public CallImpl setTargetPath(final String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    /*.{}.*/
}

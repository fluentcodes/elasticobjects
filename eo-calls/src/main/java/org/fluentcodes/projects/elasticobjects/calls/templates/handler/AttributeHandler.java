package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.calls.Call.F_TARGET_PATH;
import static org.fluentcodes.projects.elasticobjects.calls.Call.TARGET_AS_STRING;

public class AttributeHandler extends HandlerAbstract {
    static final Indicators indicator = Indicators.ATTRIBUTES;
    static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("([^=^ ]*)=\"([^\"]*)\"");

    public Indicators getIndicator() {
        return indicator;
    }

    Call createCallObject() {
        if (getCallDirective() == null) {
            throw new EoException("Nothing to do with an empty type directive.");
        }
        if (getConfigMaps() == null) {
            throw new EoInternalException("Some bug with a null conigMaps");
        }
        String[] methodAndAttributes = getCallDirective().split(" ");
        if (methodAndAttributes.length == 0) {
            throw new EoException("Nothing to do with an empty attribute type directive.");
        }

        String callKey = methodAndAttributes[0];
        ModelConfig callModel = getConfigMaps().findModel(callKey);
        Call call = (Call) callModel.create();

        Matcher matcher = ATTRIBUTE_PATTERN.matcher(getCallDirective());
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            callModel.set(key, call, value);
        }
        if (!call.hasTargetPath()) {
            callModel.set(F_TARGET_PATH, call, TARGET_AS_STRING);
        }
        if (hasContent()) {
            if (call instanceof CallContent) {
                ((CallContent) call).setContent(getContent());
            } else {
                throw new EoException("Setting content with implementing CallContent: '" + call.getClass().getSimpleName() + "'.");
            }
        }
        return call;
    }

    @Override
    public Object call() {
        if (getEo() == null) {
            return getDefaultValue();
        }
        final Call call = createCallObject();

        final StringBuilder returnResult = new StringBuilder();
        String postPend = "";
        if (call.hasKeepCall()) {
            returnResult.insert(0, createCallDirective() +
                    call.getKeepEndSequence());
            postPend = call.getKeepStartSequence() +
                    getTemplateMarker().getCloseSequence();
        }
        try {
            final Object value = ExecutorCall.execute(getEo(), call);
            if (value != null) {
                returnResult.append(value.toString());
            }
        } catch (Exception e) {
            if (getDefaultValue() == null || getDefaultValue().isEmpty()) {
                returnResult.append(e.getMessage());
            } else {
                returnResult.append(getDefaultValue());
            }
        }
        returnResult.append(postPend);
        return returnResult.toString();
    }
}

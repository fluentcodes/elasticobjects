package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

public class ParamsHandler extends HandlerAbstract {
    static final Indicators indicator = Indicators.VALUES;

    public Indicators getIndicator() {
        return indicator;
    }

    @Override
    public Object call() {
        if (getEo() == null) {
            return getDefaultValue();
        }
        String[] methodAndInput = getCallDirective().split("->");
        String parameters = methodAndInput.length == 1 ? methodAndInput[0] : methodAndInput[1];
        String callKey = methodAndInput.length == 1 ? TemplateResourceCall.class.getSimpleName() : methodAndInput[0];
        ModelConfig callModel = getEo().getConfigMaps().findModel(callKey);
        Call call = (Call) callModel.create();
        call.setByParameter(parameters);
        if (hasContent()) {
            if (call instanceof CallContent) {
                ((CallContent) call).setContent(getContent());
            } else {
                getEo().debug("Setting content with implementing CallContent: '" + call.getClass().getSimpleName() + "'.");
            }
        }
        StringBuilder returnResult = new StringBuilder();
        String postPend = "";
        if (call.hasKeepCall()) {
            returnResult.insert(0, createCallDirective() +
                    call.getKeepEndSequence());
            postPend = call.getKeepStartSequence() +
                    getTemplateMarker().getCloseSequence();
        }
        try {
            Object value = ExecutorCall.execute(getEo(), call);
            if (value != null) {
                returnResult.append(value.toString());
            }
        } catch (Exception e) {
            if (!hasDefaultValue()) {
                returnResult.append(e.getMessage());
            } else {
                returnResult.append(getDefaultValue());
            }
        }
        returnResult.append(postPend);
        return returnResult.toString();
    }
}

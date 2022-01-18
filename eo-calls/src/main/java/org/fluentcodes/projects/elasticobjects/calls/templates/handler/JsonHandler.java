package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.List;

public class JsonHandler extends HandlerAbstract {

    static final Indicators indicator = Indicators.JSON;

    public Indicators getIndicator() {
        return indicator;
    }

    @Override
    public Object call() {
        if (getEo() == null) {
            return getDefaultValue();
        }
        EoRoot eoCall = EoRoot.ofValue(getEo().getConfigMaps(),
                TemplateMarker.CURLY.getStart()
                        + getCallDirective()
                        + TemplateMarker.CURLY.getStop());
        if (!eoCall.isEmpty()) {
            getEo().set(eoCall.get());
        }
        if (eoCall.getCallKeys().isEmpty()) {
            return "";
        }
        return executeCalls(eoCall);
    }

    private String executeCalls(EOInterfaceScalar eoCall) {
        List<String> callSet = new ArrayList<>(eoCall.getCallKeys());
        StringBuilder returnResult = new StringBuilder();
        String postPend = "";
        for (int i = 0; i < callSet.size(); i++) {
            Call loopCall = (Call) eoCall.getCallEo(Integer.toString(i)).get();
            if (!loopCall.hasTargetPath()) {
                loopCall.setTargetPath(Call.TARGET_AS_STRING);
            }
            if (i == callSet.size() - 1) {
                postPend = addKeepOrContentToLast(returnResult, loopCall);
            }
            try {
                returnResult.append(ExecutorCall.execute(getEo(), loopCall));
            } catch (Exception e) {
                if (getDefaultValue() == null) {
                    returnResult.append(e.getMessage());
                } else {
                    returnResult.append(getDefaultValue());
                }
            }
        }
        returnResult.append(postPend);
        return returnResult.toString();
    }

    private String addKeepOrContentToLast(StringBuilder returnResult, Call lastCall) {
        String postPend = "";
        if (lastCall.hasKeepCall()) {
            returnResult.insert(0, createCallDirective() +
                    lastCall.getKeepEndSequence());
            postPend = lastCall.getKeepStartSequence() +
                    getTemplateMarker().getCloseSequence();
        }
        if (getContent() != null && !getContent().isEmpty()) {
            if (lastCall instanceof CallContent) {
                ((CallContent) lastCall).setContent(getContent());
            } else {
                throw new EoException("Last call with content template directive is not instance of CallContent but '" + lastCall.getClass().getSimpleName() + "'");
            }
        }
        return postPend;

    }
}

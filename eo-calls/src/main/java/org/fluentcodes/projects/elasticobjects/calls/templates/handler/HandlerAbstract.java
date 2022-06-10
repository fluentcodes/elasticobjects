package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

abstract class HandlerAbstract implements HandlerInterface {
    static final String DEFAULT_SEPARATOR = "|>";
    private EOInterfaceScalar eo;
    private TemplateMarker templateMarker;
    private String callDirective;
    private String content;
    private String defaultValue;

    EOInterfaceScalar getEo() {
        return eo;
    }

    HandlerAbstract setEo(EOInterfaceScalar eo) {
        this.eo = eo;
        return this;
    }

    TemplateMarker getTemplateMarker() {
        return templateMarker;
    }

    HandlerAbstract setTemplateMarker(TemplateMarker templateMarker) {
        this.templateMarker = templateMarker;
        return this;
    }

    String getCallDirective() {
        return callDirective;
    }

    HandlerAbstract setCallDirective(String callDirective) {
        this.callDirective = callDirective;
        return this;
    }

    String getContent() {
        return content;
    }

    HandlerAbstract setContent(String content) {
        this.content = content;
        return this;
    }

    String getDefaultValue() {
        return defaultValue;
    }

    HandlerAbstract setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    boolean hasDefaultValue() {
        return defaultValue != null && !defaultValue.isEmpty();
    }

    boolean hasCallDirective() {
        return callDirective != null && !callDirective.isEmpty();
    }

    ConfigMaps getConfigMaps() {
        return eo.getConfigMaps();
    }

    String createCallDirective() {
        final String defaultWithSeparator = hasDefaultValue() ? DEFAULT_SEPARATOR + getDefaultValue() : "";
        return getIndicatorValue() +
                getTemplateMarker().getStart() +
                getCallDirective() +
                defaultWithSeparator +
                getTemplateMarker().getContinueSequence();
    }
}

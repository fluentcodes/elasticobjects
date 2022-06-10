package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

public interface HandlerInterface {
    Object call();

    Indicators getIndicator();

    default String getIndicatorValue() {
        return getIndicator().getIndicator();
    }

}

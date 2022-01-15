package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;

import java.util.Set;

/**
 * Offers an adapter for calls.
 */

public interface EOInterfaceCall extends EOInterfaceBase {
    default EO addCall(Call call) {
        return getRoot().addCall(call);
    }

    default Set<String> getCallKeys() {
        return getRoot().getCallKeys();
    }

    default EoChild getCallEo(String key) {
        return getRoot().getCallEo(key);
    }

    default EoChild getCallsEo() {
        return getRoot().getCallsEo();
    }

    default boolean hasCalls() {
        return getRoot().hasCalls();
    }

    default boolean execute() {
        return getRoot().execute();
    }
}

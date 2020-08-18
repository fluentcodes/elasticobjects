package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;

/**
 * Add value to eo.
 * Created by Werner on 02.08.2020.
 */
public class ValueCall extends CallImpl<String> implements Call<String> {
    public static final String VALUE = "value";
    private String value;
    public ValueCall() {
    }

    public ValueCall(final String value) {
        this();
        this.value = value;
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        return value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String getFilterPath() {
        return ".";
    }
}
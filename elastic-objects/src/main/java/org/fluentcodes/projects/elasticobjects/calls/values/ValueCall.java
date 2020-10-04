package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;

/**
 * Add value to eo.
 * Created by Werner on 02.08.2020.
 */
public class ValueCall extends CallImpl {
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
        //Object value = eo.get();
        if (hasTargetPath()) {
            eo.set(this.value, getTargetPath());
        }
        return super.createReturnString(eo, value.toString());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

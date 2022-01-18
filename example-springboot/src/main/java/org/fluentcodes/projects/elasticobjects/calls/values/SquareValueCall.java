package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerDouble;

/**
 * For Compute square math values.
 */
public class SquareValueCall extends CallImpl implements SimpleCommand {
    private String function;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public boolean hasFunction() {
        return this.function!=null && !this.function.isEmpty();
    }

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        super.check(eo);
        Double inputValue = new ShapeTypeSerializerDouble().asObject(eo.get());
        if (!hasFunction()) {
            this.function = "toDegrees";
        }
        Double result = null;
        switch (function) {
            case "sin":
                result = Math.sin(inputValue);
                break;
            case "cos":
                result = Math.cos(inputValue);
                break;
            case "tan":
                result = Math.tan(inputValue);
                break;
            case "toDegrees":
                result = Math.toDegrees(inputValue);
                break;
            default:
                result = Math.toDegrees(inputValue);
        }
        try {
            return super.createReturnScalar(eo, result);
        } catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}

package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerDouble;

/**
 * For setting cosinus value to EO.
 */
public class CosinusValueCall extends CallImpl implements SimpleCommand {
    @Override
    public Object execute(final EOInterfaceScalar eo) {
        super.check(eo);
        Double value = new ShapeTypeSerializerDouble().asObject(eo.get());
        try {
            return super.createReturnScalar(eo, Math.cos(value));
        } catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}

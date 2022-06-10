package org.fluentcodes.projects.elasticobjects.calls.commands;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public interface ConfigReadWriteCommand extends Call {
    ConfigReadWriteCommand setSourceFileConfigKey(final String sourceFileConfigKey);
    ConfigReadWriteCommand setTargetFileConfigKey(final String targetFileConfigKey);
    default void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>4) {
            throw new EoException("Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 4 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setSourceFileConfigKey(array[0]);
        }
        if (array.length>1) {
            setTargetFileConfigKey(array[1]);
        }
        if (array.length>2) {
            setCondition( array[2]);
        }
        if (array.length>3) {
            setKeepCall(KeepCalls.valueOf(array[3]));
        }
    }
}

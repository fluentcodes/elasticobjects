package org.fluentcodes.projects.elasticobjects.calls.commands;

import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public interface SimpleCommand extends Call {
    default void setByParameter(final String values) {
        if (values == null || values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length > 3) {
            throw new EoException("Short form should have form '<sourcePath>[,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'.");
        }
        if (array.length > 0) {
            if (array[0].replaceAll("\\s", "").isEmpty()) {
                setSourcePath(PathElement.V_SAME);
            } else {
                setSourcePath(array[0]);
            }
        }
        if (array.length > 1) {
            setTargetPath(array[1]);
        }
        if (array.length > 2) {
            setCondition(array[2]);
        }
        if (!hasTargetPath()) {
            setTargetPath(TARGET_AS_STRING);
        }
    }
}

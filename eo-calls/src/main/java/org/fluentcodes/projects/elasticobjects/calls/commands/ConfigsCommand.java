package org.fluentcodes.projects.elasticobjects.calls.commands;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.configs.SortOrder;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Expose;

public interface ConfigsCommand extends Call {
    ConfigsCommand setConfigType(final String configType);
    ConfigsCommand setConfigFilter(final String configFilter);
    ConfigsCommand setSortOrder(final SortOrder sortOrder);
    ConfigsCommand setExpose(final Expose expose);
    default void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<configType>[,<configFilter>][,<expose>][,<targetPath>][,<sortOrder>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setConfigType(array[0]);
        }
        if (array.length>1) {
            setConfigFilter(array[1]);
        }
        if (array.length>2) {
            setExpose(Expose.valueOf(array[2]));
        }
        if (array.length>3) {
            setTargetPath(array[3]);
        }
        if (array.length>4) {
            setSortOrder(SortOrder.valueOf(array[5]));
        }
    }
}

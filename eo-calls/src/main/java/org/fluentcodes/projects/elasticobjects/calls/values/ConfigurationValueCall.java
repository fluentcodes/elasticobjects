package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigurationValueCall extends CallImpl implements SimpleCommand {
    private String configType;
    private String configKey;
    private String configFilter;

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        super.check(eo);
        if (configType == null) {
            throw new EoException("No config type defined");
        }
        return eo.getConfigMaps().getConfigKeys(configType);
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

}

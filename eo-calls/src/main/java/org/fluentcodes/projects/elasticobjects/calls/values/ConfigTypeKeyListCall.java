package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EOInterfaceScalar;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigTypeKeyListCall extends CallImpl implements SimpleCommand {
    private String configFilter;

    @Override
    public Object execute(final EOInterfaceScalar eo) {
        super.check(eo);
        List<String> keys = eo.getConfigMaps().getKeysAsString();
        if (configFilter == null) {
            return keys;
        }
        try {
            return keys
                    .stream()
                    .filter(x -> x.matches(configFilter))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return keys;
        }
    }

    public String getConfigFilter() {
        return configFilter;
    }

    public void setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
    }
}

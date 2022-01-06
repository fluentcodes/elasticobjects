package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Super class for file calls with a configuration key to resolve {@link HostConfig} with init method. Extends {@link CallImpl}.
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 07:32:31 CET 2020
 */
public abstract class HostCall extends CallImpl implements Call {

    public static final String HOST_CONFIG_KEY = "hostConfigKey";

    private String hostConfigKey;

    private HostConfig hostConfig;

    public HostCall() {
        super();
    }

    public HostCall(final String configKey) {
        super();
        setHostConfigKey(configKey);
    }

    protected HostConfig initHostConfig(final PermissionType permissionType, final IEOScalar eo) {
        if (!hasHostConfigKey()) {
            throw new EoException("Empty key for host");
        }
        hostConfig = (HostConfig) eo.getConfigMaps().find(HostConfig.class, hostConfigKey);
        hostConfig.hasPermissions(permissionType, eo.getRoles());
        return hostConfig;
    }

    public HostConfig getHostConfig() {
        return hostConfig;
    }

    /*.{javaAccessors}|*/

    /**
     * A key for host objects.
     */

    public HostCall setHostConfigKey(String hostConfigKey) {
        this.hostConfigKey = hostConfigKey;
        return this;
    }

    public String getHostConfigKey() {
        return this.hostConfigKey;
    }

    public boolean hasHostConfigKey() {
        return hostConfigKey != null && !hostConfigKey.isEmpty();
    }
    /*.{}.*/
}

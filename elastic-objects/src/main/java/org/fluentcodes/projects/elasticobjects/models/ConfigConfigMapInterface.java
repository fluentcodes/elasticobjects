package org.fluentcodes.projects.elasticobjects.models;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @since Created by Werner on 12.10.2018.
 */
public interface ConfigConfigMapInterface<T extends ConfigConfigInterface> {
    T find(final String key) ;
    boolean hasKey(final String key);
    Set<String> getKeys();
    default Set<String> getKeys(Expose expose) {
        if (expose == null || expose == Expose.NONE || isEmpty()) {
            return getKeys();
        }
        Set<String> configKeys = new LinkedHashSet<>();
        for (String naturalId: getKeys()) {
            T config = find(naturalId);
            if (config.hasExpose() && config.getExpose().ordinal() <= expose.ordinal()) configKeys.add(naturalId);
        }
        return configKeys;
    }
    boolean isEmpty();
}
